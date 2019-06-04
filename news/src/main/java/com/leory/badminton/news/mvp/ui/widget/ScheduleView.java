package com.leory.badminton.news.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.commonlib.utils.ScreenUtils;

/**
 * Describe : 比较进度view
 * Author : leory
 * Date : 2019-06-03
 */
public class ScheduleView extends LinearLayout {
    OnChildClickListener listener;

    public ScheduleView(Context context) {
        this(context, null);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initData(int count) {
        removeAllViews();
        float commonWidth=50;
        if (count > 0) {
            addTextView("决赛",40);

        }
        if (count > 1) {
            addTextView("半决赛",commonWidth);
        }
        if (count > 2) {
            addTextView("1/4决赛",commonWidth);
        }
        if (count > 3) {
            addTextView("1/8决赛",commonWidth);
        }
        if (count > 4) {
            addTextView("1/16决赛",commonWidth);
        }
        if (count > 5) {
            addTextView("1/32决赛",commonWidth);
        }

        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i);
            int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null && !tv.equals(getSelectView())) {
                        listener.onClick(tv, finalI);
                    }
                }
            });
        }
    }

    public void setSelectPos(int pos) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i);
            tv.setSelected(i == pos);
        }
    }

    public View getSelectView() {
        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i);
            if (tv.isSelected()) return tv;
        }
        return null;
    }
    public TextView getTextView(int pos) {

        return (TextView) getChildAt(pos);
    }
    private void addTextView(String text,float width) {
        TextView textView = getTextView();
        textView.setText(text);
        LayoutParams lp = new LayoutParams(ScreenUtils.dp2px(getContext(), width), LayoutParams.MATCH_PARENT);
        addView(textView, 0, lp);
    }

    private TextView getTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(getResources().getColorStateList(R.color.selector_color_match_schedule));
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    public interface OnChildClickListener {
        void onClick(TextView tv, int position);
    }
}
