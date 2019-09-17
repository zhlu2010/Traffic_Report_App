package com.laioffer.matrix;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class OnBoardingPageAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private SharedPreferences sharedPreferences;

    public OnBoardingPageAdapter(FragmentManager fragmentManager, SharedPreferences sharedPreferences) {
        super(fragmentManager);
        this.sharedPreferences = sharedPreferences;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LoginFragment.newInstance(sharedPreferences);
            case 1:
                return RegisterFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Register";
        }
        return null;
    }
}
