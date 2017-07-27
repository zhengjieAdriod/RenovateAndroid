package com.baiducloud.dawnoct.renovateproject.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker.LoginWorkerActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.casesList.CasesListActivity;
import com.baiducloud.dawnoct.renovateproject.Presenter.MainPresenter;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.MainPagerAdapter;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.MainRecyclerAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView1)
    TextView mTextView;//案例
    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.a, R.drawable.b, R.drawable.a, R.drawable.b, R.drawable.a};
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定activity
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //todo 跳转到管家版登录页
//                Intent intent = new Intent(MainActivity.this, AddNewCaseActivity.class);
                Intent intent = new Intent(MainActivity.this, LoginWorkerActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initData();
        initView();
    }

    private void initView() {
        //设置Page间间距
        mViewPager.setPageMargin(10);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MainPagerAdapter(imgRes));
        //setPageTransformer 决定动画效果
        /**##目前可选动画
         AlphaPageTransformer
         RotateDownPageTransformer
         RotateUpPageTransformer
         RotateYTransformer
         NonPageTransformer
         ScaleInTransformer
         动画间可以自由组合，例*/
//        mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        List<Post.ServiceBean> list = new ArrayList<>();
        list.add(new Post.ServiceBean("较少墙面", 25, "适用于无墙面问题的房屋"));
        list.add(new Post.ServiceBean("轻微损坏的墙面", 29, "适用于轻微损坏的屋面的房屋"));
        list.add(new Post.ServiceBean("较严重损坏的墙面", 52, "适用于较严重损坏的墙面的房屋"));
        list.add(new Post.ServiceBean("严重损坏的墙面", 92, "适用于严重损坏的墙面的房屋"));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MainRecyclerAdapter(this, R.layout.servive_item, list));
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转案例列表
                Intent intent = new Intent(MainActivity.this, CasesListActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initData() {
        mainPresenter = new MainPresenter(this);
//        mainPresenter.getData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }
}
