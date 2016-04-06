package com.sun.toy.ux.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sun.toy.ux.BaseFragment;
import com.sun.toy.ux.FrgSimpleList;

/**
 * Created by sunje on 2016-02-18.
 */
public class AdapterFrgSimple extends FragmentPagerAdapter {
    public AdapterFrgSimple(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        BaseFragment frg = FragmentSimple.newInstance(position);
        BaseFragment frg = FrgSimpleList.newInstance(position);
        return frg;
    }

//    @Override
//    public float getPageWidth(int position) {
//////        if (position == getCount() - 1) {
//////            return 1f;
//////        } else {
//        return 0.9f;
//////        }
////
//    }

    @Override
    public int getCount() {
        return 5;
    }
}
