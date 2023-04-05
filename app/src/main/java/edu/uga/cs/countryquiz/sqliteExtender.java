package edu.uga.cs.countryquiz;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqliteExtender extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    private static sqliteExtender instance;

    // Table name and columns
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";

    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_AGE + " INTEGER);";

    // Singleton pattern to ensure only one instance of the database helper
    public static synchronized sqliteExtender getInstance(Context context) {
        if (instance == null) {
            instance = new sqliteExtender(context.getApplicationContext());
        }
        return instance;
    }

    private sqliteExtender(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
