package com.example.topnews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.topnews.Fragment.Fragment_business;
import com.example.topnews.Fragment.Fragment_entertainment;
import com.example.topnews.Fragment.Fragment_politics;
import com.example.topnews.Fragment.Fragment_sports;

public class PageViewAdapter extends FragmentPagerAdapter {
    public PageViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i)
        {

            case 0:
                fragment=new Fragment_business();
                break;
            case 1:
                fragment=new Fragment_politics();
                break;
            case 2:
                fragment=new Fragment_sports();
                break;
            case 3:
                fragment=new Fragment_entertainment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
