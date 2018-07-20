package com.example.andrey.myledger;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.andrey.myledger.R.id.add_incom_container;

public class AddIncom extends FragmentActivity implements  IAddCategorytDialFrag, AdapterView.OnItemSelectedListener {

    private AddIncomDialogFragment addIncomDialogFragmet = new AddIncomDialogFragment();

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

    private ArrayAdapter<String> listIncomCategoryAdapter;

    private ImageButton imageButtonAdd;
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

        // int imageButtonA

        imageButtonAdd = (ImageButton)findViewById(R.id.imageButtonAddIncomCategory);

        // incom comment

        mIncomComment =  (EditText)findViewById(R.id.etAddIncomСomment);

        // int incom button

        mBtnSaveIncom =  (Button) findViewById(R.id.btnSaveIncom);


        /****************** SPINNER INCOM CATEGORY***************************************/


        initSpinner();




        mSpinnerIncomCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  selection =   parent.getItemAtPosition(position).toString();
                positionIncomCategory = position;

             //   if (spinnerSize == position + 1){

             //       Toast.makeText(getApplicationContext(),"НАДО БЫ ДОБАВИТь КАТЕГОРИЮ",Toast.LENGTH_LONG).show();

//                    FragmentManager dialogFragManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = dialogFragManager.beginTransaction();
//                    fragmentTransaction.add(R.id.add_incom_container, new AddIncomDialogFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                    addIncomDialogFragmet.show(getFragmentManager(),"addDialog");



            //    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /****************** END spinner incom Category***************************************/


        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.add_incom_container, new AddIncomCategory());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




            }
        });

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

    public void initSpinner() {
        String[] spinnerIncomCategoryLists =  setupSpinnerIncomCategory();

        ArrayAdapter<String> spinnerImCategoryAdapter = new ArrayAdapter<String>(AddIncom.this,android.R.layout.simple_spinner_item,spinnerIncomCategoryLists);
        //spinnerImCategoryAdapter.setNotifyOnChange(true);

        mSpinnerIncomCategory.setAdapter(spinnerImCategoryAdapter);
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

    public String [] setupSpinnerIncomCategory() {


//        ArrayAdapter categoryIncomSpinerAdapter = ArrayAdapter.createFromResource(
//                this,R.array.array_incom_options, android.R.layout.simple_spinner_item );
//
//        categoryIncomSpinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        mSpinnerIncomCategory.setAdapter(categoryIncomSpinerAdapter);
//        mSpinnerIncomCategory.setSelection(1);

        SQLiteDatabase db = new AccountBookDbHelper(this).getWritableDatabase();

        ArrayList<String> listInCategorySpinner = new ArrayList<String>();

        String nameColInCategory = AccountBookContract.IncomCategory.COLUMN_NAME_INCOMCATEGOTY ;


        String[] mSpinnerIncomCategoryCol = new String[]{"_id",nameColInCategory};
        Cursor cursorIncomCamegory = db.query(
                AccountBookContract.IncomCategory.TABLE_NAME_INCOMCATEFORY,
                mSpinnerIncomCategoryCol,
                null,
                null,
                null,
                null,
                null
        );

        if (cursorIncomCamegory.moveToFirst()) {

            do{

                String addInCategorySpinner = cursorIncomCamegory.getString(cursorIncomCamegory.getColumnIndex(nameColInCategory));
                listInCategorySpinner.add(addInCategorySpinner);


            }while (cursorIncomCamegory.moveToNext());

        }

        db.close();

       // listInCategorySpinner.add("Добавить");
        String [] allInCategorySpinner = new String[listInCategorySpinner.size()];
        allInCategorySpinner  = listInCategorySpinner.toArray(allInCategorySpinner);

         return allInCategorySpinner;

        /**
        String [] adapterIncomCategoryCol = new String[]{AccountBookContract.IncomCategory.COLUMN_NAME_INCOMCATEGOTY};
        int []  adapterIncomCategoryRowView = new int[] {android.R.id.text1};

        SimpleCursorAdapter cursorIncomCategoryAdapter= new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursorIncomCamegory,
                adapterIncomCategoryCol,
                adapterIncomCategoryRowView, 0
        );

        cursorIncomCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerIncomCategory.setAdapter(cursorIncomCategoryAdapter);

         db.close();
        */


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
        String mCostCategory = "998";
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Toast.makeText(this,"Вызов функции",Toast.LENGTH_LONG).show();
       // AddNewToDBIncomCategory();
        return;
    }



    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        Toast.makeText(this,"Ничего не делаем",Toast.LENGTH_LONG).show();
        return;
    }




}


