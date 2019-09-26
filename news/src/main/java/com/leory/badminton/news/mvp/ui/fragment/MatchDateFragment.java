package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.MatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchDateBean;
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean;
import com.leory.badminton.news.mvp.presenter.MatchDatePresenter;
import com.leory.badminton.news.mvp.ui.activity.HandOffRecordActivity;
import com.leory.badminton.news.mvp.ui.adapter.MatchDateAdapter;
import com.leory.badminton.news.mvp.ui.widget.MatchTabView;
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Describe : 比赛赛程fragment
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchDateFragment extends BaseLazyLoadFragment<MatchDatePresenter> implements MatchDetailContract.MatchDateView {
    private static final String KEY_TAB_DATE = "key_tab_date";
    private static final String KEY_COUNTRY = "key_country";
    @Inject
    List<MatchTabDateBean> dateBeans;

    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.progress)
    FrameLayout progress;
    MatchTabView tabDate;
    TextView txtFilter;
    TextView txtState;
    private MatchDateAdapter dateAdapter;

    public static MatchDateFragment newInstance(List<MatchTabDateBean> dateBeans, String country) {
        MatchDateFragment fragment = new MatchDateFragment();
        Bundle args = new Bundle();
        args.putString(KEY_COUNTRY, country);
        args.putSerializable(KEY_TAB_DATE, (Serializable) dateBeans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        ((MatchDetailComponent) component).buildMatchDateComponent()
                .view(this)
                .tabDates((List<MatchTabDateBean>) getArguments().getSerializable(KEY_TAB_DATE))
                .country(getArguments().getString(KEY_COUNTRY))
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    protected void lazyLoadData() {
        if (dateBeans != null && dateBeans.size() > 0) {
            List<String> names = new ArrayList<>();
            for (MatchTabDateBean bean : dateBeans) {
                names.add(bean.getName());
            }
            tabDate.initData(names);
            tabDate.setOnChildClickListener(new MatchTabView.OnChildClickListener() {
                @Override
                public void onClick(TextView tv, int position) {
                    tabDate.setSelectPos(position);
                    presenter.requestPosition(position, null);
                }
            });

            tabDate.setSelectPos(0);
            presenter.requestPosition(0, null);
        }

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_date, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.addItemDecoration(new MatchDateItemDecoration(getContext()));
        rcv.setAdapter(dateAdapter = new MatchDateAdapter(new ArrayList<>()));
        dateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                txtFilter.setText("全部");
                txtState.setText("场地");
                presenter.requestPosition(tabDate.getSelectPos(), dateAdapter.getData().get(position).getMatchId());
            }
        });
        ConstraintLayout head = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.head_match_date, null);
        txtFilter = head.findViewById(R.id.txt_filter);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFilter.getText().toString().equals("国羽")) {
                    txtFilter.setText("全部");
                } else {
                    txtFilter.setText("国羽");
                }
                presenter.filter(txtFilter.getText().toString(), txtState.getText().toString());
            }
        });
        txtState = head.findViewById(R.id.txt_state);
        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtState.getText().toString().equals("场地")) {
                    txtState.setText("时间");
                } else {
                    txtState.setText("场地");
                }
                presenter.filter(txtFilter.getText().toString(), txtState.getText().toString());
            }
        });
        tabDate = head.findViewById(R.id.tab_date);
        dateAdapter.addHeaderView(head);
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        rcv.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
        rcv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showDateData(List<MatchDateBean> data) {
        dateAdapter.setNewData(data);
    }

    @Override
    public void toHistoryDetail(String handOffUrl) {
        if (!TextUtils.isEmpty(handOffUrl)) {
            HandOffRecordActivity.Companion.launch(getActivity(), handOffUrl);
        }
    }

    @Override
    public String getFilterText() {
        return txtState.getText().toString();
    }
}
