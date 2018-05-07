package com.example.andrey.myledger.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.andrey.myledger.Model.AccountBook;

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

        String SQL_CREATE_ACCOUNT_BOOK_TABLE = "CREATE TABLE "+ TABLE_NAME +"("
                + AccountBookContract.AccountUserBook._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AccountBookContract.AccountUserBook.COLUMN_NAME + " TEXT NOT NULL,"
                + AccountBookContract.AccountUserBook.COLUMN_BALANCE_SUM +" INTEGER NOT NULL DEFAULT 0); ";

        //запускаем создание таблице

        db.execSQL(SQL_CREATE_ACCOUNT_BOOK_TABLE);



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);

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
}
