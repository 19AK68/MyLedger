package com.example.andrey.myledger;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;

public class InfoAccountBookActivity extends Activity {



    private TextView mtvTitelInfo;
    private TextView mtvSumAccInfo;
    private TextView tvDateAccInfo;
    private TextView tvAccInfo;

    ListView lvListInfoAcc;
    SQLiteDatabase mDB;
   // AccountBookDbHelper dbHelper;
    Context mContext;
    SimpleCursorAdapter scAdapter;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_accountbook);

        mtvTitelInfo = (TextView) findViewById(R.id.textTitelInfo);
        mtvSumAccInfo = (TextView) findViewById(R.id.tvSumAccInfo);
        tvDateAccInfo = (TextView) findViewById(R.id.tvDateAccInfo);
        tvAccInfo = (TextView) findViewById(R.id.tvAccInfo);


        Intent intent = getIntent();
        String account_id = intent.getStringExtra("account_id");
        String account_name =  intent.getStringExtra("account_name");
        String account_sum = intent.getStringExtra("account_sum");


        mtvTitelInfo.setText("Счет : "+ account_name +"  ");
        mtvSumAccInfo.setText("Cумма : "+ "  "+account_sum);

        lvListInfoAcc = (ListView)  findViewById(R.id.lvAccountInfo);
        // делаем запрос и обображаем

        InfoAccountBook(account_id);




    }

    private void InfoAccountBook(String account_id) {
        mDB = new AccountBookDbHelper(this).getWritableDatabase();
        String query;
        String colDataInfo = AccountBookContract.Costs.COLUMN_COSTS_DATE;
        String colCategoryInfo =  AccountBookContract.Costs.COLUMN_COST_CATEGORY;
        String colAccInfo =  AccountBookContract.Costs.COLUMN_COST_ACCOUNT;
        String colSumm= AccountBookContract.Costs.COLUMN_COSTS_SUM;
        String colCostID = AccountBookContract.Costs._ID;
        String colCostComment = AccountBookContract.Costs.COLUMN_COSTS_COMMENT;

        String cateforyID = AccountBookContract.Category._ID;
        String nameCategory = AccountBookContract.Category.COLUMN_NAME_CATEGOTY;

        String nameAccountInfo = AccountBookContract.AccountUserBook.COLUMN_NAME;
        String tableCost = AccountBookContract.Costs.TABLE_NAME_COSTS;
        String tableCategory = AccountBookContract.Category.TABLE_NAME_CATEGORY;

        String subSelectConcat = "("
                                    + AccountBookContract.Category.COLUMN_NAME_CATEGOTY
                                    + " ||  ' '   || "
                                    + AccountBookContract.Costs.COLUMN_COSTS_COMMENT
                                    +
                                    ")"
                                    + "AS sConcatInfo";



        int idAccCol = Integer.parseInt(account_id);


//
        String quieryJoin = " SELECT "  + tableCost + "." + colCostID
                            + ", " + colDataInfo + ", "
                            + nameCategory + ", "
                            + colSumm + ", "
                            + colCostComment +", "
                            + subSelectConcat
                            + " FROM " + tableCost
                            + " INNER JOIN " + tableCategory + " ON "
                            + tableCost +"." + colCategoryInfo +  "=" + tableCategory + "." + cateforyID
                            + " WHERE "+ colAccInfo + "=" + idAccCol;



        Cursor cursor = mDB.rawQuery(quieryJoin,null);



//        String[] sAccInfo = new String[] {colDataInfo,colCategoryInfo,colSumm};

        String[] sAccInfo = new String[] {colDataInfo,"sConcatInfo",colSumm};

        int [] itemAccInfo = new int[] {R.id.tvDateAccInfo,R.id.tvAccInfo,R.id.tvSumAccInfo};






        scAdapter = new SimpleCursorAdapter(this,R.layout.item_acc_book_info,cursor,sAccInfo,
                itemAccInfo,0);
        lvListInfoAcc.setAdapter(scAdapter);




        //сlose database
        mDB.close();

    }
}


