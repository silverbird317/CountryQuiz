package edu.uga.cs.countryquiz;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizHistoryData {

    public static List<QuizResult> quizHistory = new ArrayList<QuizResult>();

    public static final String DEBUG_TAG = "QuizResultsData";
    private static final String QUIZRESULTSFILENAME = "quizResults.dat";
    private SQLiteOpenHelper resultsDBHelper;
    private SQLiteDatabase db;
    private List<QuizResult> quizResults;
    private Context context;
    private static boolean exists = false;

    /*
     * public constructor
     */
    public QuizHistoryData( Context context ) {
        this.context = context;
        quizResults = new ArrayList<QuizResult>();
        this.resultsDBHelper = edu.uga.cs.countryquiz.ResultsDBHelper.getInstance(context);
    }

    /*
     * open database connection
     */
    public void open() {
        db = resultsDBHelper.getWritableDatabase(); //inherited from SQLiteOpenHelper
    }

    /*
     * close database connection
     */
    public void close(){
        if(resultsDBHelper != null)
            resultsDBHelper.close();
    }

    /*
     * clear items in database
     */
    public void clear() {
        String clearDBQuery = "DELETE FROM " + ResultsDBHelper.TABLE_RESULTSLIST;
        db.execSQL(clearDBQuery);
    }

    /*
     * store a single quiz result
     */
    public QuizResult storeQuizResult(QuizResult quizResult, int i) {
        ContentValues values = new ContentValues();
        //Log.d("humph", i + ": " + countryList.getCountry());

        if (db == null) {
            db = resultsDBHelper.getWritableDatabase();
        }
        values.put(ResultsDBHelper.RESULTSLIST_COLUMN_DATE, quizResult.getDate());
        values.put(ResultsDBHelper.RESULTSLIST_COLUMN_SCORE, quizResult.getScore());

        long id = db.insert(edu.uga.cs.countryquiz.ResultsDBHelper.TABLE_RESULTSLIST,null,values);

        quizResult.setCid(id);

        return quizResult;
    }

    /*
     * return a list of all quiz results
     */
    public List<QuizResult> retrieveQuizResults() {
        ArrayList<QuizResult> quizResults = new ArrayList<>();

        if (db == null) {
            db = resultsDBHelper.getWritableDatabase();
        }

        Cursor cursor = null;
        cursor = db.query(edu.uga.cs.countryquiz.ResultsDBHelper.TABLE_RESULTSLIST, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(edu.uga.cs.countryquiz.ResultsDBHelper.RESULTSLIST_COLUMN_CID));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.ResultsDBHelper.RESULTSLIST_COLUMN_DATE));
            @SuppressLint("Range") int score = Integer.valueOf(cursor.getString(
                    cursor.getColumnIndex(
                            edu.uga.cs.countryquiz.ResultsDBHelper.RESULTSLIST_COLUMN_SCORE)));

            //Log.d("Country", id + ": " + country);

            QuizResult quizResult = new QuizResult(date, score);
            quizResult.setCid(id);
            quizResults.add(quizResult);
            Log.d("ADDED", quizResult.toString());
        }
        return quizResults;
    }
}
