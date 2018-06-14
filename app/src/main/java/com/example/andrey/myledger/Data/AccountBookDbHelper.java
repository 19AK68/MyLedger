package com.example.andrey.myledger.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.andrey.myledger.Model.AccountBook;
import com.example.andrey.myledger.Model.Category;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.andrey.myledger.Data.AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM;
import static com.example.andrey.myledger.Data.AccountBookContract.AccountUserBook.COLUMN_NAME;
import static com.example.andrey.myledger.Data.AccountBookContract.AccountUserBook.TABLE_NAME;

public class AccountBookDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = AccountBookDbHelper.class.getSimpleName();


    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "account_book.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /** КОнструктор **/

    public AccountBookDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //запускаем создание таблицs

        db.execSQL(AccountBookContract.AccountUserBook.SQL_CREATE_ACCOUNT_BOOK_TABLE);
        db.execSQL(AccountBookContract.Costs.SQL_CREATE_COSTS_TABLE);
        db.execSQL(AccountBookContract.Category.SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(AccountBookContract.IncomCategory.SQL_CREATE_INCOMCATEGORY_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            db.execSQL("DROP TABLE IF EXISTS " +AccountBookContract.AccountUserBook.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " +AccountBookContract.Costs.TABLE_NAME_COSTS);
            db.execSQL("DROP TABLE IF EXISTS " +AccountBookContract.Category.TABLE_NAME_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " +AccountBookContract.IncomCategory.TABLE_NAME_INCOMCATEFORY);



         onCreate(db);

    }

    /**create record**/

    public void saveNewAccount(AccountBook accountBook){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AccountBookContract.AccountUserBook.COLUMN_NAME,accountBook.getAccountName());
        values.put(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM,accountBook.getAccountBalance());


        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void saveNewCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountBookContract.Category.COLUMN_NAME_CATEGOTY,category.getCategoryName());
        db.insert(AccountBookContract.Category.TABLE_NAME_CATEGORY,null,contentValues);
        db.close();
    }

    /** ЗАПРОСЫ
     * Query records, give options to filter results**/

    public List<AccountBook> accountBookList (String filter ) {

        String query;


        if (filter.equals("")){
            //regular query
            query = "SELECT * FROM " + TABLE_NAME ;
        } else {
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<AccountBook> accountBookLinkeList = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        AccountBook accountBook;

        if (cursor.moveToFirst()){
            do {

                accountBook = new AccountBook();

                accountBook.setAccountID((long) cursor.getLong(cursor.getColumnIndex(AccountBookContract.AccountUserBook._ID)));
                accountBook.setAccountName(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_NAME)));
                accountBook.setAccountBalance(cursor.getString(cursor.getColumnIndex(AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM)));

                accountBookLinkeList.add(accountBook);

            }while (cursor.moveToNext());

        }

        return accountBookLinkeList;
    }

    /**Query only 1 record**/

    public  AccountBook getAccountBook(long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _ID="+ id;
        Cursor cursor = db.rawQuery(query, null);


        AccountBook receivedAccountBook =  new AccountBook();

        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            receivedAccountBook.setAccountName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            receivedAccountBook.setAccountBalance(cursor.getString(cursor.getColumnIndex(COLUMN_BALANCE_SUM)));
        }

        return receivedAccountBook;
    }

    /**delete record**/

    public void deleteAccountBookRecord(long id,Context context){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");

        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    /**update record**/

    public void updateAccountBookRecord(long id,Context context,AccountBook updateAccountBook){


        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE  "+TABLE_NAME+" SET name ='"+ updateAccountBook.getAccountName() + "', suma ='" + updateAccountBook.getAccountBalance()+  "'  WHERE _ID='" + id + "'";

        db.execSQL(update );

        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }

    /**Arrary from Spinner***/

    public ArrayList<String>setupSpinnerCategory(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String table = AccountBookContract.Category.TABLE_NAME_CATEGORY;
        db.beginTransaction();
        try {
            String selectQuery = " SELECT * FROM " + table;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 1) {
                while (cursor.moveToNext()) {
                    String cname = cursor.getString(cursor.getColumnIndex(AccountBookContract.Category.COLUMN_NAME_CATEGOTY));
                    list.add(cname);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }

        return list;
    }



}
