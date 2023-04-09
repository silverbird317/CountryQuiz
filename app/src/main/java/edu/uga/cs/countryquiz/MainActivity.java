package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button startQuiz;
    public Button results;
    public static TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startQuiz = findViewById(R.id.button1);
        results = findViewById(R.id.button2);

        test = findViewById(R.id.textView3);

        startQuiz.setOnClickListener(new startListener());
        results.setOnClickListener(new resultListener());
    }

    public class startListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("start clicked");
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        }
    }

    public class resultListener implements View.OnClickListener {
        public void onClick(View view) {
            test.setText("results clicked");
            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
            startActivity(intent);
        }
    }
}