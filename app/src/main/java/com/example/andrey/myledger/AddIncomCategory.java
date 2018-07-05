package com.example.andrey.myledger;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddIncomCategory extends Fragment {

    View rootView;
    private EditText etNameCategory;
    private Button buttonAdd;

public   AddIncomCategory () {

        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        rootView =  inflater.inflate(R.layout.add_incom_category_fragment, container, false);

        etNameCategory = rootView.findViewById(R.id.add_incom_category_name);
        buttonAdd = rootView.findViewById(R.id.btnAddIncomCategory);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return  rootView;





    }


}
