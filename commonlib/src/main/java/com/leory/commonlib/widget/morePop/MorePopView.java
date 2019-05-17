package com.leory.commonlib.widget.morePop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leory.commonlib.R;
import com.leory.commonlib.R2;
import com.leory.commonlib.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe : 通用选择的弹出框
 * Author : zhuly
 * Date : 2018-10-23
 */

public class MorePopView extends FrameLayout {
    private static final String TAG = MorePopView.class.getSimpleName();
    @BindView(R2.id.image_more)
    ImageView imageMore;
    private PopupWindow window;
    private OnSelectListener listener;
    private List<MorePopBean> data;
    private int selectPos = 0;
    private boolean change = false;
    MorePopAdapter adapter;
    private OnClickListener onClickListener;
    private boolean prohibit;//是否禁止弹出

    public MorePopView(@NonNull Context context) {
        this(context, null);
    }


    public MorePopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.view_more_pop, this);
        ButterKnife.bind(this, root);

    }

    /**
     * 初始化列表数据，实体类{@link MorePopBean}
     *
     * @param data
     */
    public void initData(List<MorePopBean> data) {
        this.data = data;
    }

    /**
     * 更新状态
     */
    public void refreshView() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置是否点击后改变状态
     *
     * @param change 默认false不改变
     */
    public void setSelectStateChange(boolean change) {
        this.change = change;
    }

    /**
     * 设置选择的位置，默认为0
     */
    public void setSelectPos(int pos) {
        this.selectPos = pos;
    }

    /**
     * 点击回调
     *
     * @param listener
     */
    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    private void popSelectList() {
        if (window == null) {

            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_more_pop, null, true);
            RecyclerView rcv = contentView.findViewById(R.id.rcv);
            rcv.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MorePopAdapter(data, change);
            adapter.setSelectPos(selectPos);
            rcv.setAdapter(adapter);
            window = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
            window.setBackgroundDrawable(new BitmapDrawable());
            window.showAsDropDown(imageMore, ScreenUtils.dp2px(getContext(), 4f), ScreenUtils.dp2px(getContext(), -8f));
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                    MorePopBean bean = adapter.getData().get(position);
                    if (listener != null && bean.isCanClick()) {
                        window.dismiss();
                        adapter.setSelectPos(position);
                        adapter.notifyDataSetChanged();
                        listener.onItemClick(position, bean.getName());
                    }
                }
            });
        } else {
            window.showAsDropDown(imageMore, ScreenUtils.dp2px(getContext(), 4f), ScreenUtils.dp2px(getContext(), -8f));
        }

    }

    @OnClick(R2.id.image_more)
    public void onViewClicked() {
        if (onClickListener != null) {
            onClickListener.onClick(imageMore);
        }
        if(!prohibit) {
            popSelectList();
        }
    }

    public void setClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 是否禁止弹出
     */
    public void setProhibit(boolean prohibit){
        this.prohibit=prohibit;
    }

    public interface OnSelectListener {
        void onItemClick(int pos, String name);
    }
}
