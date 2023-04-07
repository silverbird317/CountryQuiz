package edu.uga.cs.countryquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class countryListData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    public countryListData(Context context){
        this.quizDBHelper = edu.uga.cs.countryquiz.quizDBHelper.getInstance(context);
    }

    public void open() {
        db = quizDBHelper.getWritableDatabase(); //inherited from SQLiteOpenHelper
    }
    public void close(){
        if(quizDBHelper != null)
            edu.uga.cs.countryquiz.quizDBHelper.close();
    }

    public CountryList storeCountryList(CountryList countryList) {

        ContentValues values = new ContentValues();

        values.put(quizDBHelper.COUNTRYLIST_COLUMN_COUNTRY, countryList.getCountryName());
        values.put(quizDBHelper.COUNTRYLIST_COLUMN_CONTINENT, countryList.getContinentName());

        long id = db.insert(edu.uga.cs.countryquiz.quizDBHelper.TABLE_COUNTRYLIST,null,values);

        countryList.setId(id);

        return countryList;
    }

    public List<CountryList> retrieveAllCountryLists() {
        ArrayList<CountryList> countryLists = new ArrayList<>();
        Cursor cursor = null;

        cursor = db.query(edu.uga.cs.countryquiz.quizDBHelper.TABLE_COUNTRYLIST,allColumns, null, null, null);

        while(cursor.moveToNext()){
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(edu.uga.cs.countryquiz.quizDBHelper.COUNTRYLIST_COLUMN_CID));
            @SuppressLint("Range") String country = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.quizDBHelper.COUNTRYLIST_COLUMN_COUNTRY));
            @SuppressLint("Range") String continent = cursor.getString(cursor.getColumnIndex(edu.uga.cs.countryquiz.quizDBHelper.COUNTRYLIST_COLUMN_CONTINENT));

            CountryList countryList = new CountryList(country, continent);
            countryList.setId(id);
            countryList.add(countryList);
        }
    }

}

