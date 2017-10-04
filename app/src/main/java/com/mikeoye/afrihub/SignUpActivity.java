package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikeoye.afrihub.commons.Constants;
import com.mikeoye.afrihub.commons.ViewOperations;
import com.mikeoye.afrihub.utils.FirebaseHelper;
import com.mikeoye.afrihub.utils.ConnectionHelper;
import com.mikeoye.afrihub.utils.FormHelper;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity implements ViewOperations {

    @BindView(R.id.stud_fullname) EditText fullNameEditText;
    @BindView(R.id.stud_id)       EditText studentIdEditText;
    @BindView(R.id.email_address) EditText emailAddressEditText;
    @BindView(R.id.password)      EditText passwordEditText;
    @BindView(R.id.conf_password) EditText confPasswordEditText;

    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    @BindView(R.id.activity_sign_up) FrameLayout rootLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onStart() {
        super.onStart();
        ActivityHelper.setUpToolbar(this, toolbar, true);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        ActivityHelper.changeStatusBarColor(this);

        hideProgressBar();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.sign_up_button)
    public void signUpUser() {

        final String userId = studentIdEditText.getText().toString();
        String fullname  =  fullNameEditText.getText().toString();
        String email     =  emailAddressEditText.getText().toString();
        String password  =  passwordEditText.getText().toString();

        showProgressBar();

        if (!ConnectionHelper.isConnected(this)
                && !ConnectionHelper.isMobileDataConnected(this)) {
            ActivityHelper.makeSnackBar(rootLayout, "No internet connection").show();
            return;
        }

        if (inputFieldsValidated())
        {
            FirebaseHelper firebaseHelper = FirebaseHelper.getInstance(this);
            firebaseHelper.writeNewStudent(userId, fullname, email);
            firebaseHelper.writeNewUser(userId, fullname, password, email, "none", "none");

            PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(this);
            preferenceHelper.storeUserId(userId);
            preferenceHelper.setLoggedInFlag(true);

            final Activity activity = this;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.USER_ID, userId);

                    hideProgressBar();

                    ActivityHelper
                            .switchToActivity(
                                    activity,
                                    CourseSelectionActivity.class,
                                    bundle,
                                    true);
                }
            }, 1200);
        }
        else {
            hideProgressBar();
        }

    }

    private boolean inputFieldsValidated() {
        EditText[] fields = new EditText[] {
                fullNameEditText, studentIdEditText,
                emailAddressEditText, passwordEditText, confPasswordEditText};

        if(!FormHelper.validateNonEmpty(Arrays.asList(fields))) {
            final Snackbar snackbar = ActivityHelper.makeSnackBar(rootLayout, "Empty fields not allowed");
            snackbar.show();
            return false;
        }
        if (!FormHelper.validatePasswords(passwordEditText, confPasswordEditText)) {
            final Snackbar snackbar = ActivityHelper.makeSnackBar(rootLayout, "Passwords do not match");
            snackbar.show();
            return false;
        }
        return true;
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
