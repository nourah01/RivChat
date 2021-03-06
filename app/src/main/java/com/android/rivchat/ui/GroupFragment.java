package com.android.rivchat.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.android.rivchat.R;
import com.android.rivchat.data.FriendDB;
import com.android.rivchat.data.GroupDB;
import com.android.rivchat.data.StaticConfig;
import com.android.rivchat.model.Group;
import com.android.rivchat.model.ListFriend;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**************************** GET HISTORY *******************************/
public class GroupFragment extends Fragment {
    DatabaseReference reference;

    ArrayList<String> arrayList;

    EditText e1;
    ListView l1;
    ArrayAdapter<String> adapter;
    String name;
    EditText ee;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.chatroom, container, false);
        l1 = (ListView)layout.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayList.add("low");
        arrayList.add("mid");
        arrayList.add("hight");

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,arrayList);
        l1.setAdapter(adapter);


        reference = FirebaseDatabase.getInstance().getReference().getRoot();
       // request_username();

      /*  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();


                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });*/


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getActivity(), Chatroom.class);
                intent.putExtra("room_name", "low");
                intent.putExtra("user_name", MainActivity.currentuseremail);
                startActivity(intent);

            }
        });



        return layout;
    }
    public void request_username()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter your name?");
        ee = new EditText(getActivity());
        builder.setView(ee);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = ee.getText().toString();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_username();


            }
        });
        builder.show();

    }





    public void insert_data(View v)
    {

        Map<String,Object> map = new HashMap<>();
        map.put(e1.getText().toString(), "");
        reference.updateChildren(map);

    }



}