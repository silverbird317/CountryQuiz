package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "countryQuizDB.db";
    private static final int DB_VERSION = 1;

    //private reference to single instance
    private static QuizDBHelper helperInstance;

    //private constructor
    private QuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //public method to access the instance
    public static synchronized QuizDBHelper getInstance(Context context) {
        if(helperInstance == null) {
            helperInstance = new QuizDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }
    //defining column names as Java constants
    public static final String TABLE_COUNTRYLIST = "countryList";
    public static final String COUNTRYLIST_COLUMN_CID = "cid";
    public static final String COUNTRYLIST_COLUMN_COUNTRY = "country";
    public static final String COUNTRYLIST_COLUMN_CONTINENT = "continent";

    private static final String CREATE_COUNTRYLIST =
            "create table" + TABLE_COUNTRYLIST + " ("
                    + COUNTRYLIST_COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRYLIST_COLUMN_COUNTRY + " TEXT, "
                    + COUNTRYLIST_COLUMN_CONTINENT + " TEXT"
                    + ")";


    //creates database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL((CREATE_COUNTRYLIST));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COUNTRYLIST);
        onCreate(db);
    }
}
