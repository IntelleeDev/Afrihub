package com.mikeoye.afrihub.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.models.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lami on 3/19/2017.
 */

public class CoursesOfferedAdapter extends RecyclerView.Adapter<CoursesOfferedAdapter.CoursesOfferedViewHolder> {

    private Context context;
    private List<Course> allCoursesOffered;

    public CoursesOfferedAdapter(Context context, List<Course> coursesOffered) {
        this.context = context;
        allCoursesOffered = new ArrayList<>(coursesOffered);
    }

    @Override
    public CoursesOfferedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_course_item, parent, false);
        CoursesOfferedViewHolder viewHolder = new CoursesOfferedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoursesOfferedViewHolder holder, int position) {
        Course course = allCoursesOffered.get(position);
        holder.courseTitle.setText(course.getCourseTitle());
        holder.courseDescription.setText(context.getString(R.string.course_description));
    }

    @Override
    public int getItemCount() {
        return allCoursesOffered.size();
    }

    public class CoursesOfferedViewHolder extends RecyclerView.ViewHolder {

        private ImageView courseIcon;
        private TextView courseTitle;
        private TextView courseDescription;
        private TextView courseStatus;

        public CoursesOfferedViewHolder(View itemView) {
            super(itemView);
            courseIcon = ButterKnife.findById(itemView, R.id.course_image);
            courseTitle = ButterKnife.findById(itemView, R.id.course_title);
            courseDescription = ButterKnife.findById(itemView, R.id.course_description);
        }
    }
}
