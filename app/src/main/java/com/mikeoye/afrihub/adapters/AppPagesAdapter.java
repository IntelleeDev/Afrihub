package com.mikeoye.afrihub.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mikeoye.afrihub.R;
import com.mikeoye.afrihub.fragments.CoursesFragment;
import com.mikeoye.afrihub.fragments.ProfileFragment;
import com.mikeoye.afrihub.fragments.TimeTableFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lami on 2/24/2017.
 */

public class AppPagesAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;

    private int[] imageResourceIds = {
            R.drawable.ic_date_range_black_24dp,
            R.drawable.ic_account_circle_black_24dp,
            R.drawable.ic_assignment_black_24dp };

    private Context mContext;

    public AppPagesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimeTableFragment.newInstance(0);
            case 1:
                return ProfileFragment.newInstance(1);
            case 2:
                return CoursesFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_view, null);
        ImageView imageView = ButterKnife.findById(view, R.id.tab_icon);
        imageView.setImageResource(imageResourceIds[position]);
        return view;
    }

    public int[] getIcons() {
        return imageResourceIds;
    }
}
