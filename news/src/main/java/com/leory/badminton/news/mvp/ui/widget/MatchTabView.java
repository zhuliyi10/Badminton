package com.leory.badminton.news.mvp.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.commonlib.utils.ScreenUtils;

import java.util.List;

/**
 * Describe : 比赛自定义view
 * Author : leory
 * Date : 2019-06-03
 */
public class MatchTabView extends LinearLayout {
    OnChildClickListener listener;
    private int itemWidth;
    private int textSize;
    private ColorStateList textColor;
    private Drawable bgColor;

    public MatchTabView(Context context) {
        this(context, null);
    }

    public MatchTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatchTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MatchTabView, defStyleAttr, 0);
        itemWidth = typedArray.getDimensionPixelSize(R.styleable.MatchTabView_item_width, ScreenUtils.dp2px(context, 50));
        textSize = typedArray.getDimensionPixelSize(R.styleable.MatchTabView_text_size, ScreenUtils.dp2px(context, 12));
        textColor = typedArray.getColorStateList(R.styleable.MatchTabView_text_color);
        bgColor = typedArray.getDrawable(R.styleable.MatchTabView_bg_color);
        typedArray.recycle();
    }

    public void initData(List<String> data) {
        removeAllViews();
        int count = data.size();
        for (int i = 0; i < count; i++) {
            addTextView(data.get(i), itemWidth);
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
    public int getSelectPos() {
        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i);
            if(tv.isSelected()) {
                return i;
            }
        }
        return 0;
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

    private void addTextView(String text, int width) {
        TextView textView = getTextView();
        textView.setText(text);
        LayoutParams lp = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        addView(textView, lp);
    }

    private TextView getTextView() {
        TextView textView = new TextView(getContext());
        if (textColor != null) {
            textView.setTextColor(textColor);
        }
        if (bgColor != null) {
            textView.setBackground(bgColor);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
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
