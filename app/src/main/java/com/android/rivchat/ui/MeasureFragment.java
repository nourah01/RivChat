package com.android.rivchat.ui;



        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.rivchat.MainActivity;
import com.android.rivchat.R;
import com.android.rivchat.data.measurementDB;

import java.util.ArrayList;

/**
 * Created by - on 2/12/2018.
 */

public class MeasureFragment extends Fragment {
    public MeasureFragment.FragGroupClickFloatButton onClickFloatButton;
    public EditText ID;
    ListView lst;
    static String email;
    public boolean find=false;
    public MeasureFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_group, container, false);

        onClickFloatButton = new MeasureFragment.FragGroupClickFloatButton();
        lst=(ListView) layout.findViewById(R.id.listView_data);
        email=MainActivity.currentuseremail;
        showData();
        //////////////delete
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String) lst.getItemAtPosition(position);
                final String selectedFromList2 = selectedFromList.substring(3,selectedFromList.indexOf("-"));

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("are you sure want to delete this mesurement?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id=selectedFromList2;
                        measurementDB db=new measurementDB(getActivity());
                        ArrayList<String> lisdata=db.getAllid();

                        if(lisdata.isEmpty()) {
                            Toast.makeText(getActivity(), "NO mesurement to delete", Toast.LENGTH_SHORT).show();}
                        else { if(id.isEmpty()) {
                            Toast.makeText(getActivity(), "Enter id to delete", Toast.LENGTH_SHORT).show();}
                        else {
                            for(int j=0;j<lisdata.size();j++) {
                                if(lisdata.get(j).equals(id)) {
                                    Integer result=db.DeleteData(id);
                                    showData();
                                    find=true;
                                    break;
                                } } } }
                        if(find==false)
                            Toast.makeText(getActivity(), "Id not exist", Toast.LENGTH_SHORT).show();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();



                    }
                });
                builder.show();

            }});
        //////////////delete

        //////////////deletealll

        Button b2 = (Button) layout.findViewById(R.id.button3);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("are you sure want to delete all the history?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        measurementDB db=new measurementDB(getActivity());
                        ArrayList<String> lisdata=db.getAllid();

                        if(lisdata.isEmpty()) {
                            Toast.makeText(getActivity(), "NO history to delete", Toast.LENGTH_SHORT).show();}
                        else {
                            db.DeleteAllData();
                            showData(); }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();



                    }
                });
                builder.show();
            }
        });

        //////////////deleteall


       /* Button b2 = (Button) layout.findViewById(R.id.button3);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurementDB db=new measurementDB(getActivity());
                db.DeleteAllData();
                showData();
            }
        });*/

        return layout;

    }



    public void showData() {
        measurementDB db=new measurementDB(getActivity());
        ArrayList<String> lisdata=db.getAllrecords(email);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,lisdata);
        lst.setAdapter(arrayAdapter);
    }

    public void onResume() {
        super.onResume();
        showData();
    }

 /*   @Override
    public void onRefresh() {
        showData();
    }*/

    public class FragGroupClickFloatButton implements View.OnClickListener{

        Context context;
        public MeasureFragment.FragGroupClickFloatButton getInstance(Context context){
            this.context = context;
            return this;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(getContext(), AddGroupActivity.class));
        }
    }


}
