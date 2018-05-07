package com.example.andrey.myledger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        //startActivity(new Intent(AddRecordActivity.this, MainActivity.class));
    }
}
