package com.baiducloud.dawnoct.renovateproject.Modules.casesList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baiducloud.dawnoct.renovateproject.Modules.detailCase.DetailActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.Wedgits.EmptyLayout;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.CasesListAdapter;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.GirdDropDownAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class CasesListActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    GirdDropDownAdapter scaleAdapter;
    GirdDropDownAdapter typeAdapter;
    //    GirdDropDownAdapter lvAdapter;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private List<View> popupViews = new ArrayList<>();
    LayoutInflater mInflater;
    private String headers[] = {"施工状态", "位置区域"};
    public String scales[] = {"不限", "已完工", "施工中"};//12345对应
    public String types[] = {"不限", "朝阳区", "通州区", "海淀区", "东城区"};//功能类型
    List<Post> list;
    CasesListAdapter downMenuAdapter;
    private CasesPresenter casesPresenter;
    public static int PAGE_SIZE = 5;
    public boolean hasTotal = false;
    int mPage = 1;
    String district="";
    String state="";
     SkeletonScreen skeletonScreen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases_activity_layout);
        //绑定activity
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        casesPresenter = new CasesPresenter(this);
        casesPresenter.getData(mPage, "", "");//初始化数据
    }

    private void initView() {
        mInflater = LayoutInflater.from(getApplicationContext());
        initToolBar(mToolbar, true, "案例列表");
        //init city menu
        final ListView scaleView = new ListView(this);
        scaleAdapter = new GirdDropDownAdapter(this, Arrays.asList(scales));
        scaleView.setDividerHeight(0);
        scaleView.setLayoutParams(new ViewGroup.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT));
        scaleView.setAdapter(scaleAdapter);


        //init age menu
        final ListView typeView = new ListView(this);
        typeAdapter = new GirdDropDownAdapter(this, Arrays.asList(types));
        typeView.setDividerHeight(0);
        typeView.setLayoutParams(new ViewGroup.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT));
        typeView.setAdapter(typeAdapter);

        //init popupViews
        popupViews.add(scaleView);//添加View
        popupViews.add(typeView);

        //add item click event
        scaleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scaleAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : scales[position]);
                mDropDownMenu.closeMenu();
                if (position > 0) {
                    state = scales[position];
                } else {
                    state = "";
                }
                mPage = 1;
                casesPresenter.getData(mPage, district, state);
            }
        });

        typeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : types[position]);
                mDropDownMenu.closeMenu();
                if (position > 0) {
                    district = types[position];
                } else {
                    district = "";
                }
                mPage = 1;
                casesPresenter.getData(mPage, district, state);
            }
        });
         /*todo 自定义的内容列表*/
        View view = LayoutInflater.from(this).inflate(R.layout.bridge_recycler_acitivity, null);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mEmptyLayout = (EmptyLayout) view.findViewById(R.id.loading_layout);
        initSwipeRefresh();//todo  初始化下拉刷新(并设置监听动作)
//        mEmptyLayout.hide();
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        downMenuAdapter = new CasesListAdapter(list);

        downMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //go detail page
                Post post = (Post) adapter.getItem(position);
                Intent intent = new Intent(CasesListActivity.this, DetailActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });
        //todo 加载更多(刷新交给原生的SwipeRefreshLayout布局)
        downMenuAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                downMenuAdapter.loadMoreEnd(true);//true is gone,false is visible
//                casesPresenter.getData(mPage);
                if (mSwipeRefresh != null) {
                    mSwipeRefresh.setEnabled(false);//先设置下拉刷新的失效
                }
//                if(hasTotal){//没有更多数据
////                    downMenuAdapter.loadMoreEnd(false);
//                }else {
//                    casesPresenter.getData(mPage);//todo 获得更多数据的接口.
//                }
                if (downMenuAdapter.getData().size() < 4) {//请求到的数据少于一页的情况
                    //没有更多数据true is gone,false is visible(false时，会显示没有更多数据的提示)
                    downMenuAdapter.loadMoreEnd(false);
                } else { //数据大于一页的情况
                    if (hasTotal) {//此刻数据已经全部加载到list中的情况
                        //false时会显示没有更多数据的提示
                        downMenuAdapter.loadMoreEnd(false);//true is gone,false is visible
                        mSwipeRefresh.setEnabled(true);//设置下拉刷新的生效
                    } else {//此刻还没有加载完的情况（需要去请求接口，加载更多）
                        casesPresenter.getData(mPage, district, state);//todo 获得更多数据的接口.
                    }
                }
            }
        }, mRecyclerView);
//        mRecyclerView.setAdapter(downMenuAdapter);
        skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(downMenuAdapter)
//                .load(R.layout.item_skeleton_news)
                .count(10)
                .show();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 5000);

        //todo 组装mDropDownMenu
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view);

    }

    // TODO  监听到了下拉动作(调刷新的接口)
    public void getReFreshData() {
        mPage = 1;
        casesPresenter.getData(1, district, state);//todo 获得更多数据的接口.
    }

    public void loadDataSuccess(List<Post> list) {
        skeletonScreen.hide();
        if (list.size() == 0) {
            hasTotal = true;
            downMenuAdapter.loadMoreComplete();//todo 加载完成，收起底部加载更多字样
            return;
        }
        hasTotal = false;
        mPage = mPage + 1;
        if (mPage == 2) {//刷新成功后情况
//            skeletonScreen.hide();//收起预加载占位
            downMenuAdapter.setNewData(list);
            return;
        }
        //其他
        downMenuAdapter.addData(list);
        downMenuAdapter.loadMoreComplete();//todo 加载完成，收起底部加载更多字样

    }

    /*加载更多失败时，由P层回调*/
    public void errorLoadMore() {
        downMenuAdapter.loadMoreFail();//其中封装了点击重新加载
        mSwipeRefresh.setEnabled(true);//设置下拉刷新的生效
    }
}
