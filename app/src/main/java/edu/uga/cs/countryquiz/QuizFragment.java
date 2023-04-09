package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


public class QuizFragment extends Fragment {

    private static String[] answerChoices = new String[3];
    private static String[] continents = {"Asia", "Africa", "Europe", "North America",
            "Oceania", "South America"};

    private static int[] countryRand = new int[6];

    private RadioButton buttons[] = new RadioButton[3];

    // which Android version to display in the fragment
    private int versionNum;

    private static List<CountryList> countries;

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

        TextView titleView = view.findViewById( R.id.questionNum );
        TextView question = view.findViewById( R.id.question );

        titleView.setText("Question " + (versionNum + 1));
        question.setText("What continent is " + countries.get(countryRand[versionNum]).getCountry() +" on?");

        buttons[0] = view.findViewById(R.id.radioButton);
        buttons[1] = view.findViewById(R.id.radioButton2);
        buttons[2] = view.findViewById(R.id.radioButton3);

        Log.d("answers", "0: " + answerChoices[0] + "\n1: " + answerChoices[1] + "\t2:" + answerChoices[2]);

        // generate non correct answers
        generateAnswers();

        Random random = new Random();
        //generate one number 0-2 to place correct answer
        int spot = random.nextInt(3);
        buttons[spot].setText(countries.get(countryRand[versionNum]).getContinent());
        //generate a number to determine the order of non correct answers
        int order = random.nextInt(2);
        spot++;
        buttons[spot % 3].setText(answerChoices[order % 2 + 1]);
        order++;
        spot++;
        buttons[spot % 3].setText(answerChoices[order % 2 + 1]);
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

    public static void setCorrectAnswer (int answer) {
        answerChoices[0] = countries.get(answer).getContinent();
    }
    public static void generateAnswers () {
        int continentID = -1;
        switch (answerChoices[0]) {
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
            answerChoices[count] = continents[(continentID + i) % 6];
            Log.d("rng",i + "");
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