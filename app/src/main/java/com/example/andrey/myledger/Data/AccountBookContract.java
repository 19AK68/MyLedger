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

        public final static String SQL_CREATE_ACCOUNT_BOOK_TABLE = "CREATE TABLE "+ TABLE_NAME +"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_BALANCE_SUM +" INTEGER NOT NULL DEFAULT 0); ";



    }


    public static final class Costs implements BaseColumns {

        public final static String TABLE_NAME_COSTS = "Costs";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_COSTS_DATE = "date";
        public final static String COLUMN_COSTS_TIME = "time";
        public final static String COLUMN_COST_CATEGORY = "category";
        public final static String COLUMN_COSTS_SUM = "sum";
        public final static String COLUMN_COST_ACCOUNT = "account_cost";
        public final static String COLUMN_COSTS_COMMENT = "comment"; //
        public final static String COLUMN_INCOM_CATEGORY = "incom_category";
        public final static String COLUM_ID_OPERATION = "id_operation";




        public final static String SQL_CREATE_COSTS_TABLE = " CREATE TABLE " + TABLE_NAME_COSTS + "("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COSTS_DATE + " TEXT NOT NULL,"
                + COLUMN_COSTS_TIME + " TEXT NOT NULL,"
                + COLUMN_COST_CATEGORY + " TEXT NOT NULL,"
                + COLUMN_COSTS_SUM + " INTEGER NOT NULL DEFAULT 0,"
                + COLUMN_COST_ACCOUNT  + " TEXT NOT NULL,"
                + COLUMN_COSTS_COMMENT + " TEXT NOT NULL, "
                + COLUMN_INCOM_CATEGORY + " TEXT NOT NULL, "
                + COLUM_ID_OPERATION + " TEXT NOT NULL ); ";



    }

    public static final class Category implements BaseColumns {

        public final static String TABLE_NAME_CATEGORY = "category";

        public final static  String _ID = BaseColumns._ID;
        public final static  String COLUMN_NAME_CATEGOTY = "name_category";

        public final static String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE "+ TABLE_NAME_CATEGORY +"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_CATEGOTY +" TEXT NOT NULL ); ";


    }

    public  static  final class IncomCategory implements BaseColumns {

        public  final  static String TABLE_NAME_INCOMCATEFORY = "incomcategory";
        public final static  String _ID = BaseColumns._ID;
        public final static  String COLUMN_NAME_INCOMCATEGOTY = "name_incomcategory";

        public final static String SQL_CREATE_INCOMCATEGORY_TABLE = "CREATE TABLE "+ TABLE_NAME_INCOMCATEFORY +"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_INCOMCATEGOTY +" TEXT NOT NULL ); ";


    }



}
