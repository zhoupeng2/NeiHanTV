package com.zp.neihan.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.PopupMenu;


import com.zp.neihan.base.MyApplication;

import java.util.concurrent.TimeUnit;


/**
 * @author KCrason
 * @date 2018/5/6
 */
public class Utils {

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, MyApplication.sContext.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth() {
        return MyApplication.sContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return MyApplication.sContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static boolean calculateShowCheckAllText(String content) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(Utils.dp2px(16f));
        float textWidth = textPaint.measureText(content);
        float maxContentViewWidth = Utils.getScreenWidth() - Utils.dp2px(74f);
        float maxLines = textWidth / maxContentViewWidth;
        return maxLines > 4;
    }


}
