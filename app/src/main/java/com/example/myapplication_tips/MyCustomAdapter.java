package com.example.myapplication_tips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyCustomAdapter extends ArrayAdapter<Worker> {
    // Using Custom Layouts --> MyCustomAdapter
    // Using Custom Objects --> extends ArrayAdapter<Worker>

    private ArrayList<Worker> workerArrayList;
    Context context;

    public MyCustomAdapter(ArrayList<Worker> workerArrayList, Context context) {
        super(context, R.layout.item_list_layout, workerArrayList);
        this.workerArrayList = workerArrayList;
        this.context = context;
    }

    // View Holder Class: used to cache references to the views within
    //                    an item layout, so that they don't need to be
    //                    repeatedly looked up during scrolling

    private static class MyViewHolder{
        TextView workerDetails;
    }

    // getView(): used to create and return a view for a
    //            specific item in the list.

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1- Get the planet object for the current position
        Worker workers = getItem(position);

        // 2- Inflate Layout:
        MyViewHolder myViewHolder;
        final View result;

        if (convertView == null){
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.item_list_layout,
                    parent,
                    false
            );

            // Finding Views:
            myViewHolder.workerDetails = (TextView) convertView.findViewById(R.id.worker_details);

            result = convertView;

            convertView.setTag(myViewHolder);
        }else{
            // the view is recycled
            myViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }

        // Getting the data from model class (Worker)
        myViewHolder.workerDetails.setText(workers.toString());


        return result;

    }
}
