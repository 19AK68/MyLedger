package com.example.andrey.myledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.Category;

public class AddCategoryActivity  extends AppCompatActivity {

    private EditText mNameCategory;
    private Button button;
    private AccountBookDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        //init
        mNameCategory = (EditText)findViewById(R.id.etNameCategory);
        button = (Button) findViewById(R.id.saveCategory);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewCategory();

            }
        }
        );
    }

    private void addNewCategory() {

        String addName = mNameCategory.getText().toString().trim();


        dbHelper = new AccountBookDbHelper(this);

        if (addName.isEmpty()){

            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }


       Category category = new Category(addName);

        dbHelper.saveNewCategory(category);
        goBackHome();






    }

    private void goBackHome() {

        startActivity(new Intent(AddCategoryActivity.this, AddCost.class));
    }

    
}
