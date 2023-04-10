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

        new JobLeadDBReader().execute();
    }

    public class StartListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("start clicked");
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        }
    }

    public class ResultListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("results clicked");
            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
            startActivity(intent);
        }
    }

    public class JobLeadDBReader extends AsyncTask<Void, Void, List<QuizResult>> {
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
}