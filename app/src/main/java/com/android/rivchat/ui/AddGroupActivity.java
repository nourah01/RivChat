package com.android.rivchat.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.MainActivity;
import com.android.rivchat.R;
import com.android.rivchat.data.measurementDB;
import com.android.rivchat.model.measurement;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;




/**************************** MEASURE *******************************/

public class AddGroupActivity extends Activity {

    TextView myLabel;
    TextView down ;
    TextView warn;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    String date_time;
    static String data;
    measurementDB db;
    TextView text7;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    UserProfileFragment usremail;
   static String Email;

        @Override
        protected void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

            Button openButton = (Button) findViewById(R.id.open);
        db = new measurementDB(this);
        myLabel = (TextView) findViewById(R.id.label);
        down = (TextView) findViewById(R.id.countDown);
        warn = (TextView) findViewById(R.id.warn);
        text7 = (TextView) findViewById(R.id.textView7);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Make sure that  bluetooth is open & connected to the device");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();


            }
        });
        builder.show();


        //Open Button
        openButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              try {
                    findBT();
                openBT();
                    warn.setVisibility(View.GONE);
                    text7.setVisibility(View.GONE);

 } catch (IOException ex) {
               }
            }
        });


    }



    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
           /* Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);*/
            mBluetoothAdapter.enable();


        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) {
                    mmDevice = device;
                    break;
                }
            }
        }
       // myLabel.setText("Bluetooth Device Found");
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmInputStream = mmSocket.getInputStream();
        View b = findViewById(R.id.open);
        b.setVisibility(View.GONE);
        beginListenForData();
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                down.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                stopWorker = true;
                down.setVisibility(View.GONE);

                try {
                    mmInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ///// add database
                calander = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy                                HH:mm:ss");
                date_time = simpledateformat.format(calander.getTime());
                Email = MainActivity.currentuseremail;
                measurement measurement_to_add = new measurement(Email, data, date_time + "", "  ");
                Boolean RESULT = db.insertData(measurement_to_add);
                if (RESULT == true) {
                    startActivity(new Intent(AddGroupActivity.this, measurementres.class));
                    AddGroupActivity.this.finish();
                } else {
                    Toast.makeText(AddGroupActivity.this, "NO", Toast.LENGTH_SHORT).show();

                }

            }
        }.start();
        myLabel.setText("Bluetooth Opened");
    }

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            myLabel.setText(data);

                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });


        workerThread.start();
    }

    public static String returndata() {
        return data;
    }




}

