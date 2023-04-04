package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

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

    public ResultsHistoryFragment() {
        // Required empty public constructor
    }

    public static ResultsHistoryFragment newInstance() {
        ResultsHistoryFragment fragment = new ResultsHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_history, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        //Log.d( TAG, "onActivityCreated()" );
        super.onViewCreated(view,savedInstanceState);

        listView = getView().findViewById( R.id.listView );
        //FloatingActionButton floatingButton = getView().findViewById(R.id.floatingActionButton);
        /*floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddJobLeadDialogFragment newFragment = new AddJobLeadDialogFragment();
                newFragment.setHostFragment( ReviewJobLeadsFragment.this );
                newFragment.show( getParentFragmentManager(), null);
            }
        });*/

        quizHistoryData = new QuizHistoryData( getActivity() );

    }

    // this is a callback for the AddJobLeadDialogFragment which saves a new job lead
    // this method is called from the AddJobLeadDialogFragment on the "OK" button tap.
    public void saveNewJobLead(QuizResult jobLead) {
        // add the new job lead
        quizResultList.add( jobLead );

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.appSearchBar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search words");
        EditText searchEditText = (EditText) mSearchView.findViewById(androidx.appcompat.R.id.search_src_text );

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d( TAG, "Query submitted" );
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                itemsAdapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        //Log.d( TAG, "Flow2_A.onResume()"  );
        super.onResume();
        if( quizHistoryData != null ) {
            quizHistoryData.restorelQuizResults();
            quizResultList = quizHistoryData.getQuizResults();

            Log.d( TAG, "ResultsHistoryFragment.onResume(): length: " + quizResultList.size() );

            itemsAdapter = new QuizResultArrayAdapter(getActivity(), quizResultList );
            listView.setAdapter(itemsAdapter);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    // We need to save job leads into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        Log.d( TAG, "ResultsHistoryFragment.onPause()" );
        super.onPause();

        Log.d( TAG, "ResultsHistoryFragment.onPause(): saving job leads" );
        if( quizHistoryData != null )
            quizHistoryData.saveQuizResults();
    }
}