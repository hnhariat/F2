package com.sun.toy.ux.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sun.toy.ux.BaseFragment;
import com.sun.toy.ux.FrgSimpleList;

import java.util.HashMap;

/**
 * Created by sunje on 2016-02-18.
 */
public class AdapterFrgSimple extends FragmentStatePagerAdapter {
    public AdapterFrgSimple(FragmentManager fm) {
        super(fm);
    }
    private HashMap<Integer, FrgSimpleList> frgMap = new HashMap<>();
    @Override
    public Fragment getItem(int position) {
//        BaseFragment frg = FragmentSimple.newInstance(position);
        FrgSimpleList frg = frgMap.get(position);

        if (frg == null) {
            frg = FrgSimpleList.newInstance(position);
            frgMap.put(position, frg);
        }

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
