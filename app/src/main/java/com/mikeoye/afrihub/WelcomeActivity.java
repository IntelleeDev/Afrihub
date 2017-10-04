package com.mikeoye.afrihub;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);
        checkForFirstTimeLaunch();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void checkForFirstTimeLaunch() {
        boolean isUserLoggedIn = PreferenceHelper.getInstance(this).isUserLoggedIn();
        if (isUserLoggedIn) {
            ActivityHelper.switchToActivity(this, MainActivity.class, true);
        }
    }

    @OnClick(R.id.sign_in_button_welcome)
    public void switchToSignInActivity() {
        ActivityHelper.switchToActivity(this, SignInActivity.class, false);
    }

    @OnClick(R.id.sign_up_button_welcome)
    public void switchToSignUpActivity() {
        ActivityHelper.switchToActivity(this, SignUpActivity.class, false);
    }
}
