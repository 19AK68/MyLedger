package com.example.andrey.myledger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.Category;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCostCategoryFragment extends Fragment {

    View rootView;

    private EditText editTextAddCostCategory;

    private Button buttonAddCostCategory;
    private AccountBookDbHelper dbHelper;
    Activity activity;




    public AddCostCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_add_cost_category, container, false);

        editTextAddCostCategory = (EditText) rootView.findViewById(R.id.etNameCategory);
        buttonAddCostCategory = (Button)rootView.findViewById(R.id.saveCategory);

        buttonAddCostCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keyboard.hide(view);
                addNewCategory();
                AddCost addCost = (AddCost)getActivity();
                addCost.setupSpinnerCategory();
            }
        });

        return rootView;
    }

    private void addNewCategory() {

        String addName = editTextAddCostCategory.getText().toString().trim();


        dbHelper = new AccountBookDbHelper(getActivity());

        if (addName.isEmpty()){

            //error name is empty
            Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
        }


        Category category = new Category(addName);

        dbHelper.saveNewCategory(category);
        goBackActivity();






    }

    private void  goBackActivity() {

        // Request focus and show soft keyboard automatically

        getActivity().getFragmentManager().popBackStack();
    }



}
