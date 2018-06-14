package com.example.andrey.myledger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.myledger.Data.AccountBookContract;
import com.example.andrey.myledger.Data.AccountBookDbHelper;

public class ChartAccountBookActivity extends AppCompatActivity {

    float sumChart[];
    String nameCategoryChart[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_accountbook);

        Intent intent = getIntent();
        String account_id = intent.getStringExtra("account_id");


        setupPieChert(account_id);
    }

    private void setupPieChert (String account_id) {

        String tableCost = AccountBookContract.Costs.TABLE_NAME_COSTS;
        String colCostID = AccountBookContract.Costs._ID;
        String nameCategory = AccountBookContract.Category.COLUMN_NAME_CATEGOTY;
        String colSumm= AccountBookContract.Costs.COLUMN_COSTS_SUM;
        String tableCategory = AccountBookContract.Category.TABLE_NAME_CATEGORY;
        String colCategoryInfo =  AccountBookContract.Costs.COLUMN_COST_CATEGORY;
        String cateforyID = AccountBookContract.Category._ID;
        String colAccInfo =  AccountBookContract.Costs.COLUMN_COST_ACCOUNT;


        int idAccCol = Integer.parseInt(account_id);

        SQLiteDatabase db = new AccountBookDbHelper(this).getReadableDatabase();

        String quiery = "SELECT "  + tableCost + "." + colCostID
                        + ", " +  nameCategory + ", " +  colSumm
                        + " FROM " + tableCost
                        + " INNER JOIN " + tableCategory + " ON "
                        + tableCost +"." + colCategoryInfo +  "=" + tableCategory + "." + cateforyID
                         + " WHERE "+ colAccInfo + "=" + idAccCol;


        String quierySum = " SELECT "  + tableCost + "." + colCostID
                + ", " +  nameCategory + ", " + " SUM " + "("+ colSumm +")"  + ", "
                + " FROM "
                + " GROUP BY " +  nameCategory
                + " INNER JOIN " + tableCategory + " ON "
                + tableCost +"." + colCategoryInfo +  "=" + tableCategory + "." + cateforyID
                + " WHERE "+ colAccInfo + "=" + idAccCol;


/**   Cursor cursor = db.query(tableCost,
                new String [] {nameCategory,colSumm},
                );  //db.rawQuery(quiery,null);

        String rdd = "  ";1

 */







        db.close();

    }


}


