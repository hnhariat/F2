package com.sun.toy.ux;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.sun.toy.ux.adapters.AdapterFrgSimple;
import com.sun.toy.ux.utils.Consts;
import com.sun.toy.ux.widget.CustomViewPager;

public class F2Activity extends ToyAppCompatActivity implements CustomViewPager.OnTouchDeliver {

    private CustomViewPager pager;
    private AdapterFrgSimple adapter;
    private int mPagerInitialPadding;
    private int mPageMaxWidth;
    private int mPageMinWidth;
    private int mPageMargin = 20;
    private int mDraggableMaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f2);
        initialize();
    }

    @Override
    protected void init() {
        super.init();


        mPageMaxWidth = Consts.getScreenWidth(this);

        mDraggableMaxHeight = Consts.dip2px(this, 214);

        mPageMargin = Consts.dip2px(getApplicationContext(), 10);
    }

    @Override
    protected void initView() {
        super.initView();
        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setOnTouchDeliver(this);

        mPagerInitialPadding = getResources().getDimensionPixelSize(R.dimen.page_padding);
        mPageMinWidth = mPageMaxWidth - mPagerInitialPadding * 2;

        Log.d("viewpager.values", "mPagerInitialPadding : " + mPagerInitialPadding);
        adapter = new AdapterFrgSimple(getSupportFragmentManager());
        pager.setAdapter(adapter);
//        pager.setPageMargin(mPageMargin);
        pager.setPageTransformer(false, new CustomViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = pager.getMeasuredWidth() - pager.getPaddingLeft() - pager.getPaddingRight();
                int pageHeight = pager.getHeight();
                int paddingLeft = pager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (pager.getScrollX() + paddingLeft)) / pageWidth;

                final float normalizedposition = Math.abs(Math.abs(transformPos) - 1);
                page.setAlpha(normalizedposition + 0.5f);

                int max = -pageHeight / 10;

                /* Check http://stackoverflow.com/questions/32384789/android-viewpager-smooth-transition-for-this-design
                   for other ways to do this.
                */
                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
//                    page.setTranslationY(0);
                    page.setTranslationY(max);
                } else if (transformPos <= 1) { // [-1,1]
//                    page.setTranslationY(0);
                    page.setTranslationY(max * (Math.abs(transformPos)));
                } else if (transformPos <= 0) { // (1,+Infinity]
                    // This page is way off-screen to the right.
//                    page.setTranslationY(80);
                    page.setTranslationY(max * (1 - Math.abs(transformPos)));
                } else {
                    page.setTranslationY(max);
                }
            }
        });
//        pager.setPageMargin(1);
//        pager.setPageTransformer(true, new ZoomOutPageTransformer(true));
//        pager.setClipChildren(false);
//        int extra = (int) (ViewUtils.getScreenWidth(this) * 0.1f);
//        pager.setPadding(extra, 0, 0, 0);
//        pager.setOffscreenPageLimit(20);
    }

    public int getWidthPadding(int direction, int dy) {
        if (direction == 0) { // DOWN
//            mDraggableMaxHeight
        } else { // to UP

        }

        return 0;
    }

    @Override
    public void onDragY(float delta) {
        Log.d("viewpager.drag", "y : " + delta);

        int from = 0, to = 0;
        if (pager.getPaddingLeft() != mPagerInitialPadding) {
            to = mPagerInitialPadding;
        } else {
            from = mPagerInitialPadding;
        }
//        ValueAnimator anim = ValueAnimator.ofInt(from, to);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                Log.d("hatti.View.animation.value", "vale : " + value);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pager.getLayoutParams();
        params.topMargin += (int) delta;

        if (params.topMargin >= mDraggableMaxHeight) {
            params.topMargin = mDraggableMaxHeight;
        } else if (params.topMargin <= 0) {
            params.topMargin = 0;
        }
        pager.setLayoutParams(params);

//                pager.setPadding(value, 0, value, 0);
//                pager.invalidate();
//            }
//        });
//        anim.start();
//        Toast.makeText(SwipeViewActivity.this, toValue + "", 0).show();

    }

    @Override
    public void onPagerWidth(final int position) {
        super.onPagerWidth(position);
        int from = 0, to = 0;
        boolean isBig = false;
        if (pager.getPaddingLeft() != mPagerInitialPadding) {
            to = mPagerInitialPadding;
            isBig = true;
            pager.requestDisallowInterceptTouchEvent(true);
        } else {
            from = mPagerInitialPadding;
            pager.requestDisallowInterceptTouchEvent(false);
        }
        Log.d("viewpager.values", "from : " + from + " , " + "to : " + to + " pager.getScrollX();" + pager.getScrollX());
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        final int finalTo = to;

        final int finalTo1 = to;
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                Log.d("hatti.View.animation.value", "vale : " + value);
//                pager.setPadding(value, 0, value, 0);
        final int finalTo2 = to;
//        pager.post(new Runnable() {
//            @Override
//            public void run() {
        pager.setPadding(finalTo2, 0, finalTo2, 0);
        pager.setCurrentItem(position, false);
        if (finalTo == 0) {
//            pager.scrollBy(mPagerInitialPadding * (2 * position), 0);
//            if (position > 0) {
            pager.scrollTo(mPageMaxWidth * position, 0);
//            } else {
//                pager.scrollTo(0, 0);
//            }
        } else {
            pager.scrollTo(mPageMinWidth * position, 0);
//            pager.scrollBy(-mPagerInitialPadding * (2 * position), 0);
        }
//            }
//        });

//        if (position > 0) {
//            pager.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    pager.scrollTo(pager.getChildAt(position - 1).getRight() - pager.getChildAt(position - 1).getLeft(), 0);
//                }
//            }, 200);
//
//        }
        Log.d("viewpager.values", "left padding : " + pager.getPaddingLeft());
//        pager.scrollTo(pager.getChildAt(1).getLeft(), 0);
//        pager.scrollBy(-to, 0);
//        pager.scrollTo(to, 0);
//                if (position == finalTo1) {
//                    pager.requestLayout();
//                    pager.postInvalidate();//            }
//
//            }
//
//        });
        anim.start();
        to = 0;
        from = ((RelativeLayout.LayoutParams) pager.getLayoutParams()).topMargin;
        if (isBig) {
            to = Consts.dip2px(this, 192);
        }
        ValueAnimator anim2 = ValueAnimator.ofInt(from, to);
//        final int finalTo = to;
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pager.getLayoutParams();
                params.topMargin = value;
                pager.setLayoutParams(params);
            }
        });
        anim2.start();
//        Toast.makeText(SwipeViewActivity.this, position + "", 0).show();

    }
}