package com.mikeoye.afrihub.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lami on 11/2/2016.
 */
public class FragmentSwitcher {
    private Map<String, Fragment> fragments;
    private FragmentManager fragmentManager;

    @IdRes
    private int fragmentManagerContainerId;
    private String currentFragmentTag;

    private FragmentSwitcher() {}
    private FragmentSwitcher(final Activity activity, @IdRes int fragmentManagerContainerId) {
        fragments = new HashMap<>();
        AppCompatActivity compatActivity = (AppCompatActivity) activity;
        fragmentManager = compatActivity.getSupportFragmentManager();
        this.fragmentManagerContainerId = fragmentManagerContainerId;
    }

    public void addFragment(String fragName, Fragment fragment) {
        fragments.put(fragName, fragment);
    }

    public void addFragmentWithBundle(String fragName, Fragment fragment, Bundle saveInstanceState) {
        fragment.setArguments(saveInstanceState);
        fragments.put(fragName, fragment);
    }

    public void switchFragment(String fragName) {
        // Get the fragment with the given tag
        Fragment fragment = fragments.get(fragName);
        if (currentFragmentTag.equalsIgnoreCase(fragName)) { return; }

        currentFragmentTag = fragName;
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(fragmentManagerContainerId, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * No fragment has been attached so call this method to make
     * one visible fragment.
     */
    public void showAsFirst(String fragTag) {
        Fragment fragment = fragments.get(fragTag);
        currentFragmentTag = fragTag;
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .add(fragmentManagerContainerId, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public static FragmentSwitcher getNewInstance(Activity activity, @IdRes int containerId) {
        return new FragmentSwitcher(activity, containerId);
    }
}
