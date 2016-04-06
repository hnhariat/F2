package com.sun.toy.ux.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by sunje on 2016-04-06.
 */
public class Consts {
    public static int dip2px(Context context, int dip) {
        int px = 0;
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return px;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
