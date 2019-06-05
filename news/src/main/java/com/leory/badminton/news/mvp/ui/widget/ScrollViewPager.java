package com.leory.badminton.news.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Describe : 禁止左右滑动
 * Author : leory
 * Date : 2019-05-22
 */
public class ScrollViewPager extends ViewPager {
    private boolean scrollable=false;
    public ScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void setScrollablel(boolean scrollable){
        this.scrollable=scrollable;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,scrollable);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable;
    }
}
