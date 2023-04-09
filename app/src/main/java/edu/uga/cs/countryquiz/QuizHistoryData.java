package edu.uga.cs.countryquiz;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents job leads.  It also facilitates storing and restoring job leads
 * from file.
 */
public class QuizHistoryData {

    public static List<QuizResult> quizHistory = new ArrayList<QuizResult>();

    public static final String DEBUG_TAG = "QuizResultsData";
    private static final String QUIZRESULTSFILENAME = "quizResults.dat";

    private List<QuizResult> quizResults;
    private Context context;

    public QuizHistoryData( Context context ) {
        this.context = context;
        quizResults = null;
        initQuizResults();
    }

    // create/initialize the QUIZRESULTSFILENAME file, if it doesn't exist yet
    public void initQuizResults() {
        try {
            File file = context.getFileStreamPath( QUIZRESULTSFILENAME );

            if( !file.exists() ) {
                OutputStream output = context.openFileOutput( QUIZRESULTSFILENAME, MODE_PRIVATE );
                ObjectOutputStream quizResultsFile = new ObjectOutputStream( output );

                quizResults = new ArrayList<QuizResult>();
                quizResultsFile.writeObject( quizResults );

                Log.d( DEBUG_TAG, "initQuizResults: initialized file: " + QUIZRESULTSFILENAME );
            }
            else {
                Log.d( DEBUG_TAG, "initQuizResults: using existing file: " + QUIZRESULTSFILENAME );
            }
            Log.d( DEBUG_TAG, "initQuizResults: quizResults object: " + quizResults );
        }
        catch( Exception ex ) {
            Log.d( DEBUG_TAG, "initQuizResults: Exception caught: " + ex );
        }
    }

    // Restore all job leads from file.
    public void restorelQuizResults() {
        try {
            Log.d( DEBUG_TAG, "restorelQuizResults" );

            FileInputStream input = context.openFileInput( QUIZRESULTSFILENAME );
            ObjectInputStream resultsFile = new ObjectInputStream( input );

            quizResults = (List<QuizResult>) resultsFile.readObject();
            resultsFile.close();

            Log.d( DEBUG_TAG, "restorelQuizResults: Number of records restored: " + quizResults.size() );
        }
        catch( Exception ex ){
            Log.d( DEBUG_TAG, "restorelQuizResults: Exception caught: " + ex );
        }
    }

    public List<QuizResult> getQuizResults() {
        return quizResults;
    }

    // Store a new job lead.
    public void addQuizResults( QuizResult quizResult ) {
        quizResults.add( quizResult );
    }

    // Store job leads to a file
    public void saveQuizResults() {
        Log.d( DEBUG_TAG, "saveQuizResults: size: " + quizResults.size() );
        Log.d( DEBUG_TAG, "saveQuizResults: object: " + quizResults );

        try {
            FileOutputStream output = context.openFileOutput( QUIZRESULTSFILENAME, MODE_PRIVATE );
            ObjectOutputStream resultsFile = new ObjectOutputStream( output );

            resultsFile.writeObject( quizResults );
            resultsFile.close();

            Log.d( DEBUG_TAG, "saveQuizResults: job leads saved" );
        }
        catch( Exception ex ){
            Log.d( DEBUG_TAG, "saveQuizResults: Exception caught: " + ex );
        }
    }
}
