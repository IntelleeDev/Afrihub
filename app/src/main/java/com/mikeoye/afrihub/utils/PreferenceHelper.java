package com.mikeoye.afrihub.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.fragments.ProfileFragment;

/**
 * Created by lami on 3/3/2017.
 */

public class PreferenceHelper {

    private Context context;
    private SharedPreferences sharedPreferences;

    private PreferenceHelper(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void storeProfileImageString(String imageString) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.profile_image_uri), imageString);
        editor.commit();
    }

    public void storeUserId(String userId) {
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.user_id_key), userId);
        editor.commit();
    }

    public void setLoggedInFlag(boolean value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(String.valueOf(R.string.logged_status_key), value);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        boolean loginStatus =
                sharedPreferences.getBoolean(
                        String.valueOf(R.string.logged_status_key), false);
        return loginStatus;
    }

    public String getProfileImageString() {
        return sharedPreferences.getString(String.valueOf(R.string.profile_image_uri), "");
    }

    public String getUserID() {
        return sharedPreferences.getString(String.valueOf(R.string.user_id_key), "");
    }

    public static PreferenceHelper getInstance(Context context) {
        return new PreferenceHelper(context);
    }
}
