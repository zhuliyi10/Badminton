package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerLiveComponent;
import com.leory.badminton.news.mvp.contract.LiveContract;
import com.leory.badminton.news.mvp.model.bean.LiveBean;
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean;
import com.leory.badminton.news.mvp.presenter.LivePresenter;
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity;
import com.leory.badminton.news.mvp.ui.adapter.LiveDetailAdapter;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;
import com.leory.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe :直播fragment
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
public class LiveFragment extends BaseLazyLoadFragment<LivePresenter> implements LiveContract.View {
    @BindView(R2.id.txt_next_live)
    TextView txtNextLive;
    @BindView(R2.id.match_name)
    TextView matchName;
    @BindView(R2.id.match_date)
    TextView matchDate;
    @BindView(R2.id.txt_city)
    TextView txtCity;
    @BindView(R2.id.txt_country)
    TextView txtCountry;
    @BindView(R2.id.image_flag)
    ImageView imageFlag;
    @BindView(R2.id.progress)
    FrameLayout progress;
    @BindView(R2.id.item_live)
    LinearLayout itemLive;
    @BindView(R2.id.rcv)
    RecyclerView rcv;


    private LiveDetailAdapter liveDetailAdapter;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLiveComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(liveDetailAdapter=new LiveDetailAdapter(new ArrayList<>()));
    }

    @Override
    protected void lazyLoadData() {
        presenter.requestData();
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showLiveData(LiveBean bean) {
        matchName.setText(bean.getMatchName());
        matchDate.setText(bean.getMatchDate());
        txtCity.setText(bean.getCity());
        txtCountry.setText(bean.getCountry());
        ImageConfig config = new ImageConfig.Builder()
                .imageView(imageFlag)
                .url(bean.getCountryFlag())
                .build();
        AppUtils.obtainImageLoader().loadImage(getContext(), config);
        itemLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatchDetailActivity.launch(getActivity(), bean.getDetailUrl(), "");
            }
        });
    }

    @Override
    public void showLiveDetail(List<LiveDetailBean> data) {
        txtNextLive.setText("直播中");
        liveDetailAdapter.setNewData(data);
    }

}
