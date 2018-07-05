package com.example.andrey.myledger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;

public class AddIncomDialogFragment extends  DialogFragment {

    private IAddCategorytDialFrag iAddCategorytDialFrag;

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder createAddCategoryAlert = new AlertDialog.Builder(getActivity());
        createAddCategoryAlert.setTitle("Добавить категорию");
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog_add_category_fragment,null);


        createAddCategoryAlert.setView(inflater.inflate(R.layout.dialog_add_category_fragment, null));
        createAddCategoryAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etNameIncomCatrgory = (EditText) getDialog().findViewById(R.id.add_incom_category_name);

                String addNameIncomCategory = etNameIncomCatrgory.getText().toString();
                AddNewIncomCategory(addNameIncomCategory);
                // Log.e("", "Value is  addNameIncomCategory: " +etNameIncomCatrgory.getText());

                iAddCategorytDialFrag.onDialogPositiveClick(AddIncomDialogFragment.this);
            }
        });
        createAddCategoryAlert.setNegativeButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                iAddCategorytDialFrag.onDialogNegativeClick(AddIncomDialogFragment.this);
            }
        });

        return createAddCategoryAlert.create();
    }

    private void AddNewIncomCategory(String addNameIncomCategory) {

//        AccountBookDbHelper dbHelper = new AccountBookDbHelper(getActivity());
//        AccountBookContract.IncomCategory incomCategory =  new AccountBookContract.IncomCategory();
//
//        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iAddCategorytDialFrag = (IAddCategorytDialFrag) activity;
    }





}
