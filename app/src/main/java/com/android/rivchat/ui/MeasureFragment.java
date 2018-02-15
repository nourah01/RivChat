package com.android.rivchat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rivchat.R;

/**
 * Created by - on 2/12/2018.
 */

public class MeasureFragment extends Fragment {
    public MeasureFragment.FragGroupClickFloatButton onClickFloatButton;

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

        return layout;
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
