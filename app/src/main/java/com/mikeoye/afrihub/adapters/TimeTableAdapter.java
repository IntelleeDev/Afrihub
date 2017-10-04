package com.mikeoye.afrihub.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.models.Course;
import com.mikeoye.afrihub.models.TimeTable;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lami on 2/24/2017.
 */

public class TimeTableAdapter extends ArrayAdapter<Course> {

    private Context mContext;

    public TimeTableAdapter(Context context, int resource, List<Course> timeTableData) {
        super(context, resource, timeTableData);
        mContext = context;
        ButterKnife.bind((AppCompatActivity) mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Course data = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.time_table_list_item, parent, false);
            viewHolder.dayTextView = ButterKnife.findById(convertView, R.id.day_text);
            //viewHolder.courseCodeTextView = ButterKnife.findById(convertView, R.id.course_code_text);
            viewHolder.courseTitleTextView = ButterKnife.findById(convertView, R.id.course_title_text);
            viewHolder.timeTextView = ButterKnife.findById(convertView, R.id.course_time_text);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dayTextView.setText(data.getLectureDay());
        //viewHolder.courseCodeTextView.setText(data.getCourseCode());
        viewHolder.courseTitleTextView.setText(data.getCourseTitle());
        viewHolder.timeTextView.setText(data.getTimeScheduled());

        return convertView;
    }

    private static class ViewHolder {
        TextView dayTextView;
        TextView courseCodeTextView;
        TextView courseTitleTextView;
        TextView timeTextView;
    }

}
