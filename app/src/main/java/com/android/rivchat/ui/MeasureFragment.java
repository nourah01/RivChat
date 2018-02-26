package com.android.rivchat.ui;



        import android.content.Context;
        import android.content.Intent;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import com.android.rivchat.R;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import com.android.rivchat.data.historyDB;
        import com.android.rivchat.model.history;
        import android.widget.ListView;
        import android.widget.Button;
        import java.util.ArrayList;
        import android.widget.Toast;







/**
 * Created by - on 2/12/2018.
 */

public class MeasureFragment extends Fragment {
    public MeasureFragment.FragGroupClickFloatButton onClickFloatButton;
    public EditText ID;
    ListView lst;

    public boolean find=false;
    public boolean findid=false;
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

        ID=(EditText) layout.findViewById(R.id.editText);
        lst=(ListView) layout.findViewById(R.id.listView_data);

        historyDB db=new historyDB(getActivity());
        history h=new history("shaimaa","2-13-2018","i was tired" );
        history h2=new history("test","2-13-2018" ,"i was tired and sad");

        Boolean RESULT= db.insertData(h);
        Boolean RESULT2= db.insertData(h2);

        showData();

       /* if (RESULT==true) {
            Toast.makeText(GroupFragment.this, "OK", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(GroupFragment.this, "NO", Toast.LENGTH_SHORT).show();

        }*/

        Button b = (Button) layout.findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=ID.getText().toString();
                historyDB db=new historyDB(getActivity());
                ArrayList<String> lisdata=db.getAllid();
                if(lisdata.isEmpty()) {
                    Toast.makeText(getActivity(), "NO history to delete", Toast.LENGTH_SHORT).show();                }
                if(id.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter id to delete", Toast.LENGTH_SHORT).show();
                    findid=true;}

                    for(int i=0;i<lisdata.size();i++) {
                    if(lisdata.remove(i).equals(id)) {
                        Integer result=db.DeleteData(id);
                        showData();
                        find=true;
                        break;
                    }

                            }
            if(find==false | findid==false)
                Toast.makeText(getActivity(), "Id not exist", Toast.LENGTH_SHORT).show();                }


        });

        Button b2 = (Button) layout.findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyDB db=new historyDB(getActivity());
                db.DeleteAllData();
                showData();
                startActivity(new Intent(getActivity(), measurementres.class));
                getActivity().finish();}
        });

        return layout;

    }



    public void showData() {
        historyDB db=new historyDB(getActivity());
        ArrayList<String> lisdata=db.getAllrecords();
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,lisdata);
        lst.setAdapter(arrayAdapter);
    }

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
