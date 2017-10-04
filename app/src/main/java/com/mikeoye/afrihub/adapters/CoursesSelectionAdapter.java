package com.mikeoye.afrihub.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.models.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by lami on 3/1/2017.
 */

public class CoursesSelectionAdapter extends RecyclerView.Adapter<CoursesSelectionAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> availableCourses;
    private List<String> userSelectedCourses;

    public CoursesSelectionAdapter(Context context, List<Course> availableCourses) {
        this.context = context;
        this.availableCourses = new ArrayList<>(availableCourses);
        userSelectedCourses = new ArrayList<>();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.course_list_item, parent, false);
        CourseViewHolder viewHolder = new CourseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        holder.textView.setText(availableCourses.get(position).getCourseTitle());
    }

    @Override
    public int getItemCount() {
        return availableCourses.size();
    }

    public void clear() {
        availableCourses.clear();
    }

    public void addAll(Collection<? extends Course> selectedCourses) {
        availableCourses.addAll(selectedCourses);
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        private TextView textView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = ButterKnife.findById(itemView, R.id.course_text);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String courseCode = availableCourses.get(position).getCourseCode();

            if (!isCourseSelected(courseCode)) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.course_item_selected));
                userSelectedCourses.add(courseCode);
                return;
            }

            textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_alpha));
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.course_item_unselected));
            textView.setText(availableCourses.get(position).getCourseTitle());
            userSelectedCourses.remove(userSelectedCourses.indexOf(courseCode));
        }
    }

    public String[] getSelectedCourses() {
        String[] result = new String[userSelectedCourses.size()];
        for (int i = 0, SIZE = userSelectedCourses.size(); i < SIZE; i++) {
            result[i] = userSelectedCourses.get(i);
        }
        return result;
    }

    private boolean isCourseSelected(String courseCode) {
        if (userSelectedCourses.contains(courseCode)) {
            return true;
        }
        return false;
    }
}
