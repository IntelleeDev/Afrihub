package com.mikeoye.afrihub.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.CourseDetailActivity;
import com.mikeoye.afrihub.adapters.TimeTableAdapter;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.Course;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment implements ViewOperations {

    private static final String PAGE_TITLE = "Timetable";
    private TimeTableAdapter timeTableAdapter;
    private FirebaseHelper firebaseHelper;
    private PreferenceHelper preferenceHelper;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseHelper.pullUserRegisteredCourses(preferenceHelper.getUserID(), timeTableAdapter);
    }

    public static Fragment newInstance(int position) {
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        return timeTableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        ButterKnife.bind(this, view);
        firebaseHelper = FirebaseHelper.getInstance(this);
        preferenceHelper = PreferenceHelper.getInstance(getActivity());

        hideProgressBar();

        ListView listView = ButterKnife.findById(view, R.id.time_table_list_view);

        timeTableAdapter =
                new TimeTableAdapter(getActivity(), R.layout.time_table_list_item, new ArrayList<Course>());
        listView.setAdapter(timeTableAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("COURSE_ID", "course_key");
                ActivityHelper.switchToActivity(getActivity(), CourseDetailActivity.class, bundle, false);
            }
        });
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
