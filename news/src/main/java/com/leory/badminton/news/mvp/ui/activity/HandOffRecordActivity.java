package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerHandOffRecordComponent;
import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.model.bean.HandOffBean;
import com.leory.badminton.news.mvp.presenter.HandOffRecordPresenter;
import com.leory.badminton.news.mvp.ui.adapter.HandOffRecordAdapter;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.image.ImageLoader;
import com.leory.commonlib.utils.ImageUtils;
import com.leory.commonlib.utils.ToastUtils;
import com.leory.commonlib.widget.XSDToolbar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Describe : 交手记录
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
public class HandOffRecordActivity extends BaseActivity<HandOffRecordPresenter> implements HandOffRecordContract.View {

    private static final String KEY_HAND_OFF_URL = "key_hand_off_url";
    @BindView(R2.id.toolbar)
    XSDToolbar toolbar;
    @BindView(R2.id.rcv)
    RecyclerView rcv;
    View player11,player12,player21,player22;
    ImageView head11,head12,head21,head22;
    ImageView flag11,flag12,flag21,flag22;
    TextView name11,name12,name21,name22;
    TextView ranking1,ranking2;
    TextView score1,score2;

    private HandOffRecordAdapter adapter;
    public static void launch(Activity preActivity, String handOffUrl) {
        preActivity.startActivity(new Intent(preActivity, HandOffRecordActivity.class).putExtra(KEY_HAND_OFF_URL, handOffUrl));
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        DaggerHandOffRecordComponent.builder()
                .view(this)
                .recordUrl(getIntent().getStringExtra(KEY_HAND_OFF_URL))
                .appComponent((AppComponent) component)
                .build()
                .inject(this);
        return null;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hand_off;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setOnBackListener(() -> finish());
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter=new HandOffRecordAdapter(new ArrayList<>()));
        View head=LayoutInflater.from(this).inflate(R.layout.head_hand_off,null);
        player11=head.findViewById(R.id.player11);
        player12=head.findViewById(R.id.player12);
        player21=head.findViewById(R.id.player21);
        player22=head.findViewById(R.id.player22);
        head11=head.findViewById(R.id.head11);
        head12=head.findViewById(R.id.head12);
        head21=head.findViewById(R.id.head21);
        head22=head.findViewById(R.id.head22);
        flag11=head.findViewById(R.id.flag11);
        flag12=head.findViewById(R.id.flag12);
        flag21=head.findViewById(R.id.flag21);
        flag22=head.findViewById(R.id.flag22);
        name11=head.findViewById(R.id.player11_name);
        name12=head.findViewById(R.id.player12_name);
        name21=head.findViewById(R.id.player21_name);
        name22=head.findViewById(R.id.player22_name);
        ranking1=head.findViewById(R.id.player1_rank);
        ranking2=head.findViewById(R.id.player2_rank);
        score1=head.findViewById(R.id.score1);
        score2=head.findViewById(R.id.score2);
        adapter.addHeaderView(head);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showHandOffView(HandOffBean bean) {
        if(TextUtils.isEmpty(bean.getPlayer12Name())){
            player12.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(bean.getPlayer22Name())){
            player22.setVisibility(View.GONE);
        }
        name11.setText(bean.getPlayer1Name());
        name12.setText(bean.getPlayer12Name());
        name21.setText(bean.getPlayer2Name());
        name22.setText(bean.getPlayer22Name());
        score1.setText(bean.getPlayer1Win());
        score2.setText(bean.getPlayer2Win());
        ranking1.setText(bean.getPlayer1Ranking());
        ranking2.setText(bean.getPlayer2Ranking());
        ImageUtils.loadImage(this,head11,bean.getPlayer1HeadUrl());
        ImageUtils.loadImage(this,head12,bean.getPlayer12HeadUrl());
        ImageUtils.loadImage(this,head21,bean.getPlayer2HeadUrl());
        ImageUtils.loadImage(this,head22,bean.getPlayer22HeadUrl());
        ImageUtils.loadImage(this,flag11,bean.getPlayer1Flag());
        ImageUtils.loadImage(this,flag12,bean.getPlayer12Flag());
        ImageUtils.loadImage(this,flag21,bean.getPlayer2Flag());
        ImageUtils.loadImage(this,flag22,bean.getPlayer22Flag());
        adapter.setNewData(bean.getRecordList());

    }
}
