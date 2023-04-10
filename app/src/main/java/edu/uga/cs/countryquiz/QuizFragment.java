package edu.uga.cs.countryquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


public class QuizFragment extends Fragment {
    // country/continent variables
    private static String[] answerChoices = new String[18];
    private static String[] continents = {"Asia", "Africa", "Europe", "North America",
            "Oceania", "South America"};
    private static int[] countryRand = new int[6];
    private static List<CountryList> countries;
    String correctAnswer = "";

    // button variables
    private RadioGroup radioGroup;
    private RadioButton buttons[] = new RadioButton[3];
    private int checked = -1; // variable for if answer is selected
    private int prevCorrect = -1; // variable for if previous answer was correct

    // footer variables
    private TextView questionsAnswered;
    private TextView questionsCorrect;
    private static int answered = 0;
    private static int correct = 0;

    // which Android version to display in the fragment
    private int versionNum;

    public QuizFragment() {
        // Required empty public constructor
    }


    public static QuizFragment newInstance( int versionNum ) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "versionNum", versionNum );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            versionNum = getArguments().getInt( "versionNum" );
        }
    }

    /*@Override
    public void onPause() {
        super.onPause();
        if (versionNum == 5) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateTime = new Date();
            String date = formatter.format(dateTime).toString();
            QuizResult result = new QuizResult(date, correct);

            QuizHistoryData.quizHistory.add(result);
            Log.d("RESULT", result.toString());
        }
    }*/

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false );
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        // set headers
        TextView titleView = view.findViewById( R.id.questionNum );
        TextView question = view.findViewById( R.id.question );

        titleView.setText("Question " + (versionNum + 1));
        question.setText("What continent is " + countries.get(countryRand[versionNum]).getCountry() +" on?");

        // set footers
        questionsAnswered = view.findViewById( R.id.questionsAnswered );
        questionsCorrect = view.findViewById( R.id.currentScore);

        questionsAnswered.setText(answered + " out of 6 questions answered");
        questionsCorrect.setText("Score: " + correct + " / 6");

        // set buttons
        buttons[0] = view.findViewById(R.id.radioButton);
        buttons[1] = view.findViewById(R.id.radioButton2);
        buttons[2] = view.findViewById(R.id.radioButton3);

        radioGroup = view.findViewById(R.id.radioGroup);
        checked = radioGroup.getCheckedRadioButtonId();

        if (versionNum == 5) {
            LinearLayout layout = view.findViewById(R.id.LinearLayout);
            Button finish = new Button(getActivity());
            finish.setText("Finish Quiz");
            finish.setOnClickListener(new FinishListener());

            layout.addView(finish);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 250, 20, 0);
            finish.setLayoutParams(params);
        }

        Log.d("answers", "0: " + answerChoices[0] + "\n1: " + answerChoices[1] + "\t2:" + answerChoices[2]);

        // generate non correct answers
        generateAnswers(versionNum);

        Random random = new Random();
        //generate one number 0-2 to place correct answer
        int spot = random.nextInt(3);
        correctAnswer = countries.get(countryRand[versionNum]).getContinent();
        buttons[spot].setText(correctAnswer);
        //generate a number to determine the order of non correct answers
        int order = random.nextInt(2);
        spot++;
        buttons[spot % 3].setText(answerChoices[order % 2 + 1]);
        order++;
        spot++;
        buttons[spot % 3].setText(answerChoices[order % 2 + 1]);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton check = (RadioButton) view.findViewById(checkedId);
                        String checkAns = check.getText().toString();

                        Log.d("Correct?", "id: " + checkedId + "\tanswer: " + checkAns);

                        // increment answered if previously not answered and increment correct if correct
                        if (checked == -1) {
                            answered++;
                        }
                        if (checkAns.equals(correctAnswer) && prevCorrect == -1) {
                            correct++;
                            prevCorrect = 0;
                        } else {
                            if (checked > -1 && prevCorrect == 0) {
                                correct--;
                                prevCorrect = -1;
                            }
                        }
                        checked = radioGroup.getCheckedRadioButtonId();
                        // change text
                        questionsAnswered.setText(answered + " out of 6 questions answered");
                        questionsCorrect.setText("Score: " + correct + " / 6");
                    }

                });
    }

    public class FinishListener implements View.OnClickListener {
        public void onClick(View view) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm");
            Date dateTime = new Date();
            String date = formatter.format(dateTime).toString();
            QuizResult result = new QuizResult(date, correct);

            answered = 0;
            correct = 0;

            QuizHistoryData.quizHistory.add(result);
            Log.d("RESULT", result.toString());

            getActivity().finish();
        }
    }

    public static int getNumberOfCountries() {
        return 6;
    }

    public static void setCountry (int index, int countryIndex) {
        countryRand[index] = countryIndex;
    }

    public static void setCountries (List<CountryList> country) {
        countries = country;
    }

    public static void setCorrectAnswer (int index, int answer) {
        answerChoices[index] = countries.get(answer).getContinent();
    }
    public static void generateAnswers (int versionNum) {
        int continentID = -1;
        switch (answerChoices[versionNum * 3]) {
            case "Asia": // 0
                continentID = 0;
                break;
            case "Africa": // 1
                continentID = 1;
                break;
            case "Europe": // 2
                continentID = 2;
                break;
            case "North America": // 3
                continentID = 3;
                break;
            case "Oceania": // 4
                continentID = 4;
                break;
            case "South America": // 5
                continentID = 5;
                break;
        }
        int count = 1;
        UniqueRng rng = new UniqueRng(5);
        while (rng.hasNext() && count < 3) {
            int i = rng.next();
            Log.d("CONTINENT", continentID + "");
            answerChoices[versionNum + count] = continents[(continentID + i) % 6];
            Log.d("Answer rng", i + "\tcontinentID: " + ((continentID + i) % 6));
            Log.d("CountryGive", continents[(continentID + i) % 6]);
            count++;
        }
    }

    public static class UniqueRng implements Iterator<Integer> {
        private List<Integer> numbers = new ArrayList<>();

        public UniqueRng(int n) {
            for (int i = 1; i <= n; i++) {
                numbers.add(i);
            }

            Collections.shuffle(numbers);
        }
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return numbers.remove(0);
        }

        @Override
        public boolean hasNext() {
            return !numbers.isEmpty();
        }
    } // generate unique random numbers in small set, used for random continents
}