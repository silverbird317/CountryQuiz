package edu.uga.cs.countryquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.countryquiz.QuizResult;

// this class is a custom ArrayAdapter, holding QuizResults
public class QuizResultArrayAdapter extends ArrayAdapter<QuizResult> {

    public static final String DEBUG_TAG = "QuizResultArrayAdapter";

    private final Context context;
    private List<QuizResult> values;
    private final List<QuizResult> originalValues;

    public QuizResultArrayAdapter(Context context, List<QuizResult> values) {
        super(context, 0, new ArrayList<QuizResult>( values ));
        this.context = context;
        this.values = new ArrayList<QuizResult>( values );
        this.originalValues = new ArrayList<QuizResult>( values );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.values: object: " + values );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.originalValues: object: " + originalValues );
    }

    // this overridden method creates a single item's view, to be used in a ListView.
    // position is supplied by Android and indicates which item on the list should be rendered.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.getView: position: " + position );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.getView: values size: " + values.size() );

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.quiz_result, parent, false);

        QuizResult jobLead = values.get( position );

        TextView date = itemView.findViewById(R.id.date);
        TextView score = itemView.findViewById(R.id.score);

        date.setText("Date: " + jobLead.getDate());
        score.setText("Score: " + jobLead.getScore() + " / 6");

        return itemView;
    }
}
