package com.baiducloud.dawnoct.renovateproject.Modules.postList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.AddNewCaseActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.postPost.UpadateCaseActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.PostListAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/21.
 */

public class ListPostActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view_posts)
    RecyclerView mRecyclerView;
    PostListAdapter mDownMenuAdapter;
    ListPostPresenter mPresenter;
    private ArrayList<Post> list;
    Post.WorkerBean workerBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        initToolBar(mToolbar, true, "所在项目");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_project);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳至增加项目页面
                Intent intent = new Intent(ListPostActivity.this, AddNewCaseActivity.class);
                intent.putExtra("worker", workerBean);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        mPresenter = new ListPostPresenter(this);
        list = new ArrayList<>();
        mDownMenuAdapter = new PostListAdapter(list);
        mDownMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳至修改页
                Intent intent = new Intent(ListPostActivity.this, UpadateCaseActivity.class);
                intent.putExtra("worker", workerBean);
                intent.putExtra("post", mDownMenuAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDownMenuAdapter);
        workerBean = new Post.WorkerBean();
        String pk = workerBean.getPk();//获得管家的pk
        mPresenter.getData("1");
    }

    public void setNetData(List<Post> posts) {
        mDownMenuAdapter.setNewData(posts);
    }
}
