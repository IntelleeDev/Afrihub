package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikeoye.afrihub.adapters.CoursesSelectionAdapter;
import com.mikeoye.afrihub.commons.Constants;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.models.Course;
import com.mikeoye.afrihub.utils.ActivityHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseSelectionActivity extends AppCompatActivity implements ViewOperations {

    private CoursesSelectionAdapter coursesSelectionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseHelper firebaseHelper;

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.activity_course_selection) FrameLayout rootLayout;
    @BindView(R.id.course_list_recycler_view) RecyclerView courseListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        ButterKnife.bind(this);
        loadUpCourseSelectionAdapter();
        hideProgressBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseHelper.pullOfferedCourses(coursesSelectionAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void loadUpCourseSelectionAdapter() {
        firebaseHelper = FirebaseHelper.getInstance(this);
        layoutManager = new LinearLayoutManager(this);

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Monday", "ORA 101", "Oracle Database Administration 1", "9:00am"));
        courses.add(new Course("Monday", "ORA 101", "Programming JavaScript Applications", "9:00am"));
        courses.add(new Course("Monday", "ORA 101", "Artificial Intelligence with Python", "9:00am"));

        coursesSelectionAdapter = new CoursesSelectionAdapter(this, courses);
        courseListRecyclerView.setLayoutManager(layoutManager);
        courseListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        courseListRecyclerView.setAdapter(coursesSelectionAdapter);
    }

    @OnClick(R.id.continue_button)
    public void postStudentCourses() {
        showProgressBar();

        String[] selectedCourses = coursesSelectionAdapter.getSelectedCourses();
        if (selectedCourses.length <= 0) {
            ActivityHelper
                    .makeSnackBar(rootLayout, "Please select at least one course to continue")
                    .show();
            return;
        }

        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString(Constants.USER_ID);

        firebaseHelper.writeNewUserCourses(userId, coursesSelectionAdapter.getSelectedCourses());

        final Activity activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityHelper.switchToActivity(activity, MainActivity.class, true);
            }
        }, 1200);
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
