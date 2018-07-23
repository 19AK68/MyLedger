package com.example.andrey.myledger;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.AccountBook;


public class AddIncomAccoutnFragment  extends Fragment {

    View rootView;

    private EditText editTextAddAccount;
    private EditText editTextAddSumAccount;
    private Button buttonAddACoount;
    private AccountBookDbHelper dbHelper;
    Activity activity;

    public AddIncomAccoutnFragment() {

        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.add_incom_accoutn_fragment, container, false);

        editTextAddAccount =  (EditText) rootView.findViewById(R.id.add_incom_accout_name);
        editTextAddSumAccount = (EditText) rootView.findViewById(R.id.add_incom_sum_account);


        buttonAddACoount =(Button) rootView.findViewById(R.id.btnAddIncomAccount);

        buttonAddACoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Keyboard.hide(view);

                AddNewIncomAcount();
                AddIncom addIncomAccount = (AddIncom) getActivity();
                addIncomAccount.setupSpinnerIncomAccount();








            }
        });

        editTextAddSumAccount.addTextChangedListener(new TextWatcher() {

            private String txtBefore;
            private String txtAfter;
            private String txtBeforeWithout;
            private String txtAfterWithout;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        return rootView;




    }

    private void AddNewIncomAcount() {

        String addIncomAccountName = editTextAddAccount.getText().toString().trim();
        String addIncomAccountSumm = editTextAddSumAccount.getText().toString().trim();

        dbHelper = new AccountBookDbHelper(getActivity());

        if (addIncomAccountName.isEmpty()){

            //error name is empty
            Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
        }
        if(addIncomAccountSumm.isEmpty()){
            //error name is empty
            Toast.makeText(getActivity(), "Введите сумму остатка ", Toast.LENGTH_SHORT).show();
        }


        AccountBook accountBook = new AccountBook(addIncomAccountName,addIncomAccountSumm);

        dbHelper.saveNewAccount(accountBook);

        goBackActivity();

    }

    private void goBackActivity() {

        // Request focus and show soft keyboard automatically

        getActivity().getFragmentManager().popBackStack();
    }


}
