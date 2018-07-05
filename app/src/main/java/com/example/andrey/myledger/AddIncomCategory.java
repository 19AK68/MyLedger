package com.example.andrey.myledger;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.IncomCategory;

public class AddIncomCategory extends Fragment {

    View rootView;
    private EditText etNameCategory;
    private Button buttonAdd;
    Activity activity;

    private AccountBookDbHelper dbHelper;

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
                AddNewIncomCategory();
                Keyboard.hide(v);
            }
        });




        return  rootView;





    }

    private void AddNewIncomCategory() {

        String addNewIncomCategory = etNameCategory.getText().toString().trim();

        dbHelper = new AccountBookDbHelper(getActivity());

        if (addNewIncomCategory.isEmpty()) {

            //error name is empty
            Toast.makeText(getActivity(), "You must enter a name category", Toast.LENGTH_SHORT).show();

        }

        IncomCategory incomCategory = new IncomCategory(addNewIncomCategory);

        dbHelper.saveNewIncomCategory(incomCategory);

        goBackActivity();




    }

    private void goBackActivity() {
        // Request focus and show soft keyboard automatically

        getActivity().getFragmentManager().popBackStack();
    }


}
