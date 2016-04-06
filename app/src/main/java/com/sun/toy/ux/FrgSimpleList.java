package com.sun.toy.ux;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunje on 2016-04-06.
 */
public class FrgSimpleList extends BaseFragment {

    private Toolbar toolbar;

    public static FrgSimpleList newInstance(int position) {
        FrgSimpleList frg = new FrgSimpleList();
        frg.setPosition(position);
        return frg;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_simple_list, null);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();
        toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);

        toolbar.setTitle("" + position);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void initControl() {
        super.initControl();

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPagerWidth(position);
            }
        });
    }
}
