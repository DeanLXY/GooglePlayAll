package itcast.zz16.googleplay.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DrawableUtils
 * @create_time 2016/8/27 0027
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class DrawableUtils {

    /**
     * 返回 背景 shapeDrawable
     * @return
     */
    public static GradientDrawable  create(int color){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(UIUtils.dip2px(5)); // dp
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 创建状态选择器
     * @param pressedDrawable
     * @param normalDrawable
     * @return
     */
    public static StateListDrawable createStateList(Drawable pressedDrawable,Drawable normalDrawable){
//        <selector xmlns:android="http://schemas.android.com/apk/res/android" android:enterFadeDuration="500">
//
//
//        <item android:drawable="@drawable/btn_pressed" android:state_pressed="true" />
//        <item android:drawable="@drawable/btn_normal" />
//        </selector>
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);
        drawable.addState(new int[]{},normalDrawable);
        return drawable;
    }
}
