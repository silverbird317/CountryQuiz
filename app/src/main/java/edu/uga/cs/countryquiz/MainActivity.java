package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    public Button startQuiz;
    public Button results;
    public static TextView test;
    private CountryListData dbhelper;
    private Context context;
    private static int entered = 0; // keep track of how many times mainActivity loaded

    /*
     * onCreate override method, loads countries in database if not loaded, clears history database is not clear
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startQuiz = findViewById(R.id.button1);
        results = findViewById(R.id.button2);

        test = findViewById(R.id.textView3);

        startQuiz.setOnClickListener(new StartListener());
        results.setOnClickListener(new ResultListener());

        context = this;

        entered++;

        new CountryDBReader().execute();
        new ResultsDBReader().execute();
    }

    /*
     * button listener for start quiz button
     */
    public class StartListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("start clicked");
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        }
    }

    /*
     * button listener for results button
     */
    public class ResultListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("results clicked");
            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
            startActivity(intent);
        }
    }

    /*
     * asynchronous call to open countries database
     */
    public class CountryDBReader extends AsyncTask<Void, Void, List<QuizResult>> {
        @Override
        protected List<QuizResult> doInBackground(Void... params ) {
            List<QuizResult> quizResults = new ArrayList<QuizResult>();
            if (CountryListData.getCountryCount() == 0) {
                dbhelper = new CountryListData(context);
                dbhelper.open();

                Log.d("COUNT", "Country Count = " + dbhelper.getCountryCount());
                QuizFragment.setCountries(dbhelper.retrieveAllCountryLists());
            }
            return quizResults;
        }
    }

    /*
     * asynchronous call to open and clear results database
     */
    public class ResultsDBReader extends AsyncTask<Void, Void, List<QuizResult>> {
        @Override
        protected List<QuizResult> doInBackground(Void... params ) {
            List<QuizResult> quizResults = new ArrayList<QuizResult>();
            QuizHistoryData data = new QuizHistoryData(context);
            data.open();
            Log.d("QUIZSIZESTART", data.retrieveQuizResults().size() + "");
            if (entered == 1 && data.retrieveQuizResults().size() > 0) {
                data.clear();
                Log.d("QUIZSIZENEW", data.retrieveQuizResults().size() + "");
            }
            return quizResults;
        }
    }
}