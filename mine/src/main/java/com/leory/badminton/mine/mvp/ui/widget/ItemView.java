package com.leory.badminton.mine.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.commonlib.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe : 自定义左右内容视图
 * Author : leory
 * Date : 2019-6-19
 */

public class ItemView extends FrameLayout {

    @BindView(R2.id.icon_left)
    ImageView iconLeft;
    @BindView(R2.id.txt_left)
    TextView txtLeft;
    @BindView(R2.id.txt_right)
    TextView txtRight;
    @BindView(R2.id.icon_right)
    ImageView iconRight;
    @BindView(R2.id.arrow_right)
    ImageView arrowRight;
    @BindView(R2.id.right_view)
    LinearLayout rightView;
    @BindView(R2.id.divider)
    View divider;
    @BindView(R2.id.txt_left_second)
    TextView txtLeftSecond;

    public ItemView(@NonNull Context context) {
        this(context, null);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0);
        String leftText = typedArray.getString(R.styleable.ItemView_left_text);
        String leftSecondText = typedArray.getString(R.styleable.ItemView_left_second_text);
        String rightText = typedArray.getString(R.styleable.ItemView_right_text);
        int leftTextColor = typedArray.getColor(R.styleable.ItemView_left_text_color, -1);
        int leftTextSecondColor = typedArray.getColor(R.styleable.ItemView_left_second_text_color, -1);
        int rightTextColor = typedArray.getColor(R.styleable.ItemView_right_text_color, -1);
        boolean showRightArrow = typedArray.getBoolean(R.styleable.ItemView_show_right_arrow, false);
        boolean showDivider = typedArray.getBoolean(R.styleable.ItemView_show_divider, false);
        int iconLeft = typedArray.getResourceId(R.styleable.ItemView_icon_left, -1);
        int iconRight = typedArray.getResourceId(R.styleable.ItemView_icon_right, -1);
        typedArray.recycle();

        View root = LayoutInflater.from(getContext()).inflate(R.layout.view_item, this);
        ButterKnife.bind(this, root);

        setLeftText(leftText);
        setLeftSecondText(leftSecondText);
        setRightText(rightText);
        setLeftTextColor(leftTextColor);
        setLeftSecondTextColor(leftTextSecondColor);
        setRightTextColor(rightTextColor);
        showRightArrow(showRightArrow);
        showDivider(showDivider);

        setLeftIcon(iconLeft);
        setRightIcon(iconRight);

    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    public void setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            txtLeft.setText(text);
            txtLeft.setVisibility(VISIBLE);
        } else {
            txtLeft.setVisibility(GONE);
        }
    }

    /**
     * 设置左边文字的颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        if (color != -1) {
            txtLeft.setTextColor(color);
        }
    }

    /**
     * 设置左边次要文字
     *
     * @param text
     */
    public void setLeftSecondText(String text) {
        if (!TextUtils.isEmpty(text)) {
            txtLeftSecond.setText(text);
            txtLeftSecond.setVisibility(VISIBLE);
        } else {
            txtLeftSecond.setVisibility(GONE);
        }
    }

    /**
     * 设置左边次要文字的颜色
     *
     * @param color
     */
    public void setLeftSecondTextColor(int color) {
        if (color != -1) {
            txtLeftSecond.setTextColor(color);
        }
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            txtRight.setText(text);
            txtRight.setVisibility(VISIBLE);
        } else {
            txtRight.setVisibility(GONE);
        }
    }

    /**
     * 设置右边文字的颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        if (color != -1) {
            txtRight.setTextColor(color);
        }
    }

    /**
     * 是否显示右箭头
     *
     * @param isShow
     */
    public void showRightArrow(boolean isShow) {
        if (isShow) {
            arrowRight.setVisibility(VISIBLE);
        } else {
            arrowRight.setVisibility(GONE);
        }
    }

    /**
     * 显示下划线
     *
     * @param isShow
     */
    public void showDivider(boolean isShow) {
        if (isShow) {
            divider.setVisibility(VISIBLE);
        } else {
            divider.setVisibility(GONE);
        }
    }

    /**
     * 设置左边Icon
     *
     * @param resId
     */
    public void setLeftIcon(int resId) {
        if (resId != -1) {
            iconLeft.setVisibility(VISIBLE);
            iconLeft.setImageResource(resId);
        } else {
            iconLeft.setVisibility(GONE);
        }
    }

    /**
     * 设置右边Icon
     *
     * @param resId
     */
    public void setRightIcon(int resId) {
        if (resId != -1) {
            iconRight.setVisibility(VISIBLE);
            iconRight.setImageResource(resId);
        } else {
            iconRight.setVisibility(GONE);
        }
    }

    /**
     * 设置左边图片的url
     *
     * @param url
     */
    public void setLeftIconUrl(String url) {
        iconLeft.setVisibility(VISIBLE);
        ImageUtils.loadImage(getContext(), iconLeft, url);
    }

    /**
     * 设置右边图片的url
     *
     * @param url
     */
    public void setRightIconUrl(String url) {
        iconRight.setVisibility(VISIBLE);
        ImageUtils.loadImage(getContext(), iconRight, url);
    }


    /**
     * 获取右边文字
     *
     * @return
     */
    public String getRightText() {
        return txtRight.getText().toString().trim();
    }
}
