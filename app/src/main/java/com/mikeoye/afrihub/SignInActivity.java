package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikeoye.afrihub.commons.FirebaseConstants;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.models.User;
import com.mikeoye.afrihub.utils.ConnectionHelper;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInActivity extends AppCompatActivity {

    private PreferenceHelper preferenceHelper;

    @BindView(R.id.usernameEditText) EditText usernameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.error_display)    TextView errorTextView;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar spinner;
    @BindView(R.id.activity_sign_in) FrameLayout rootLayout;

    @Override
    public void onStart() {
        super.onStart();
        ActivityHelper.setUpToolbar(this, toolbar, true);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
        setUpActivity();

        if(preferenceHelper.isUserLoggedIn()) {
            ActivityHelper.switchToActivity(this, MainActivity.class, true);
        }
    }

    private void setUpActivity() {
        spinner.setVisibility(View.GONE);

        preferenceHelper = PreferenceHelper.getInstance(this);
        ActivityHelper.changeStatusBarColor(this);
        errorTextView.setText("");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.sign_in_button)
    public void authenticateUser() {
        if(!ConnectionHelper.isConnected(this)
                && !ConnectionHelper.isMobileDataConnected(this)) {
            Snackbar snackbar = ActivityHelper.makeSnackBar(rootLayout, "No internet connection");
            snackbar.show();
            return;
        }

        spinner.setVisibility(View.VISIBLE);

        final Activity activity = this;
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null || !username.equals(user.getUserId())) {
                    ActivityHelper.setErrorMessage(errorTextView, R.string.user_not_found);
                    spinner.setVisibility(View.GONE);
                    return;
                }

                if (!password.equals(user.getPassword())) {
                    ActivityHelper.setErrorMessage(errorTextView, R.string.password_incorrect);
                    spinner.setVisibility(View.GONE);
                    return;
                }
                preferenceHelper.setLoggedInFlag(true);
                preferenceHelper.storeUserId(username);
                spinner.setVisibility(View.GONE);
                ActivityHelper.switchToActivity(activity, MainActivity.class, true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database
                .child(FirebaseConstants.USERS_CHILD)
                    .child(username)
                        .addListenerForSingleValueEvent(valueEventListener);
    }
}
