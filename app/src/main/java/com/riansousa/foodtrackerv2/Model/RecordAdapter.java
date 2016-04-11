package com.riansousa.foodtrackerv2.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.riansousa.foodtrackerv2.R;

import java.util.ArrayList;

/**
 * Code Pattern modeled after example at:
 * http://stackoverflow.com/questions/27818786/fetch-data-from-sqlite-and-display-in-gridview
 */
public class RecordAdapter extends BaseAdapter {
    Context context;
    ArrayList<Record> recordList;
    private static LayoutInflater inflater = null;

    /** constructor */
    public RecordAdapter(Context context, ArrayList<Record> recordList) {
        this.context = context;
        this.recordList = recordList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /** required override method from BaseAdapter */
    @Override
    public int getCount() {
        return recordList.size();
    }

    /** required override method from BaseAdapter */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /** required override method from BaseAdapter */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /** required override method from BaseAdapter */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_item, null);

        /** instantiate UI objects for manipulation */
        TextView record_time = (TextView) convertView.findViewById(R.id.record_time);
        TextView record_group = (TextView) convertView.findViewById(R.id.record_group);
        TextView record_item = (TextView) convertView.findViewById(R.id.record_item);
        TextView record_portion = (TextView) convertView.findViewById(R.id.record_portion);
        TextView record_calories = (TextView) convertView.findViewById(R.id.record_calories);

        /** get the row data */
        Record record = new Record();
        record = recordList.get(position);

        /** time is stored as date time TEXT field in SQLLite. Split to get time ONLY */
        String[] time = record.getTime().split(" ");

        /** set UI data */
        record_time.setText(time[1]);
        record_group.setText(record.getGroup());
        record_item.setText(record.getItem());
        record_portion.setText(record.getPortion());
        record_calories.setText(record.getCalories());

        return convertView;
    }
}
