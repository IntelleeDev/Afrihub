package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikeoye.afrihub.commons.Constants;
import com.mikeoye.afrihub.commons.FirebaseConstants;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.models.User;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;
import com.squareup.picasso.Picasso;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileUpdateActivity extends AppCompatActivity implements ViewOperations {

    private static final String TAG = ProfileUpdateActivity.class.getSimpleName();
    private String imageString;

    @BindView(R.id.profile_image)         ImageView profileImageView;
    @BindView(R.id.student_fullname)      EditText fullnameEditText;
    @BindView(R.id.student_email)         EditText emailAddressEditText;
    @BindView(R.id.student_phone_number)  EditText phoneNumberEditText;
    @BindView(R.id.select_prof_image)     ImageView selectProfilePicButton;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private String profileImageString;
    private FirebaseHelper firebaseHelper;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        ButterKnife.bind(this);
        ActivityHelper.setUpToolbar(this, toolbar, true);

        firebaseHelper = FirebaseHelper.getInstance(this);
        preferenceHelper = PreferenceHelper.getInstance(this);

        profileImageString = preferenceHelper.getProfileImageString();
        if (!profileImageString.equals("")) {
            profileImageView.setImageBitmap(BitmapFactory.decodeFile(profileImageString));
        }
    }

    @Override

    public void onStart() {
        super.onStart();
        bindProfileDataToFields();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    @OnClick(R.id.update_profile_button)
    public void updateUserProfileInfo() {

        String fullname  = fullnameEditText.getText().toString();
        String emailAddress = emailAddressEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        String userId = preferenceHelper.getUserID();
        firebaseHelper.updateUserProfile(userId, fullname, emailAddress, phoneNumber);

        preferenceHelper.storeProfileImageString(imageString);

        final Activity activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityHelper.closeActivity(activity);
            }
        }, 1200);
    }

    private void bindProfileDataToFields() {
        preferenceHelper = PreferenceHelper.getInstance(this);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference
                .child(FirebaseConstants.USERS_CHILD)
                .child(preferenceHelper.getUserID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hideProgressBar();
                        if (dataSnapshot != null) {
                            User userProfile = dataSnapshot.getValue(User.class);
                            fullnameEditText.setText(userProfile.getUsername());
                            emailAddressEditText.setText(userProfile.getEmailAddress());
                            phoneNumberEditText.setText(userProfile.getPhoneNumber());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constants.CHOOSE_PROFILE_IMAGE_REQUEST
                    && resultCode == RESULT_OK
                    && data != null)
            {
                Uri imageUri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver()
                        .query(imageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageString = cursor.getString(columnIndex);
                cursor.close();

                profileImageView.setImageBitmap(BitmapFactory.decodeFile(imageString));
            }
        }
        catch(Exception exception) {
            Log.d(TAG, exception.getMessage());
        }
    }

    @OnClick(R.id.select_prof_image)
    public void selectProfileImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constants.CHOOSE_PROFILE_IMAGE_REQUEST);

    }
}
