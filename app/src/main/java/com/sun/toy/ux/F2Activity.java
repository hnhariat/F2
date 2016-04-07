package com.sun.toy.ux;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private int prevPadding = 0;
    private int mHeaderHeight;
    private TextView txtHeader;
    private ImageView imgHeader;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

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
        mHeaderHeight = Consts.dip2px(this, 192);
        mPageMargin = Consts.dip2px(getApplicationContext(), 10);
    }

    @TargetApi(11)
    @Override
    protected void initView() {
        super.initView();
        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setOnTouchDeliver(this);

        txtHeader = (TextView) findViewById(R.id.txt);
        imgHeader = (ImageView) findViewById(R.id.img);
        imgHeader.setScaleType(ImageView.ScaleType.MATRIX);
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

        pager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtHeader.setText(((FrgSimpleList)adapter.getItem(position)).getTitle()+ "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(0);
    }

    private int getCorrectScrollX(int direction, int width, int topMargin) {
        if (direction == 0) { // down

        } else { // up

        }
        return (int) (topMargin * (width / (float) mPageMaxWidth));
    }

    private int getWidthPadding(int direction, int dy) {
        return (int) ((dy / (float) mDraggableMaxHeight) * mPagerInitialPadding);
    }

    @TargetApi(11)
    @Override
    public void onDragY(float dy) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pager.getLayoutParams();
        if (params.topMargin == 0) {
            // first
            setPageFormation();
        }

        Log.d("viewpager.drag", "y : " + dy);

        int from = 0, to = 0;
        if (pager.getPaddingLeft() != mPagerInitialPadding) {
            to = mPagerInitialPadding;
        } else {
            from = mPagerInitialPadding;
        }

        params.topMargin += (int) dy;

        if (params.topMargin >= mDraggableMaxHeight) {
            params.topMargin = mDraggableMaxHeight;
        } else if (params.topMargin <= 0) {
            params.topMargin = 0;
        }
        int padding = getWidthPadding(from == 0 ? 1 : 0, params.topMargin);
        Log.d("view.padding", "" + padding);
        pager.setPadding(padding, 0, padding, 0);
        pager.scrollBy((prevPadding - padding) * pager.getCurrentItem() * 2, 0);
        prevPadding = padding;

        scaleHeader(params.topMargin / (float) mDraggableMaxHeight);
    }

    private void scaleHeader(float ratio) {
        int w = imgHeader.getDrawable().getIntrinsicWidth();
        int h = imgHeader.getDrawable().getIntrinsicHeight();
        matrix.setScale(1f + ratio, 1f + ratio, w/2, h/2);
        imgHeader.setImageMatrix(matrix);
        imgHeader.invalidate();
    }

    @TargetApi(11)
    @Override
    public void onDragEnd() {
        Log.d("asdlfj", "enc drag");
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pager.getLayoutParams();
        final int from = params.topMargin;
        int to = 0;
        if (from == mHeaderHeight) {
            // select
            to = 0;
        } else if (params.topMargin > mHeaderHeight / 2) {
            // 일정거리 이상 이동
            to = mHeaderHeight;
        } else {
            to = 0;
        }
        Log.d("sldfjsldfj", "params.topMargin : " + params.topMargin + " mDraggableMaxHeight * 0.8f : " + mHeaderHeight * 0.8f + " " +
                "mDraggableMaxHeight : " + mHeaderHeight);
        ValueAnimator anim = ValueAnimator.ofInt(from, to);

        final int finalTo = to;
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                params.topMargin = value;

                int padding = getWidthPadding(from == 0 ? 1 : 0, params.topMargin);

                pager.setPadding(padding, 0, padding, 0);
                pager.scrollBy((prevPadding - padding) * pager.getCurrentItem() * 2, 0);
                Log.d("view.pager.drag.end", "prevPadding : " + prevPadding + " | padding : " + padding + " | page : " + pager.getCurrentItem());
                prevPadding = padding;
                pager.setLayoutParams(params);

                if (value == finalTo && finalTo == 0) {
                    setPagingEnable(true);
                } else {
                    setPagingEnable(false);
                }
            }
        });
        anim.setDuration(200);
        anim.start();
    }

    private void setPagingEnable(boolean enable) {
        pager.setPagingEnable(enable);
    }

    private void setPageFormation() {
        for (int i = 0; i < pager.getChildCount(); i++) {

            View page = pager.getChildAt(i);

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
    }

    @TargetApi(11)
    @Override
    public void onPagerWidth(final int position) {
        super.onPagerWidth(position);
//        int from = 0, to = 0;
//        boolean isBig = false;
//        if (pager.getPaddingLeft() != mPagerInitialPadding) {
//            to = mPagerInitialPadding;
//            isBig = true;
//            pager.requestDisallowInterceptTouchEvent(true);
//        } else {
//            from = mPagerInitialPadding;
//            pager.requestDisallowInterceptTouchEvent(false);
//        }
//        if (isBig) {
//            return;
//        }
//        Log.d("viewpager.values", "from : " + from + " , " + "to : " + to + " pager.getScrollX();" + pager.getScrollX());
//        ValueAnimator anim = ValueAnimator.ofInt(from, to);
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            int prevPadding = 0;
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                pager.setPadding(value, 0, value, 0);
//                pager.scrollBy((prevPadding - value) * pager.getCurrentItem() * 2, 0);
//                prevPadding = value;
//            }
//        });
//
////        if (position > 0) {
////            pager.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    pager.scrollTo(pager.getChildAt(position - 1).getRight() - pager.getChildAt(position - 1).getLeft(), 0);
////                }
////            }, 200);
////
////        }
//        Log.d("viewpager.values", "left padding : " + pager.getPaddingLeft());
////        pager.scrollTo(pager.getChildAt(1).getLeft(), 0);
////        pager.scrollBy(-to, 0);
////        pager.scrollTo(to, 0);
////                if (position == finalTo1) {
////                    pager.requestLayout();
////                    pager.postInvalidate();//            }
////
////            }
////
////        });
//        anim.start();
//        to = 0;
//        from = ((RelativeLayout.LayoutParams) pager.getLayoutParams()).topMargin;
//        if (isBig) {
//            to = Consts.dip2px(this, 192);
//        }
//        ValueAnimator anim2 = ValueAnimator.ofInt(from, to);
////        final int finalTo = to;
//        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pager.getLayoutParams();
//                params.topMargin = value;
//                pager.setLayoutParams(params);
//            }
//        });
//        anim2.start();
////        Toast.makeText(SwipeViewActivity.this, position + "", 0).show();
//
    }
}