package com.mikeoye.afrihub.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.commons.Constants;

/**
 * Created by lami on 2/24/2017.
 */

public class ActivityHelper {

    private ActivityHelper() throws Exception {
        throw new Exception("Cannot instantiate this class");
    }

    public static MaterialDialog makeProgressDialog(Context context, @StringRes int displayMessage) {
        MaterialDialog  progressDialog
                = new MaterialDialog.Builder(context)
                            .content(displayMessage)
                            .typeface("Roboto-Bold.ttf", "Roboto-Regular.ttf")
                            .progress(true, 0)
                            .build();
        return progressDialog;
    }

    public static void setErrorMessage(TextView errorView, @StringRes int errorMessage) {
        errorView.setText(errorMessage);
    }

    public static void setUpToolbar(AppCompatActivity activity, Toolbar toolbar, boolean setBackEnabled) {
        activity.setSupportActionBar(toolbar);
        if (setBackEnabled) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public static Snackbar makeSnackBar(ViewGroup rootLayout, String message) {
        Snackbar snackBar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
        return snackBar;
    }

    public static void changeStatusBarColor(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void closeActivity(Activity currentActivity) {
        currentActivity.finish();
    }

    public static void switchToActivity(Activity currentActivity, Class targetActivity,
                                        boolean removeFromBackStack) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);
        if (removeFromBackStack) {
            currentActivity.finish();
        }
    }

    public static void inflateMenu(Toolbar toolbar, @MenuRes int menuResource) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(menuResource);
    }

    public static void switchToActivity(Activity currentActivity, Class targetActivity,
                                        Bundle bundle,
                                        boolean removeFromBackStack) {
        Intent intent = new Intent(currentActivity, targetActivity);
        intent.putExtras(bundle);
        currentActivity.startActivity(intent);
        if (removeFromBackStack) {
            currentActivity.finish();
        }
    }
}
