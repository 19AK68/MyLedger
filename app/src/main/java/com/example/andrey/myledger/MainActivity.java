package com.example.andrey.myledger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.AccountBookAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AccountBookDbHelper dbHelper;
    private AccountBookAdapter adapter;
    private String filter ="";
    private Button btnCost;
    private Button btnIcomes;
    private ClickListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCost = (Button) findViewById(R.id.btnCost);
        btnIcomes = (Button) findViewById(R.id.btnIncomes);

        //initialize
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //  layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        //populate recyclerview

        populaterecyclerView(filter);

        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnCost:
                     gotoAddCost();
                        break;
                    case R.id.btnIncomes:
                        gotoAddIncom();
                        break;

                }
            }
        };

        btnCost.setOnClickListener(oclBtn);
        btnIcomes.setOnClickListener(oclBtn);



    }



    private void populaterecyclerView(String filter) {

        dbHelper = new AccountBookDbHelper(this);
        adapter = new AccountBookAdapter(dbHelper.accountBookList(filter),this,mRecyclerView);
        mRecyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.filterSpinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filterOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = parent.getSelectedItem().toString();
                populaterecyclerView(filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                populaterecyclerView(filter);
            }
        });


        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu:
                goToAddAccountBookActivity();
                // Handle item selection
                return true;

            case R.id.addMenu2:
                goToCategoryActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToCategoryActivity() {
        Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
        startActivity(intent);
    }

    private void goToAddAccountBookActivity(){
        Intent intent = new Intent(MainActivity.this, AddAccountBookActivity.class);
        startActivity(intent);
    }

    private void gotoAddCost() {
        Intent intent = new Intent(MainActivity.this, AddCost.class);
        startActivity(intent);
    }

    private void gotoAddIncom() {
        Intent intent = new Intent(MainActivity.this, AddIncom.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaterecyclerView(filter);
        adapter.notifyDataSetChanged();
    }

}
