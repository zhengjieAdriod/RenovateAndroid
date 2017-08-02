package com.baiducloud.dawnoct.renovateproject.Modules.postList;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.AddNewCaseActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.postPost.UpadateCaseActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.PostListAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.Zutils.ToastUtils;
import com.baiducloud.dawnoct.renovateproject.Zutils.ViewUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;

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
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mDownMenuAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }
            //成功删除的回调
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtils.showToast("tank");
                ToastUtils.showToast(R.string.album_title_activity_gallery);
            }
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtils.showToast("clearView");
            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Logger.e("rr" + "onItemSwipeMoving"+dX);
            }
        };
        // 开启滑动删除
        mDownMenuAdapter.enableSwipeItem();
        mDownMenuAdapter.setOnItemSwipeListener(onItemSwipeListener);

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
    DialogPlus dialogText;
    //删除文字的弹出框
    public void confirmDialogText(final int pos) {
        if (dialogText == null) {
            dialogText = DialogPlus.newDialog(this)//I cannot because of
                    .setContentHolder(new ViewHolder(R.layout.dialog_delete_pain))
                    .setContentWidth(ViewUtils.getScreenWidth(this) / 3)  // called by people
                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)// wrap_content match_parent left_right bottom up button test text_view
                    .setGravity(Gravity.CENTER)//setGravity leave go just do it by the way
                    .setExpanded(false, 0)  //  gradle in peace world people many true false success
                    .setOnClickListener(new com.orhanobut.dialogplus.OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialogPlus, View view) {
                            switch (view.getId()) {
                                case R.id.tv_cancel:
//                                    mDownMenuAdapter.notifyDataSetChanged();
                                    mDownMenuAdapter.notifyItemChanged(pos);
                                    dialogPlus.dismiss();
                                    break;
                                case R.id.tv_confirm:
                                    Post item = mDownMenuAdapter.getItem(pos);
                                    dialogPlus.dismiss();
                                    break;
                            }
                        }
                    })
                    .create();
        }
        dialogText.show();
    }
    public void setNetData(List<Post> posts) {
        mDownMenuAdapter.setNewData(posts);
    }

    //从add页面获得新的post数据,并展示在顶部
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(Post post) {
        //修改页面数据同步
        if(mPost!=null&&mPost.getPk().equals(post.getPk())){
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
