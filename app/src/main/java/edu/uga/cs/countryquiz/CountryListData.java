package edu.uga.cs.countryquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CountryListData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    public CountryListData(Context context){
        this.quizDBHelper = edu.uga.cs.countryquiz.QuizDBHelper.getInstance(context);
    }

    public void open() {
        db = quizDBHelper.getWritableDatabase(); //inherited from SQLiteOpenHelper
    }
    public void close(){
        if(quizDBHelper != null)
            quizDBHelper.close();
    }

    public CountryList storeCountryList(CountryList countryList) {

        ContentValues values = new ContentValues();

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

        while(cursor.moveToNext()){
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_CID));
            @SuppressLint("Range") String country = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_COUNTRY));
            @SuppressLint("Range") String continent = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.QuizDBHelper.COUNTRYLIST_COLUMN_CONTINENT));

            CountryList countryList = new CountryList(country, continent);
            countryList.setCid(id);
            countryList.setCountry(country);
            countryList.setContinent(continent);
            countryLists.add(countryList);
        }
        return countryLists;
    }

}

