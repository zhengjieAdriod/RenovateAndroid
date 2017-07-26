package com.baiducloud.dawnoct.renovateproject.Modules.detailCase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.DetailAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/10.
 */

public class DetailActivity extends BaseActivity {
    //管家姓名
    @BindView(R.id.worker_name)
    TextView worken_name;
    //开工日期
    @BindView(R.id.start_date01)
    TextView start_date01;
    //预计工期
    @BindView(R.id.predict_date01)
    TextView predict_date01;
    //施工方案
    @BindView(R.id.scheme01)
    TextView scheme01;
    @BindView(R.id.start_in_imags)
    RecyclerView start_in_imags;
    @BindView(R.id.protection_imags)
    RecyclerView protection_imags;
    @BindView(R.id.work_site_imags)
    RecyclerView work_site_imags;
    @BindView(R.id.finish_imags)
    RecyclerView finish_imags;
    DetailAdapter mAdapter01, mAdapter02, mAdapter03, mAdapter04;
    List<String> list01, list02, list03, list04;

    Post post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases_detail_layout);
        //绑定activity
        ButterKnife.bind(this);
        post = (Post) getIntent().getSerializableExtra("post");
        initData();
        initView();

    }

    private void initData() {
        list01 = new ArrayList<>();
//        list01.add(post.getStart_in());
        list02 = new ArrayList<>();
//        list02.add(post.getProtection());
        list03 = new ArrayList<>();
//        list03.add(post.getWork_site());
        list04 = new ArrayList<>();
//        list04.add(post.getFinish());

    }

    private void initView() {
        worken_name.setText(post.getWorker().getName());
        start_date01.setText(post.getCreated_time());
        predict_date01.setText(post.getPredict());
        scheme01.setText(post.getScheme().getMeasures() + "/" + post.getScheme().getDamage_des());
        start_in_imags.setLayoutManager(new LinearLayoutManager(this));
        mAdapter01 = new DetailAdapter(R.layout.detail_item, list01);
        mAdapter02 = new DetailAdapter(R.layout.detail_item, list02);
        mAdapter03 = new DetailAdapter(R.layout.detail_item, list03);
        mAdapter04 = new DetailAdapter(R.layout.detail_item, list04);
        start_in_imags.setAdapter(mAdapter01);
        protection_imags.setLayoutManager(new LinearLayoutManager(this));
        protection_imags.setAdapter(mAdapter02);
        work_site_imags.setLayoutManager(new LinearLayoutManager(this));
        work_site_imags.setAdapter(mAdapter03);
        finish_imags.setLayoutManager(new LinearLayoutManager(this));
        finish_imags.setAdapter(mAdapter04);

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    public void loadDataFirst(List<Post> posts) {

    }
}