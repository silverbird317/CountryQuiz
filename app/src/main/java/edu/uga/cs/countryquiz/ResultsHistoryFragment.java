package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import edu.uga.cs.countryquiz.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class ResultsHistoryFragment extends Fragment {


    private static final String TAG = "ResultsHistoryFragment";

    private ListView listView;

    private QuizHistoryData quizHistoryData = null;
    private List<QuizResult> quizResultList;
    QuizResultArrayAdapter itemsAdapter;
    private int versionNum;

    public ResultsHistoryFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_history, container, false);
    }
    /*public static edu.uga.cs.countryquiz.QuizFragment newInstance(int versionNum ) {
        edu.uga.cs.countryquiz.QuizFragment fragment = new edu.uga.cs.countryquiz.QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "versionNum", versionNum );
        fragment.setArguments( args );
        return fragment;
    }*/
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);
        quizHistoryData = new QuizHistoryData(getActivity());
        quizResultList = QuizHistoryData.quizHistory;
        itemsAdapter = new QuizResultArrayAdapter( getActivity(), quizResultList );

        // set headers
        TextView titleView = view.findViewById( R.id.questionNum );
        TextView question = view.findViewById( R.id.question );

    }
    public void onResume() {
        //Log.d( TAG, "Flow2_A.onResume()"  );
        super.onResume();
        if( quizHistoryData != null ) {
            //quizHistoryData.restorelJobLeads();
            quizResultList = QuizHistoryData.quizHistory;

            Log.d( TAG, "ReviewJobLeadsFragment.onResume(): length: " + quizResultList.size() );

            itemsAdapter = new QuizResultArrayAdapter(getActivity(), quizResultList );
            listView.setAdapter(itemsAdapter);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }
    /*public void loadQuizResults() {
        for (QuizResult q: quizResultList) {
            itemsAdapter.getView();
        }
    }*/
    public void saveNewQuizResult(QuizResult quizResult) {
        // add the new job lead
        quizResultList.add( quizResult );

        // update the list view including the new job lead
        itemsAdapter = new QuizResultArrayAdapter( getActivity(), quizResultList );
        listView.setAdapter( itemsAdapter );

        // Reposition the ListView to show the JobLead most recently added (as the last item on the list).
        // Use of the post method is needed to wait until the ListView is rendered, and only then
        // reposition the item into view (show the last item on the list).
        // the post method adds the argument (Runnable) to the message queue to be executed
        // by Android on the main UI thread.  It will be done *after* the setAdapter call
        // updates the list items, so the repositioning to the last item will take place
        // on the complete list of items.
        listView.post( new Runnable() {
            @Override
            public void run() {
                listView.smoothScrollToPosition( quizResultList.size()-1 );
            }
        } );
    }

    public static ResultsHistoryFragment newInstance() {
        ResultsHistoryFragment fragment = new ResultsHistoryFragment();
        return fragment;
    }
}