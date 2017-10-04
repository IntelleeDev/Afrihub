package com.mikeoye.afrihub.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.adapters.CoursesOfferedAdapter;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment implements ViewOperations {

    private RecyclerView coursesOfferedRecyclerView;
    private CoursesOfferedAdapter coursesOfferedAdapter;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        ButterKnife.bind(this, view);
        coursesOfferedRecyclerView =  ButterKnife.findById(view, R.id.new_course_recycler_view);

        List<Course> offeredCourses = new ArrayList<>();
        offeredCourses.add(new Course("", "", "Oracle database", ""));
        offeredCourses.add(new Course("", "", "Oracle database", ""));
        offeredCourses.add(new Course("", "", "Oracle database", ""));
        offeredCourses.add(new Course("", "", "Oracle database", ""));
        offeredCourses.add(new Course("", "", "Oracle database", ""));
        offeredCourses.add(new Course("", "", "Oracle database", ""));

        coursesOfferedAdapter = new CoursesOfferedAdapter(getActivity(), offeredCourses);
        coursesOfferedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        coursesOfferedRecyclerView.setAdapter(coursesOfferedAdapter);

        hideProgressBar();
        return view;
    }

    @Override
    public void bindDataToFields() {

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
