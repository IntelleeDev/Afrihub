package com.mikeoye.afrihub.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.commons.FirebaseConstants;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.User;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ViewOperations {

    @BindView(R.id.profile_image)       ImageView profileImageView;
    @BindView(R.id.student_fullname)    TextView fullnameTextView;
    @BindView(R.id.stud_id)             TextView idTextView;
    @BindView(R.id.stud_email)          TextView emailTextView;
    @BindView(R.id.stud_phone_number)   TextView phoneNumberTextView;

    private String profileImageString;
    private PreferenceHelper preferenceHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUserProfile();
    }

    public static Fragment newInstance(int position) {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        preferenceHelper = PreferenceHelper.getInstance(getActivity());
        return view;
    }

    private void loadUserProfile() {

        profileImageString = preferenceHelper.getProfileImageString();
        if (!profileImageString.equals("")) {
            profileImageView.setImageBitmap(BitmapFactory.decodeFile(profileImageString));
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    User userProfile = dataSnapshot.getValue(User.class);

                    fullnameTextView.setText(userProfile.getUsername());
                    idTextView.setText(userProfile.getUserId());
                    emailTextView.setText(userProfile.getEmailAddress());
                    phoneNumberTextView.setText(userProfile.getPhoneNumber());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(getContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference
                .child(FirebaseConstants.USERS_CHILD)
                .child(preferenceHelper.getUserID())
                .addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void bindDataToFields() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
