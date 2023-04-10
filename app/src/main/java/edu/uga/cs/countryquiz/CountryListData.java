package edu.uga.cs.countryquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CountryListData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    private static int countryCount;

    Context context;

    public CountryListData(Context context){
        this.quizDBHelper = edu.uga.cs.countryquiz.QuizDBHelper.getInstance(context);
        this.context = context;
    }

    public void open() {
        db = quizDBHelper.getWritableDatabase(); //inherited from SQLiteOpenHelper
        loadCountries();
    }
    public void close(){
        if(quizDBHelper != null)
            quizDBHelper.close();
    }

    public CountryList storeCountryList(CountryList countryList, int i) {

        ContentValues values = new ContentValues();

        //Log.d("humph", i + ": " + countryList.getCountry());

        values.put(QuizDBHelper.COUNTRYLIST_COLUMN_COUNTRY, countryList.getCountry());
        values.put(QuizDBHelper.COUNTRYLIST_COLUMN_CONTINENT, countryList.getContinent());

        long id = db.insert(edu.uga.cs.countryquiz.QuizDBHelper.TABLE_COUNTRYLIST,null,values);

        countryList.setCid(id);

        return countryList;
    }

    public List<CountryList> retrieveAllCountryLists() {
        ArrayList<CountryList> countryLists = new ArrayList<>();

        Cursor cursor = null;

        cursor = db.query(edu.uga.cs.countryquiz.QuizDBHelper.TABLE_COUNTRYLIST, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_CID));
            @SuppressLint("Range") String country = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_COUNTRY));
            @SuppressLint("Range") String continent = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_CONTINENT));

            //Log.d("Country", id + ": " + country);
            CountryList countryList = new CountryList(country, continent);
            countryList.setCid(id);
            countryLists.add(countryList);
        }
        return countryLists;
    }

    public List<CountryList> loadCountries() {
        ArrayList<CountryList> countryLists = new ArrayList<>();

        db.beginTransaction();
        InputStream in_s = context.getResources().openRawResource( R.raw.country_continent );

        try {
            // read the CSV data
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            int i = 1;
            while ((nextRow = reader.readNext()) != null) {

                String country = nextRow[0];
                String continent = nextRow[1];

                CountryList countryList = new CountryList(country, continent);
                countryLists.add(countryList);
                countryCount++;

                storeCountryList(countryList, i);
                i++;
            }


            return countryLists;
        } catch (Exception e) {
            Log.e( "ERROR", e.toString() );
        }
        return null;
    }

    public static int getCountryCount () {
        return countryCount;
    }

}

