package com.baiducloud.dawnoct.renovateproject.Modules.postList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.AddNewCaseActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.postPost.UpadateCaseActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.PostListAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    Post mPost;//记录被点击的Item上的post
    int mPositon;//记录被点击的Item上的位置(实现在修改页,数据修改后及时同步)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
        //注册事件
        EventBus.getDefault().register(this);
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
                mPost = mDownMenuAdapter.getItem(position);
                mPositon = position;
                //跳至修改页
                Intent intent = new Intent(ListPostActivity.this, UpadateCaseActivity.class);
                intent.putExtra("worker", workerBean);
                intent.putExtra("post", mDownMenuAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDownMenuAdapter);
        workerBean = (Post.WorkerBean) getIntent().getSerializableExtra("worker");
        if(workerBean==null){
            return;
        }
        String pk = workerBean.getPk();//获得管家的pk
        mPresenter.getData(pk);
    }

    public void setNetData(List<Post> posts) {
        mDownMenuAdapter.setNewData(posts);
    }

    //从add页面获得新的post数据,并展示在顶部
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(Post post) {
        //修改页面数据同步
        if(mPost!=null&&mPost.getPk().equals(post.getPk())){
//            mPost = post;
            mDownMenuAdapter.setData(mPositon,post);
            mDownMenuAdapter.notifyItemChanged(mPositon);
            return;
        }
        //添加页数据同步
        mDownMenuAdapter.addDataTop(post);
        mRecyclerView.smoothScrollToPosition(0);
        Log.e("zj", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
