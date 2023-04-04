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

// this class is a custom ArrayAdapter, holding JobLeads
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

        TextView companyName = itemView.findViewById(R.id.companyName);
        TextView phone = itemView.findViewById(R.id.phone);
        TextView url = itemView.findViewById(R.id.url);
        TextView comments = itemView.findViewById(R.id.comments);

        companyName.setText( jobLead.getCompanyName() );
        phone.setText( jobLead.getPhone() );
        url.setText( jobLead.getUrl() );
        comments.setText( jobLead.getComments() );

        return itemView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<QuizResult> list = new ArrayList<QuizResult>( originalValues );
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = list.size();
                    filterResults.values = list;
                }
                else{
                    List<QuizResult> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for( QuizResult jobLead : list ) {
                        if( jobLead.getCompanyName().toLowerCase().contains(searchStr)
                                || jobLead.getComments().toLowerCase().contains(searchStr) ) {
                            resultsModel.add( jobLead );
                        }
/*
                        if (jobLead.getCompanyName().regionMatches(true, i, searchStr, 0, length))
                            return true;

 */

                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                values = (ArrayList<QuizResult>) results.values;
                notifyDataSetChanged();
                clear();
                int count = values.size();
                for(int i = 0; i<count; i++){
                    add( values.get(i) );
                    notifyDataSetInvalidated();
                }
            }

        };
        return filter;
    }
}
