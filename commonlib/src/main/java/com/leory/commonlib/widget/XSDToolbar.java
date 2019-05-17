package com.leory.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leory.commonlib.R;
import com.leory.commonlib.R2;
import com.leory.commonlib.utils.ScreenUtils;
import com.leory.commonlib.widget.morePop.MorePopBean;
import com.leory.commonlib.widget.morePop.MorePopView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe : 小水滴标题栏
 * Author : zhuly
 * Date : 2018-10-24
 */

public class XSDToolbar extends FrameLayout {


    private static int theme_white_bg_bar;
    private static int theme_white_text_color;
    private static int theme_white_title_color;

    private static int theme_blue_bg_bar;
    private static int theme_blue_text_color;
    private static int theme_blue_title_color;
    @BindView(R2.id.icon_arrow)
    ImageView iconArrow;
    @BindView(R2.id.txt_left)
    TextView txtLeft;
    @BindView(R2.id.back)
    LinearLayout back;
    @BindView(R2.id.txt_title)
    TextView txtTitle;
    @BindView(R2.id.txt_right)
    TextView txtRight;
    @BindView(R2.id.icon_right)
    ImageView iconRight;
    @BindView(R2.id.right_more)
    FrameLayout rightMore;
    @BindView(R2.id.right)
    LinearLayout right;
    @BindView(R2.id.divider)
    View divider;
    @BindView(R2.id.root)
    RelativeLayout root;


    private int themeType;//主题类型
    private int bgBar;//标题栏背景
    private boolean leftBack;//显示退出
    private String leftText;//左边文字
    private int leftTextColor;//左边文字颜色
    private String titleText;//标题文字
    private int titleTextColor;//标题文字颜色
    private String rightText;//右边文字
    private int rightTextColor;//右边文字颜色
    private int rightIconRes;//右边图标
    private boolean showDivider;//显示分隔线
    private OnBackListener backListener;
    private OnRightTextListener rightTextListener;
    private OnRightIconListener rightIconListener;

    public XSDToolbar(@NonNull Context context) {
        this(context, null);
    }

    public XSDToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public XSDToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initTheme();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XSDToolbar, defStyleAttr, 0);
        themeType = typedArray.getInt(R.styleable.XSDToolbar_theme_type, 0);
        bgBar = typedArray.getColor(R.styleable.XSDToolbar_bg_bar, -1);
        leftBack = typedArray.getBoolean(R.styleable.XSDToolbar_left_back, true);
        leftText = typedArray.getString(R.styleable.XSDToolbar_left_text);
        leftTextColor = typedArray.getColor(R.styleable.XSDToolbar_left_text_color, -1);

        titleText = typedArray.getString(R.styleable.XSDToolbar_title_text);
        titleTextColor = typedArray.getColor(R.styleable.XSDToolbar_title_text_color, -1);

        rightText = typedArray.getString(R.styleable.XSDToolbar_right_text);
        rightTextColor = typedArray.getColor(R.styleable.XSDToolbar_right_text_color, -1);
        rightIconRes = typedArray.getResourceId(R.styleable.XSDToolbar_right_icon_res, -1);

        showDivider = typedArray.getBoolean(R.styleable.XSDToolbar_show_divider, false);
        typedArray.recycle();
        View root = LayoutInflater.from(getContext()).inflate(R.layout.toolbar, this);
        ButterKnife.bind(this, root);
        initView();

    }

    private void initTheme() {
        theme_white_bg_bar = Color.WHITE;
        theme_white_text_color = Color.parseColor("#444444");
        theme_white_title_color = Color.BLACK;
        theme_blue_bg_bar = Color.parseColor("#ff318bf3");
        theme_blue_text_color = Color.WHITE;
        theme_blue_title_color = Color.WHITE;
    }

    private void initView() {
        if (themeType == 0) {
            txtLeft.setTextColor(theme_white_text_color);
            txtRight.setTextColor(theme_white_text_color);
            txtTitle.setTextColor(theme_white_title_color);
            root.setBackgroundColor(theme_white_bg_bar);
            iconArrow.setImageResource(R.mipmap.arrow_left_gray);

        } else if (themeType == 1) {
            txtLeft.setTextColor(theme_blue_text_color);
            txtRight.setTextColor(theme_blue_text_color);
            txtTitle.setTextColor(theme_blue_title_color);
            root.setBackgroundColor(theme_blue_bg_bar);
            iconArrow.setImageResource(R.mipmap.arrow_left_white);
        }
        //左边
        setLeftBack(leftBack);
        setLeftText(leftText);
        setLeftTextColor(leftTextColor);

        //背景
        setBgBar(bgBar);

        //标题
        setTitleText(titleText);
        setTitleTextColor(titleTextColor);
        //右边
        setRightText(rightText);
        setRightTextColor(rightTextColor);
        setRightIconRes(rightIconRes);

        //分隔线
        showDivider(showDivider);


    }

    /**
     * 设置标题
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        txtTitle.setText(titleText);
    }

    /**
     * 设置标题颜色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        if (titleTextColor != -1) {
            txtTitle.setTextColor(titleTextColor);
        }
    }

    /**
     * 设置返回的显示
     *
     * @param show
     */
    public void setLeftBack(boolean show) {
        if (show) {
            back.setVisibility(VISIBLE);
        } else {
            back.setVisibility(GONE);
        }
    }

    /**
     * 设置左边文字
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        if (!TextUtils.isEmpty(leftText)) {
            txtLeft.setText(leftText);
            txtLeft.setVisibility(VISIBLE);
        } else {
            txtLeft.setVisibility(GONE);
        }
    }

    /**
     * 设置左边文字颜色
     *
     * @param leftTextColor
     */
    public void setLeftTextColor(int leftTextColor) {
        if (leftTextColor != -1) {
            txtLeft.setTextColor(leftTextColor);
        }
    }

    /**
     * 设置背景
     *
     * @param bgBar
     */
    public void setBgBar(int bgBar) {
        if (bgBar != -1) {
            root.setBackgroundColor(bgBar);
        }
    }

    /**
     * 设置右边文字
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        if (!TextUtils.isEmpty(rightText)) {
            txtRight.setText(rightText);
            txtRight.setVisibility(VISIBLE);
        } else {
            txtRight.setVisibility(GONE);
        }
    }

    /**
     * 设置右边文字颜色
     *
     * @param rightTextColor
     */
    public void setRightTextColor(int rightTextColor) {
        if (rightTextColor != -1) {
            txtRight.setTextColor(rightTextColor);
        }
    }

    /**
     * 设置右边icon
     *
     * @param rightIconRes
     */
    public void setRightIconRes(int rightIconRes) {
        if (rightIconRes != -1) {
            iconRight.setVisibility(VISIBLE);
            iconRight.setImageResource(rightIconRes);
        } else {
            iconRight.setVisibility(GONE);
        }
    }

    /**
     * 设置返回键监听
     *
     * @param listener
     */
    public void setOnBackListener(OnBackListener listener) {
        this.backListener = listener;
    }

    /**
     * 设置右边文本点击事件
     *
     * @param listener
     */
    public void setOnRightTextListener(OnRightTextListener listener) {
        this.rightTextListener = listener;
    }

    /**
     * 设置右边图片点击事件
     *
     * @param listener
     */
    public void setOnRightIconListener(OnRightIconListener listener) {
        this.rightIconListener = listener;
    }


    /**
     * 显示分隔线
     */
    public void showDivider(boolean show) {
        if (show) {
            divider.setVisibility(VISIBLE);
        } else {
            divider.setVisibility(GONE);
        }
    }

    /**
     * 右边布局添加子view
     *
     * @param child
     */
    public void addToRightMore(View child) {
        rightMore.setVisibility(VISIBLE);
        rightMore.addView(child);
    }

    /**
     * 初始化更多弹窗，更多图标就显示
     *
     * @param data     数据列表
     * @param listener 点击回调
     */
    public void addMorePopView(List<MorePopBean> data, MorePopView.OnSelectListener listener) {
        MorePopView more = new MorePopView(getContext());
        ViewGroup.LayoutParams lp = new LayoutParams(ScreenUtils.dp2px(getContext(), 32), ScreenUtils.dp2px(getContext(), 32));
        more.setLayoutParams(lp);
        more.initData(data);
        more.setOnSelectListener(listener);
        addToRightMore(more);
    }

    /**
     * 获取弹出按键
     *
     * @return
     */
    public MorePopView getMorePopView() {
        if (rightMore.getChildCount() > 0) {
            return (MorePopView) rightMore.getChildAt(0);
        }
        return null;
    }

    @OnClick({R2.id.back, R2.id.txt_right, R2.id.icon_right})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.back) {
            if (backListener != null) {
                backListener.onBackClick();
            }
        } else if (view.getId() == R.id.txt_right) {

            if (rightTextListener != null) {
                rightTextListener.onRightTextClick();
            }
        } else if (view.getId() == R.id.icon_right) {
            if (rightIconListener != null) {
                rightIconListener.OnRightIconClick();
            }

        }
    }

    /**
     * 返回监听
     */
    public interface OnBackListener {
        void onBackClick();
    }

    /**
     * 右边文字监听
     */
    public interface OnRightTextListener {
        void onRightTextClick();
    }

    /**
     * 右边图标监听
     */
    public interface OnRightIconListener {
        void OnRightIconClick();
    }
}
