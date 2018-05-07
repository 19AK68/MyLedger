package com.example.andrey.myledger.Data;

import android.provider.BaseColumns;

import com.example.andrey.myledger.Model.AccountBook;

public class AccountBookContract {

    private AccountBookContract(){}

    public static final class AccountUserBook implements BaseColumns{

        public final static String TABLE_NAME = "AccountsBook";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_BALANCE_SUM = "balance_sum";


    }
}
