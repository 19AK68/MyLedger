package com.example.andrey.myledger;

import android.app.DatePickerDialog;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.AccountBook;
import com.example.andrey.myledger.Model.Category;
import com.example.andrey.myledger.Model.Cost;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class AddCost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   private TextView currentDate;
   private TextView currentTime;
   private TextView mCategory;

   private EditText mAddCostSum;
   private EditText mComment ;

   private Button mBtnSaveCost;

   private ImageButton imageButtonAddCostCategoty;
   private int positionCategory = 0;
   private int positionAccount = 0;

   Spinner mSpinnerCategory;
   Spinner mSpinnerAccount;


   private String addCostSumFormat = "";

    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_cost);




        // init

        // Date and Time

        currentDate=(TextView)findViewById(R.id.addDataCost);
        currentTime=(TextView) findViewById(R.id.addTimeCost);
        //  set Date and Time
        setInitialDateTime();
        ///////


        mCategory=(TextView)findViewById(R.id.tvCategory);
        imageButtonAddCostCategoty = (ImageButton) findViewById(R.id.imageButtonAddCostCategory);

        mSpinnerCategory = (Spinner)findViewById(R.id.spinnerCategory) ;
         //   setupSpinnerCategory();



        mAddCostSum = (EditText)findViewById(R.id.etAddSCostSum);

        mSpinnerAccount=  (Spinner)findViewById(R.id.spinnerAccount) ;



        mComment= (EditText)findViewById(R.id.etAddSCostСomment);

        mBtnSaveCost = (Button) findViewById(R.id.btnSaveAddCost) ;



        //spinner  category
        setupSpinnerCategory();
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  selection =   parent.getItemAtPosition(position).toString();

                   positionCategory = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageButtonAddCostCategoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.add_cost_container, new AddCostCategoryFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        mAddCostSum.addTextChangedListener(new TextWatcher(){

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


        // spinner account
        setupSpinnerAccount();
        mSpinnerAccount.setOnItemSelectedListener( this);

        mBtnSaveCost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SaveCost();
               gotoMainActivity();
           }
       });




    }

    private void goToAddCategoryActivity() {
        Intent intent = new Intent(AddCost.this, AddCategoryActivity.class);
        startActivity(intent);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(AddCost.this, MainActivity.class);
        startActivity(intent);
    }


    /***Setup SpinnerAccount**/
    private void setupSpinnerAccount() {

        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();

        String[] mSpinnerAccountCol = new String[]{"_id",AccountBookContract.AccountUserBook.COLUMN_NAME};
        Cursor cursorAccount = db.query(
                AccountBookContract.AccountUserBook.TABLE_NAME,
                 mSpinnerAccountCol,
                null,
                null,
                null,
                null,
                null);

        String[] adapterAccountCol = new String[]{AccountBookContract.AccountUserBook.COLUMN_NAME};

        int[] adapterAccountRowView =  new  int[]{android.R.id.text1};
        SimpleCursorAdapter cursorAccountAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursorAccount,
                adapterAccountCol,
                adapterAccountRowView, 0
        );

        cursorAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccount.setAdapter(cursorAccountAdapter);

        db.close();


    }
    /***ens Setup Spinner Account**/

    public void setupSpinnerCategory() {


        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();
        String nameTable = AccountBookContract.Category.TABLE_NAME_CATEGORY;


        String[] mSpinnerCategoryCol = new String[]{AccountBookContract.Category._ID, AccountBookContract.Category.COLUMN_NAME_CATEGOTY};
        Cursor cursorCategory = db.query(nameTable,
                mSpinnerCategoryCol,
                null,
                null,
                null,
                null,
                null);

        String[] adapterCategoryCol = new String[]{AccountBookContract.Category.COLUMN_NAME_CATEGOTY};

        int[] adapterCategoryRowView =  new  int[]{android.R.id.text1};
        SimpleCursorAdapter cursorCategoryAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursorCategory,
                adapterCategoryCol,
                adapterCategoryRowView, 0
        );

        cursorCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(cursorCategoryAdapter);

        db.close();
    }
    /***END Setup Spinner Category**/

    /**************************** ОПЕРАЦИИ с ДАТОЙ и ВРЕМЕНЕМ**************************************/

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddCost.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(AddCost.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };


    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE  | DateUtils.FORMAT_SHOW_YEAR   ));

        currentTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),    DateUtils.FORMAT_SHOW_TIME   ));


    }

    /**************************** END ОПЕРАЦИИ с ДАТАОЙ и ВРЕМЕНЕМ**************************************/

    /** Spinner */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        positionAccount =position;
        String  selection =   parent.getItemAtPosition(position).toString();
         String ssss = Integer.toString(positionAccount);



//        Toast toast = Toast.makeText(this, selection + " позиция " + " "+ position+ " " + "id = " + id+" ", Toast.LENGTH_SHORT);
//        toast.show();
//        positionAccount=position;
//        Toast.makeText(this,ssss,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,ssss,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

/** Сохраняем Cost**/
    private void  SaveCost() {


        String query;
        String tabelCategory;
        String tabelAccount;
        String kod_operation = "0"; //  затраты  = 0, приход = 1
        String mIncomCategory = "999"; // посольку  это затраты



        int posID_C =positionCategory+1;
        int posID_A = positionAccount+1;

        String saveDate = currentDate.getText().toString().trim();
        String saveTime = currentTime.toString().trim();

        String saveCostCategory = Integer.toString(posID_C);
        String saveCostSumm = mAddCostSum.getText().toString().trim();
        String saveCostAccount = Integer.toString(posID_A);
        String saveCostComment = mComment.getText().toString().trim();


        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AccountBookContract.Costs.COLUMN_COSTS_DATE,saveDate);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_TIME,saveTime);
        cv.put(AccountBookContract.Costs.COLUMN_COST_CATEGORY,saveCostCategory);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_SUM,saveCostSumm);
        cv.put(AccountBookContract.Costs.COLUMN_COST_ACCOUNT,saveCostAccount);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_COMMENT,saveCostComment);
        cv.put(AccountBookContract.Costs.COLUMN_INCOM_CATEGORY,mIncomCategory);
        cv.put(AccountBookContract.Costs.COLUM_ID_OPERATION,kod_operation);

        db.insert(AccountBookContract.Costs.TABLE_NAME_COSTS,null,cv);
        db.close();
        //пересчет остатка по счету
        RecalculationaAccountBalance(posID_A,saveCostSumm);




    }

    private AccountBook RecalculationaAccountBalance(int posID_A, String sumCost ) {


        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();
        String query;
        query = "SELECT * FROM "+ AccountBookContract.AccountUserBook.TABLE_NAME + " WHERE _ID="+ posID_A;
        Cursor cursor = db.rawQuery(query,null);
        AccountBook receivedAccountBook = new AccountBook();

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            receivedAccountBook.setAccountID(cursor.getLong(cursor.getColumnIndex(AccountBookContract.AccountUserBook._ID)));
            receivedAccountBook.setAccountName(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_NAME)));
            receivedAccountBook.setAccountBalance(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM)));
        }

        String sumBegin = receivedAccountBook.getAccountBalance().toString().trim();

//       int balanceBegin = Integer.parseInt(sumBegin);
         float balanceBegin = Float.parseFloat(sumBegin);



//       String sumOperation = mAddCostSum.toString().trim();

       float  balasceOperation =Float.parseFloat(sumCost);
       float balanceEnd = balanceBegin-balasceOperation;

       ContentValues cv = new ContentValues();
       cv.put(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM, Float.toString(balanceEnd));
       db.update(AccountBookContract.AccountUserBook.TABLE_NAME,cv,"_ID=?",new String[]{Integer.toString(posID_A)});


        db.close();

        return receivedAccountBook;

    }

//    private Category getCostCategory(int posID) {
//        String query;
//        SQLiteDatabase db = new AccountBookDbHelper(this).getReadableDatabase();
//        query  = "SELECT  * FROM " + AccountBookContract.Category.TABLE_NAME_CATEGORY + " WHERE _ID="+ posID;
//        Cursor cursor = db.rawQuery(query,null);
//        Category receivedCategory = new Category();
//
//        if (cursor.getCount()>0){
//            cursor.moveToFirst();
//            receivedCategory.setCategoryID( cursor.getLong(cursor.getColumnIndex(AccountBookContract.Category._ID)) );
//            receivedCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(AccountBookContract.Category.COLUMN_NAME_CATEGOTY)));
//
//        }
//
//
//
//      // db.close();
//
//        return receivedCategory;
//
//    }


}
