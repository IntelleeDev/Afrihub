package com.mikeoye.afrihub;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.mikeoye.afrihub.adapters.AppPagesAdapter;
import com.mikeoye.afrihub.commons.Constants;
import com.mikeoye.afrihub.fragments.CoursesFragment;
import com.mikeoye.afrihub.fragments.ProfileFragment;
import com.mikeoye.afrihub.fragments.TimeTableFragment;
import com.mikeoye.afrihub.utils.ActivityHelper;
import com.mikeoye.afrihub.utils.FragmentSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity  {

    private static final String PAGER_POSITION = "POSITION";

    private FragmentSwitcher fragmentSwitcher;

    @BindView(R.id.toolbar)   Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //viewPager.setCurrentItem(savedInstanceState.getInt(PAGER_POSITION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ActivityHelper.setUpToolbar(this, toolbar, false);
        initializeWidgets(savedInstanceState);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActivityHelper.inflateMenu(toolbar, R.menu.time_table_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_course_item:
                ActivityHelper.switchToActivity(this, CourseUpdateActivity.class, false);
                break;
            case R.id.edit_profile_item:
                ActivityHelper.switchToActivity(this, ProfileUpdateActivity.class, false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeWidgets(Bundle savedInstanceState) {
        AppPagesAdapter appPagesAdapter = new AppPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(appPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);

        int[] tabIcons = appPagesAdapter.getIcons();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(tabIcons[i]);

            int iconColor =
                    ContextCompat.getColor(getApplicationContext(), R.color.tab_icon_inactive);
            tab.getIcon().setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
        }

        if (savedInstanceState == null) {
            int iconColor =
                    ContextCompat.getColor(getApplicationContext(), R.color.text_color);
            TabLayout.Tab firstTab = tabLayout.getTabAt(0);
            firstTab.getIcon().setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor =
                        ContextCompat.getColor(getApplicationContext(), R.color.text_color);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor =
                        ContextCompat.getColor(getApplicationContext(), R.color.tab_icon_inactive);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActionBar actionBar = getSupportActionBar();
                switch(position) {
                    case Constants.TIMETABLE_FRAGMENT:
                        actionBar.setTitle("Timetable");
                        ActivityHelper.inflateMenu(toolbar, R.menu.time_table_menu);
                        break;
                    case Constants.PROFILE_FRAGMENT:
                        actionBar.setTitle("Profile");
                        ActivityHelper.inflateMenu(toolbar, R.menu.profile_menu);
                        break;
                    case Constants.COURSES_FRAGMENT:
                        actionBar.setTitle("Courses");
                        ActivityHelper.inflateMenu(toolbar, R.menu.base_menu);
                        break;
                }

                TabLayout.Tab tab = tabLayout.getTabAt(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
