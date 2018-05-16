package com.example.andrey.myledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InfoAccountBookActivity extends AppCompatActivity {



    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_accountbook);

        mText = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        String account_id = intent.getStringExtra("account_id");
        mText.setText("Информация account_id: "+account_id);


    }
}
