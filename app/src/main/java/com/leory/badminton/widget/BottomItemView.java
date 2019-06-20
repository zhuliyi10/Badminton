package com.leory.badminton.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leory.badminton.R;
import com.leory.badminton.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe : 底部选择器
 * Author : leory
 * Date : 2019-05-22
 */
public class BottomItemView extends FrameLayout {
    @BindView(R2.id.icon)
    ImageView icon;
    @BindView(R2.id.text_name)
    TextView textName;
    private int normalRes;
    private int activateRes;
    private int normalTextColor;
    private int activateTextColor;
    private String text;
    private boolean activate;

    public BottomItemView(@NonNull Context context) {
        this(context, null);
    }

    public BottomItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomItemView, defStyleAttr, 0);
        normalRes = typedArray.getResourceId(R.styleable.BottomItemView_normal_res, -1);
        activateRes = typedArray.getResourceId(R.styleable.BottomItemView_activate_res, -1);
        normalTextColor = typedArray.getColor(R.styleable.BottomItemView_normal_text_color, -1);
        activateTextColor = typedArray.getColor(R.styleable.BottomItemView_activate_text_color, -1);
        text = typedArray.getString(R.styleable.BottomItemView_text);
        activate = typedArray.getBoolean(R.styleable.BottomItemView_activate, false);
        typedArray.recycle();
        View root = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_item, this);
        ButterKnife.bind(this, root);
        initView();
    }

    private void initView() {
        textName.setText(text);
        setActivate(activate);
    }

    /**
     * 是否激活
     *
     * @param activate
     */
    public void setActivate(boolean activate) {
        this.activate = activate;
        if (activate) {
            icon.setImageResource(activateRes);
            textName.setTextColor(activateTextColor);
        }else {
            icon.setImageResource(normalRes);
            textName.setTextColor(normalTextColor);
        }
    }

    /**
     * 是否是激活
     * @return
     */
    public boolean isActivate(){
        return activate;
    }
}
