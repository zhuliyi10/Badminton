package com.leory.badminton.news.mvp.ui.widget.spinner;

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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.commonlib.utils.ScreenUtils;
import com.leory.commonlib.widget.morePop.MorePopBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe : 通用选择的弹出框
 * Author : leory
 * Date : 2019-05-19
 */

public class SpinnerPopView extends FrameLayout {
    private static final String TAG = SpinnerPopView.class.getSimpleName();
    @BindView(R2.id.txt_name)
    TextView txtName;

    private PopupWindow window;
    private OnSelectListener listener;
    private List<String> data;
    private int selectPos = 0;
    private boolean change = true;
    SpinnerPopAdapter adapter;
    private boolean prohibit;//是否禁止弹出

    public SpinnerPopView(@NonNull Context context) {
        this(context, null);
    }


    public SpinnerPopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.view_spinner_pop, this);
        ButterKnife.bind(this, root);
    }

    /**
     * 初始化列表数据，实体类{@link MorePopBean}
     *
     * @param data
     */
    public void initData(List<String> data) {
        initData(data, 0);
    }

    /**
     * 初始化列表数据，实体类{@link MorePopBean}
     * 和位置
     *
     * @param data
     */
    public void initData(List<String> data, int selectPos) {
        this.data = data;
        setSelectPos(selectPos);
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
        if (data != null && data.size() > pos) {
            txtName.setText(data.get(pos));
        }
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

            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_spinner_list, null, true);
            RecyclerView rcv = contentView.findViewById(R.id.rcv);
            rcv.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SpinnerPopAdapter(data, change);
            adapter.setSelectPos(selectPos);
            rcv.setAdapter(adapter);
            window = new PopupWindow(contentView, getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
            window.setBackgroundDrawable(new BitmapDrawable());
            window.showAsDropDown(this, 0, ScreenUtils.dp2px(getContext(), 8f));
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                    String item = adapter.getData().get(position);
                    if (listener != null) {
                        window.dismiss();
                        adapter.setSelectPos(position);
                        setSelectPos(position);
                        adapter.notifyDataSetChanged();
                        listener.onItemClick(position, item);
                    }
                }
            });
        } else {
            window.showAsDropDown(this, 0, ScreenUtils.dp2px(getContext(), 8f));
        }

    }


    /**
     * 是否禁止弹出
     */
    public void setProhibit(boolean prohibit) {
        this.prohibit = prohibit;
    }

    @OnClick(R2.id.root)
    public void onViewClicked() {
        if (!prohibit) {
            popSelectList();
        }
    }

    public interface OnSelectListener {
        void onItemClick(int pos, String name);
    }
}
