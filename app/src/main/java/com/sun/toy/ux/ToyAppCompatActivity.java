package com.sun.toy.ux;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;

public class ToyAppCompatActivity extends AppCompatActivity implements View.OnClickListener, OnFragmentInteractionListener {

    protected Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void initialize() {
        ButterKnife.bind(this);
        init();
        initView();
        initControl();
        initActionBar();
    }

    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected void init() {
    }


    protected void initControl() {
    }

    protected void initActionBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentVisible(int position) {

    }

    @Override
    public void onPagerWidth(int toValue) {

    }
}
