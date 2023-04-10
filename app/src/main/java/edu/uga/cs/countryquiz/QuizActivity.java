package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity {
    private Integer[] countries = new Integer[18];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        int countryCount = CountryListData.getCountryCount();
        Set<Integer> set = new Random().ints(0, countryCount).distinct()
                .limit(18)
                .boxed()
                .collect(Collectors.toSet());

        set.toArray(countries);
        int countryIndex = -1;

        for (int i = 0; i < 6; i++) {
            countryIndex = countries[i];
            Log.d("Country index", "index is "+ i + " \tCountry index is " + countryIndex);
            QuizFragment.setCountry(i, countryIndex);
            QuizFragment.setCorrectAnswer(i * 3, countryIndex);
        }

        ViewPager2 pager = findViewById(R.id.viewPager2);
        CountriesPagerAdapter adapter = new CountriesPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);
    }
}