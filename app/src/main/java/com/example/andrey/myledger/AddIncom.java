package com.example.andrey.myledger;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;
import com.example.andrey.myledger.Model.AccountBook;

import java.security.PrivateKey;
import java.util.Calendar;

public class AddIncom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView mCurrentIncomDate;
    private TextView mCurrentIncomTime;
    private TextView mIncomCategory;

    private EditText mEditIncomSum;
    private EditText mEditIncomComment;



    private EditText mAddIncomSum;
    private EditText mIncomComment ;

    private Button mBtnSaveIncom;
    private int positionIncomCategory = 0;
    private int positionIncomAccount = 0;


    Spinner mSpinnerIncomCategory;
    Spinner mSpinnerIncomAccount;

    Calendar mIncomDateAndTime=Calendar.getInstance();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_incom);


        // init  Date and Time

        mCurrentIncomDate=(TextView)findViewById(R.id.addDataIncom);
        mCurrentIncomTime=(TextView) findViewById(R.id.addTimeIncom);
        //  set Date and Time
        setInitialIncomDateTime();
        ///////


        // init Category

        mIncomCategory = (TextView) findViewById(R.id.tvIncomCategory);
        mSpinnerIncomCategory = (Spinner)findViewById(R.id.spinnerIncomCategory);

        // int Account


        mSpinnerIncomAccount = (Spinner)findViewById(R.id.spinnerIncomAccount);




        //int incom sum

        mAddIncomSum =  (EditText)findViewById(R.id.etAddIncomSum);

            //formmat sum

        mAddIncomSum.addTextChangedListener(new TextWatcher(){

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

        // incom comment

        mIncomComment =  (EditText)findViewById(R.id.etAddIncomСomment);

        // int incom button

        mBtnSaveIncom =  (Button) findViewById(R.id.btnSaveIncom);


        /****************** SPINNER INCOM CATEGORY***************************************/


        setupSpinnerIncomCategory();
        mSpinnerIncomCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  selection =   parent.getItemAtPosition(position).toString();
                positionIncomCategory = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /****************** END spinner incom Category***************************************/

        /****************** SPINNER INCOM ACCOUNT*******************************************/


        setupSpinnerIncomAccount();
        mSpinnerIncomAccount.setOnItemSelectedListener(this);


        /****************** END spinner incom Account***************************************/

        mBtnSaveIncom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveIncom();
                gotoMainActivity();

            }
        });


    }




    /*****end Create*******************************************************************************/


    /**************************** INCOM ОПЕРАЦИИ с ДАТОЙ и ВРЕМЕНЕМ **************************************/

    /*** НАЧАЛЬНІЕ ЗНАЧЕНИЯ ВРЕМЕНИ И ДАТЫ INCOM*/
    private void setInitialIncomDateTime() {

        mCurrentIncomDate.setText(DateUtils.formatDateTime(this,
                mIncomDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE  | DateUtils.FORMAT_SHOW_YEAR   ));

        mCurrentIncomTime.setText(DateUtils.formatDateTime(this,
                mIncomDateAndTime.getTimeInMillis(),    DateUtils.FORMAT_SHOW_TIME   ));

    }



    public void setIncomDate(View v) {
        new DatePickerDialog(AddIncom.this, d,
                mIncomDateAndTime.get(Calendar.YEAR),
                mIncomDateAndTime.get(Calendar.MONTH),
                mIncomDateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setIncomTime(View v) {
        new TimePickerDialog(AddIncom.this, t,
                mIncomDateAndTime.get(Calendar.HOUR_OF_DAY),
                mIncomDateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mIncomDateAndTime.set(Calendar.YEAR, year);
            mIncomDateAndTime.set(Calendar.MONTH, monthOfYear);
            mIncomDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialIncomDateTime();
        }
    };


    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mIncomDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mIncomDateAndTime.set(Calendar.MINUTE, minute);
            setInitialIncomDateTime();
        }
    };




    /****************************INCOM END ОПЕРАЦИИ с ДАТОЙ и ВРЕМЕНЕМ**************************************/

    /****Setup Spinner Incom Category *******/

    private void setupSpinnerIncomCategory() {


        ArrayAdapter categoryIncomSpinerAdapter = ArrayAdapter.createFromResource(
                this,R.array.array_incom_options, android.R.layout.simple_spinner_item );

        categoryIncomSpinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSpinnerIncomCategory.setAdapter(categoryIncomSpinerAdapter);
        mSpinnerIncomCategory.setSelection(1);

    }

    /****END setup Spinner Incom Category *******/

    ////////////////////////////////////////////////////

    /****Setup Spinner Incom Account *******/

    private  void setupSpinnerIncomAccount (){

        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();

        String[] mSpinnerAccountCol = new String[]{"_id", AccountBookContract.AccountUserBook.COLUMN_NAME};
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
        mSpinnerIncomAccount.setAdapter(cursorAccountAdapter);

        db.close();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        positionIncomAccount =position;
        String  selection =   parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /****END setup Spinner Incom Account *******/

    /***Сохраняем операцию SaveIncom**/

    private void SaveIncom() {

        String quiery;
        String mCostCategory = "98";
        String kod_operation = "1";

        int posID_IncomCategory =positionIncomCategory+1;
        int posID_IncomAccount = positionIncomAccount+1;

        String saveIncomDate = mCurrentIncomDate.getText().toString().trim();
        String saveIncomTime = mCurrentIncomTime.getText().toString().trim();

        String saveIncomCategoty = Integer.toString(posID_IncomCategory);
        String saveIncomAccount = Integer.toString(posID_IncomAccount);

        String saveIncomSumm = mAddIncomSum.getText().toString().trim();
        String saveIncomComment = mIncomComment.getText().toString().trim();

        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(AccountBookContract.Costs.COLUMN_COSTS_DATE,saveIncomDate);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_TIME,saveIncomTime);
        cv.put(AccountBookContract.Costs.COLUMN_COST_CATEGORY,mCostCategory);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_SUM,saveIncomSumm);
        cv.put(AccountBookContract.Costs.COLUMN_COST_ACCOUNT,saveIncomAccount);
        cv.put(AccountBookContract.Costs.COLUMN_COSTS_COMMENT,saveIncomCategoty);
        cv.put(AccountBookContract.Costs.COLUMN_INCOM_CATEGORY,saveIncomComment);
        cv.put(AccountBookContract.Costs.COLUM_ID_OPERATION,kod_operation);

        db.insert(AccountBookContract.Costs.TABLE_NAME_COSTS,null,cv);

       db.close();

        //пересчет остатка по счету
        IncomRecalculationaAccountBalance(posID_IncomAccount,saveIncomSumm);


    }

    private AccountBook IncomRecalculationaAccountBalance(int posID_incomAccount, String incomSumm) {

        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();
        String query;

        query = "SELECT * FROM "+ AccountBookContract.AccountUserBook.TABLE_NAME + " WHERE _ID="+ posID_incomAccount;
        Cursor cursor = db.rawQuery(query,null);

        AccountBook receivedAccountBook = new AccountBook();


        if (cursor.getCount()>0){
            cursor.moveToFirst();
            receivedAccountBook.setAccountID(cursor.getLong(cursor.getColumnIndex(AccountBookContract.AccountUserBook._ID)));
            receivedAccountBook.setAccountName(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_NAME)));
            receivedAccountBook.setAccountBalance(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM)));
        }

        String sumBegin = receivedAccountBook.getAccountBalance().toString().trim();


        float balanceBegin = Float.parseFloat(sumBegin);





        float  balasceOperation =Float.parseFloat(incomSumm);
        float balanceEnd = balanceBegin+balasceOperation;

        ContentValues cv = new ContentValues();
        cv.put(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM, Float.toString(balanceEnd));
        db.update(AccountBookContract.AccountUserBook.TABLE_NAME,cv,"_ID=?",new String[]{Integer.toString(posID_incomAccount)});



        db.close();
        return receivedAccountBook;

    }


    /** end SaveIncom**/


    /**Возвращаемся в начало**/
    private void gotoMainActivity() {

        Intent intent = new Intent(AddIncom.this, MainActivity.class);
        startActivity(intent);
    }
}
