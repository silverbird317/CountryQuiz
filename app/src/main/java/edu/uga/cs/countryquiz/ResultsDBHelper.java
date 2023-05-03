package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ResultsDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quizResultsDB.db";
    private static final int DB_VERSION = 1;

    //private reference to single instance
    private static ResultsDBHelper helperInstance;

    /*
     * private constructor
     */
    private ResultsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
     * public method to access the instance
     */
    public static synchronized ResultsDBHelper getInstance(Context context) {
        if(helperInstance == null) {
            helperInstance = new ResultsDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }
    /*
     * defining column names as Java constants
     */
    public static final String TABLE_RESULTSLIST = "results";
    public static final String RESULTSLIST_COLUMN_CID = "qid";
    public static final String RESULTSLIST_COLUMN_DATE = "date";
    public static final String RESULTSLIST_COLUMN_SCORE = "score";
    private static final String CREATE_RESULTSLIST =
            "create table " + TABLE_RESULTSLIST + " ("
                    + RESULTSLIST_COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RESULTSLIST_COLUMN_DATE + " TEXT, "
                    + RESULTSLIST_COLUMN_SCORE + " TEXT"
                    + ")";


    /*
     * creates database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESULTSLIST);
    }
    /*
     * updates database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_RESULTSLIST);
        onCreate(db);
    }
}
