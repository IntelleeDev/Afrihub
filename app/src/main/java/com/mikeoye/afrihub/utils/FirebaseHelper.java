package com.mikeoye.afrihub.utils;

import android.content.ContentUris;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikeoye.afrihub.adapters.CoursesOfferedAdapter;
import com.mikeoye.afrihub.adapters.CoursesSelectionAdapter;
import com.mikeoye.afrihub.adapters.TimeTableAdapter;
import com.mikeoye.afrihub.commons.FirebaseConstants;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.Course;
import com.mikeoye.afrihub.models.Student;
import com.mikeoye.afrihub.models.TimeTable;
import com.mikeoye.afrihub.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lami on 3/1/2017.
 */

public class FirebaseHelper {

    private User user;
    private DatabaseReference databaseReference;
    private ViewOperations implementingView;

    private FirebaseHelper(ViewOperations implementingView) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.implementingView = implementingView;
    }

    public void writeNewStudent(String userId, String fullname, String email) {
        Student obj = new Student(userId, fullname, email);
        databaseReference
                .child(FirebaseConstants.STUDENTS_CHILD)
                .child(userId)
                .setValue(obj);
    }

    public void writeNewUser(String userId, String username, String password,
                             String emailAddress, String phoneNumber,
                             String profileImage)
    {
        User obj = new User(userId, username, password, emailAddress, phoneNumber, profileImage);
        databaseReference
                .child(FirebaseConstants.USERS_CHILD)
                .child(userId)
                .setValue(obj);
    }

    public void writeNewUserCourses(String userId, String[] courses) {
        DatabaseReference ref =
                databaseReference
                    .child(FirebaseConstants.STUDENTS_CHILD)
                    .child(userId)
                    .child(FirebaseConstants.COURSES_CHILD);
        for (int i = 0, N = courses.length; i < N; i++) {
            ref.push().setValue(courses[i]);
        }
    }

    public void pullOfferedCourses(final CoursesOfferedAdapter coursesOfferedAdapter) {

    }

    public void pullOfferedCourses(final CoursesSelectionAdapter adapter) {
        implementingView.showProgressBar();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses =  new ArrayList<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        courses.add(
                                new Course(
                                        snapshot.child("lectureDay").getValue().toString(),
                                        snapshot.child("courseCode").getValue().toString(),
                                        snapshot.child("courseTitle").getValue().toString(),
                                        snapshot.child("timeScheduled").getValue().toString()));
                    }
                    adapter.clear();
                    adapter.addAll(courses);
                    adapter.notifyDataSetChanged();
                    implementingView.hideProgressBar();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference
                .child(FirebaseConstants.COURSES_CHILD)
                    .addListenerForSingleValueEvent(valueEventListener);
    }

    public void pullUserRegisteredCourses(final String userId, final TimeTableAdapter adapter) {
        implementingView.showProgressBar();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Course> courses =  new ArrayList<>();
                if (dataSnapshot != null) {

                    adapter.clear();

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        databaseReference
                                .child(FirebaseConstants.COURSES_CHILD)
                                .child(snapshot.getValue().toString())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            adapter.add(dataSnapshot.getValue(Course.class));
                                            adapter.notifyDataSetChanged();
                                            implementingView.hideProgressBar();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference
                .child(FirebaseConstants.STUDENTS_CHILD)
                .child(userId)
                .child(FirebaseConstants.COURSES_CHILD)
                .addListenerForSingleValueEvent(valueEventListener);

    }

    public void updateUserProfile(String userId, String username, String emailAddress, String phoneNumber) {
        implementingView.showProgressBar();
        User userProfile = new User(userId, username, emailAddress, phoneNumber);
        Map<String, Object> profileDataUpdate = userProfile.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put(FirebaseConstants.USERS_CHILD + "/" + userId, profileDataUpdate);

        databaseReference.updateChildren(childUpdate);
    }

    public void pullCoursesNotOffered(final String userId, final CoursesSelectionAdapter adapter) {
        implementingView.showProgressBar();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Pull up all user registered courses
                final Map<String, Object> coursesRegisterd = new HashMap<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        coursesRegisterd.put(snapshot.getValue().toString(), null);
                    }

                    // Pull up all courses offered by AfriHub
                    databaseReference
                            .child(FirebaseConstants.COURSES_CHILD)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    List<Course> courses = new ArrayList<>();
                                    if (dataSnapshot != null) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            // Skip the course if its already registerd
                                            if (!coursesRegisterd.containsKey(snapshot.getKey())) {
                                                courses.add(
                                                        new Course(
                                                                snapshot.child("lectureDay").getValue().toString(),
                                                                snapshot.child("courseCode").getValue().toString(),
                                                                snapshot.child("courseTitle").getValue().toString(),
                                                                snapshot.child("timeScheduled").getValue().toString()));
                                            }
                                        }
                                        adapter.clear();
                                        adapter.addAll(courses);
                                        adapter.notifyDataSetChanged();
                                        implementingView.hideProgressBar();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference
                .child(FirebaseConstants.STUDENTS_CHILD)
                .child(userId)
                .child(FirebaseConstants.COURSES_CHILD)
                .addListenerForSingleValueEvent(valueEventListener);
    }

    public static FirebaseHelper getInstance(ViewOperations viewOperations) {
        return new FirebaseHelper(viewOperations);
    }
}
