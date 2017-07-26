package com.baiducloud.dawnoct.renovateproject.Modules.casesList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.baiducloud.dawnoct.renovateproject.ZAdapter.DownMenuAdapter;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.GirdDropDownAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class CasesActivity extends BaseActivity {
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
    DownMenuAdapter downMenuAdapter;
    private CasesPresenter casesPresenter;

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
        casesPresenter.getData();
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
                String scale = scales[position];
//                mScale = position;
//                presenter.getNetData(loginBean, 1, mScale, mType, mLv, mBridgeType, false);//默认是false
            }
        });

        typeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : types[position]);
                mDropDownMenu.closeMenu();
//                if (position > 0) {
//                    mType = types[position];
//                } else {
//                    mType = "";
//                }
//                presenter.getNetData(loginBean, 1, mScale, mType, mLv, mBridgeType, false);//默认是false
            }
        });
         /*todo 自定义的内容列表*/
        View view = LayoutInflater.from(this).inflate(R.layout.bridge_recycler_acitivity, null);
//        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
//        mEmptyLayout = (EmptyLayout) view.findViewById(R.id.loading_layout);

//        mEmptyLayout.hide();
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        downMenuAdapter = new DownMenuAdapter(list);

        downMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //go detail page
                Post post = (Post) adapter.getItem(position);
                Intent intent = new Intent(CasesActivity.this, DetailActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(downMenuAdapter);

        //todo 组装mDropDownMenu
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view);

    }

    /*第一次获得数据或者刷新时，有P层回调*/
    public void loadDataFirst(List<Post> list) {
        this.list = list;
        downMenuAdapter.setNewData(list);
    }


}
