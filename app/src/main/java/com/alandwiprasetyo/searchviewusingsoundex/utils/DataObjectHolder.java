package com.alandwiprasetyo.searchviewusingsoundex.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alandwiprasetyo.searchviewusingsoundex.R;

/**
 * Created by root on 19/07/16.
 */
public class DataObjectHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener {
    public TextView label;
    public TextView value;

    public DataObjectHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.name_course);
        value = (TextView) itemView.findViewById(R.id.textView2);
        Log.i("Test", "Adding Listener");
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

//            myClickListener.onItemClick(getAdapterPosition(), v);
    }
}