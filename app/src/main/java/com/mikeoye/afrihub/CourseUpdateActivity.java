package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikeoye.afrihub.adapters.CoursesSelectionAdapter;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.Course;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.ConnectionHelper;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseUpdateActivity extends AppCompatActivity implements ViewOperations {

    private CoursesSelectionAdapter coursesSelectionAdapter;
    private FirebaseHelper firebaseHelper;
    private PreferenceHelper preferenceHelper;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.courses_list) RecyclerView recyclerView;
    @BindView(R.id.activity_course_update) FrameLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);

        ButterKnife.bind(this);
        setUpActivity();
        hideProgressBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseHelper.pullCoursesNotOffered(preferenceHelper.getUserID(), coursesSelectionAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.submit_course)
    public void addCourse() {
        if (!ConnectionHelper.isConnected(this)
                && !ConnectionHelper.isMobileDataConnected(this)) {
            ActivityHelper.makeSnackBar(rootLayout, "No internet connection!").show();
            return;
        }
        firebaseHelper.writeNewUserCourses(preferenceHelper.getUserID(),
                coursesSelectionAdapter.getSelectedCourses());

        final MaterialDialog progressDialog = ActivityHelper.makeProgressDialog(this, R.string.please_wait);
        progressDialog.show();

        final Activity activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.hide();
                ActivityHelper.switchToActivity(activity, MainActivity.class, true);
            }
        }, 2000);
    }

    private void setUpActivity() {
        ActivityHelper.setUpToolbar(this, toolbar, true);
        preferenceHelper = PreferenceHelper.getInstance(this);
        firebaseHelper = FirebaseHelper.getInstance(this);

        List<Course> courses = new ArrayList<>();
        coursesSelectionAdapter = new CoursesSelectionAdapter(this, courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(coursesSelectionAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
