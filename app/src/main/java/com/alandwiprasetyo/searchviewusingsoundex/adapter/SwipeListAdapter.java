package com.alandwiprasetyo.searchviewusingsoundex.adapter;

/**
 * Created by root on 19/07/16.
 */
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import com.alandwiprasetyo.searchviewusingsoundex.utils.DataObjectHolder;
import com.alandwiprasetyo.searchviewusingsoundex.R;
import com.alandwiprasetyo.searchviewusingsoundex.algoritmn.Levenshtein;
import com.alandwiprasetyo.searchviewusingsoundex.algoritmn.Soundex;
import com.alandwiprasetyo.searchviewusingsoundex.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 26/12/15.
 */
public class SwipeListAdapter extends RecyclerView
        .Adapter<DataObjectHolder> implements Filterable {
    private Activity activity;
    private List<Word> orig;
    private List<Word> wordList = new ArrayList<>();
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    public SwipeListAdapter(Activity activity, List<Word> wordList) {
        this.activity = activity;
        this.wordList = wordList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Word> results = new ArrayList<>();
                if (orig == null)
                    orig = wordList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Word g : orig) {
                            if (g.value.contains(Soundex.soundex(constraint.toString())))
                                results.add(g);
                            else if(Levenshtein.getLevenshteinDistance(g.name.toLowerCase(),constraint.toString()) < 5)
                                results.add(g);
                            else if (g.name.toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                wordList = (ArrayList<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(wordList.get(position).name);
        holder.value.setText(wordList.get(position).value);
    }

    public void addItem(Word dataObj, int index) {
        wordList.add(index, dataObj);
        notifyItemInserted(index);
    }

    @Override
    public int getItemCount() {
        return wordList == null ? 0:wordList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
