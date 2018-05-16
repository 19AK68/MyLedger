package com.example.andrey.myledger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.*;

import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.AccountBook;

public class AddAccountBookActivity extends AppCompatActivity {

    private EditText mNameAccount;
    private EditText mSumAccount;
    private Button   mButton;
    private AccountBookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_book);

        //init
        mNameAccount = (EditText)findViewById(R.id.etNameAccount);
        mSumAccount= (EditText)findViewById(R.id.etBalansSum);
        mButton = (Button) findViewById(R.id.saveBtn);

        mSumAccount.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                int p = str.indexOf(".");
                if (p != -1) {
                    String tmpStr = str.substring(p);
                    if (tmpStr.length() == 4) {
                        s.delete(s.length()-1, s.length());
                    }
                }

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccout();
            }
        });


    }

    private void addNewAccout() {

        String addName = mNameAccount.getText().toString().trim();
        String addSumm = mSumAccount.getText().toString().trim();



        dbHelper = new AccountBookDbHelper(this);

        if (addName.isEmpty()){

            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }
        if(addSumm.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a Balance Sum", Toast.LENGTH_SHORT).show();
        }

        AccountBook accountBook = new AccountBook(addName,addSumm);

        dbHelper.saveNewAccount(accountBook);
        goBackHome();






    }

    private void goBackHome() {
     Intent intent = new Intent(AddAccountBookActivity.this,MainActivity.class) ;
        startActivity(intent);
    }
}
