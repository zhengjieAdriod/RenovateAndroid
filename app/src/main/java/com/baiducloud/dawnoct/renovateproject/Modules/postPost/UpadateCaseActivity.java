package com.baiducloud.dawnoct.renovateproject.Modules.postPost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker.GlobalData;
import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.Wedgits.MyGridView;
import com.baiducloud.dawnoct.renovateproject.Wedgits.StateButton;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.PhotoAdapter;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.ServiceChoiceAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;

import okhttp3.RequestBody;

/**
 * Created by DawnOct on 2017/7/11.
 */

public class UpadateCaseActivity extends BaseActivity {
    @BindView(R.id.district_ed)
    EditText district_ed;
    @BindView(R.id.village_ed)
    EditText village_ed;
    @BindView(R.id.service_ed)
    EditText service_ed;
    @BindView(R.id.predict_ed)
    EditText predict_ed;
    @BindView(R.id.fact_ed)
    EditText fact_ed;
    @BindView(R.id.create_ed)
    EditText create_ed;
    @BindView(R.id.state_ed)
    EditText state_ed;
    @BindView(R.id.area_ed)
    EditText area_ed;

    @BindView(R.id.gridview_01)
    MyGridView gridview_01;
    @BindView(R.id.gridview_02)
    MyGridView gridview_02;
    @BindView(R.id.gridview_03)
    MyGridView gridview_03;
    @BindView(R.id.gridview_04)
    MyGridView gridview_04;
    @BindView(R.id.gridview_head)
    MyGridView gridview_head;
    @BindView(R.id.start_in_post)
    ImageView start_in_post;
    @BindView(R.id.protection_post)
    ImageView protection_post;
    @BindView(R.id.WorkSite__post)
    ImageView WorkSite__post;
    @BindView(R.id.finish__post)
    ImageView finish__post;
    UpadateCasePresenter processPresenter;
    private List<Imagepxh> Imagepxhlist01;
    private List<Imagepxh> Imagepxhlist02;
    private List<Imagepxh> Imagepxhlist03;
    private List<Imagepxh> Imagepxhlist04;
    private List<Imagepxh> Imagepxhlist05;
    PhotoAdapter myAdapter, myAdapter02, myAdapter03, myAdapter04, myAdapterHead;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.service_in_post)
    StateButton service_in_post;
    Post.WorkerBean worker;
    Post mPost;
    TranslateAnimation translateAnimation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 取消进入该界面弹出输入法
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_process_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        initToolBar(mToolbar, true, "查看并编辑项目");
        translateAnimation= new TranslateAnimation(0, 0, 0, 20);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setDuration(500);
//        start_in_post.startAnimation(translateAnimation);

        //初始化多个EditText
        district_ed.setText(mPost.getDistrict());
        village_ed.setText(mPost.getVillage());
        Post.ServiceBean service = mPost.getService();
        if (service != null)
            service_ed.setText(service.getName());
        predict_ed.setText(mPost.getPredict());
        fact_ed.setText(mPost.getFact());
        create_ed.setText(mPost.getCreated_time());
        state_ed.setText(mPost.getState());
        area_ed.setText(mPost.getArea());

        //对图片组的初始化(包括头图)
        Imagepxhlist01 = new ArrayList<Imagepxh>();//图片的uri
        Imagepxhlist02 = new ArrayList<Imagepxh>();//图片的uri
        Imagepxhlist03 = new ArrayList<Imagepxh>();//图片的uri
        Imagepxhlist04 = new ArrayList<Imagepxh>();//图片的uri
        Imagepxhlist05 = new ArrayList<Imagepxh>(1);//图片的uri
        myAdapterHead = new PhotoAdapter(UpadateCaseActivity.this, Imagepxhlist05, 5);
        myAdapter = new PhotoAdapter(UpadateCaseActivity.this, Imagepxhlist01, 1);
        myAdapter02 = new PhotoAdapter(UpadateCaseActivity.this, Imagepxhlist02, 2);
        myAdapter03 = new PhotoAdapter(UpadateCaseActivity.this, Imagepxhlist03, 3);
        myAdapter04 = new PhotoAdapter(UpadateCaseActivity.this, Imagepxhlist04, 4);
        gridview_head.setAdapter(myAdapterHead);
        gridview_01.setAdapter(myAdapter);// gridview适配。
        gridview_02.setAdapter(myAdapter02);// gridview适配。
        gridview_03.setAdapter(myAdapter03);// gridview适配。
        gridview_04.setAdapter(myAdapter04);// gridview适配。
        String pk = mPost.getPk();
        processPresenter.getData(mPost);//访问网络,获得图片租

    }

    /*展示获得的数据,图片租*/
    public void showView(PhotoesInfo info) {
        List<PhotoesInfo.Photo> startImages = info.getStartImages();
        List<PhotoesInfo.Photo> protectionImages = info.getProtectionImages();
        List<PhotoesInfo.Photo> workSiteImages = info.getWorkSiteImages();
        List<PhotoesInfo.Photo> finishImages = info.getFinishImages();
        Imagepxhlist01.addAll(startImages);
        myAdapter.notifyDataSetChanged();
        Imagepxhlist02.addAll(protectionImages);
        myAdapter02.notifyDataSetChanged();
        Imagepxhlist03.addAll(workSiteImages);
        myAdapter03.notifyDataSetChanged();
        Imagepxhlist04.addAll(finishImages);
        myAdapter04.notifyDataSetChanged();
        //对头图的初始化
        PhotoesInfo.Photo headPhoto = new PhotoesInfo.Photo("", mPost.getPost_imag(), "des");
        if (!TextUtils.isEmpty(mPost.getPost_imag())) {
            Imagepxhlist05.add(headPhoto);
            myAdapterHead.notifyDataSetChanged();
        }
    }

    /**
     * 单独点击事件
     */
    public void singlePost(View view) {
        //获得用户输入的数据
        if (!getEditextData())
            return;
        switch (view.getTag().toString()) {
            case "start":
                Imagepxhlist01 = myAdapter.getList();
                postImages(mPost, Imagepxhlist01, "start_imag");
                start_in_post.startAnimation(translateAnimation);
                break;
            case "protect":
                Imagepxhlist02 = myAdapter02.getList();
                postImages(mPost, Imagepxhlist02, "protect_imag");
                protection_post.startAnimation(translateAnimation);
                break;
            case "work_site":
                Imagepxhlist03 = myAdapter03.getList();
                postImages(mPost, Imagepxhlist03, "work_site");
                WorkSite__post.startAnimation(translateAnimation);
                break;
            case "finish":
                Imagepxhlist04 = myAdapter04.getList();
                postImages(mPost, Imagepxhlist04, "finish");
                finish__post.startAnimation(translateAnimation);
                break;
            case "head":
                Imagepxhlist05 = myAdapterHead.getList();
                postImages(mPost, Imagepxhlist05, "head");
                break;
        }
    }

    private boolean getEditextData() {
        Editable text = district_ed.getText();
        String district = text.toString().trim();
        Editable text01 = village_ed.getText();
        String village = text01.toString().trim();
        Editable text02 = service_ed.getText();
        String service = text02.toString().trim();
        Editable text03 = predict_ed.getText();
        String predict = text03.toString().trim();
        Editable text04 = fact_ed.getText();
        String fact = text04.toString().trim();
        Editable text05 = create_ed.getText();
        String create = text05.toString().trim();
        Editable text06 = state_ed.getText();
        String state = text06.toString().trim();
        Editable text07 = area_ed.getText();
        String area = text07.toString().trim();
        mPost.setCreated_time(create);
        mPost.setDistrict(district);
        mPost.setVillage(village);
        mPost.setFact(fact);
        mPost.setPredict(predict);
        mPost.setService(new Post.ServiceBean(service, 20, "test"));
        mPost.setState(state);
        mPost.setArea(area);
        if (TextUtils.isEmpty(village) || TextUtils.isEmpty(district) || TextUtils.isEmpty(service)||TextUtils.isEmpty(area)
                || TextUtils.isEmpty(predict) || TextUtils.isEmpty(state) || TextUtils.isEmpty(create)) {
            return true;
        }
        return true;
    }

    /**
     * 单独上传图片
     */
    private void postImages(Post post, List<Imagepxh> list, String key) {
        if (list == null || list.size() == 0) {
            return;
        }
        Map<String, RequestBody> map = new HashMap<>();
        File file = null;
        for (int i = 0; i < list.size(); i++) {
            Imagepxh imagepxh = list.get(i);
            String des = imagepxh.getDes();
            //只对本地图片进行上传
            if (TextUtils.isEmpty(des)) {
                file = new File(imagepxh.getPath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                map.put(key + i + "\"; filename=\"" + file.getName(), requestBody);
            }

        }
        processPresenter.updatePostData(post, map);//todo 访问网路
    }

    private void initData() {
//        worker = (Post.WorkerBean) getIntent().getSerializableExtra("worker");
        mPost = (Post) getIntent().getSerializableExtra("post");
        worker = mPost.getWorker();
        processPresenter = new UpadateCasePresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                myAdapter.addAllPath(pathList);// list.add(); //记录了uri
                myAdapter.notifyDataSetChanged();
                Log.e("zj", pathList.get(0));

            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                myAdapter02.addAllPath(pathList);// list.add(); //记录了uri
                myAdapter02.notifyDataSetChanged();
                Log.e("zj", pathList.get(0));

            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                myAdapter03.addAllPath(pathList);// list.add(); //记录了uri
                myAdapter03.notifyDataSetChanged();
                Log.e("zj", pathList.get(0));

            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                myAdapter04.addAllPath(pathList);// list.add(); //记录了uri
                myAdapter04.notifyDataSetChanged();
                Log.e("zj", pathList.get(0));

            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
        if (requestCode == 105) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                //因为就一个头像,因此只取最后一个图片
                int size = pathList.size();
                myAdapterHead.setPath(pathList.get(size - 1));// list.add(); //记录了uri
                myAdapterHead.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }

    public void delete(String pk, int num) {
        String photoType = "";
        switch (num) {
            case 1:
                photoType = "start_imag";
                break;
            case 2:
                photoType = "protect_imag";
                break;
            case 3:
                photoType = "work_site";
                break;
            case 4:
                photoType = "finish";
                break;
        }
        Log.e("zj", "");
        processPresenter.deleteNetPhoto(pk, photoType);
    }

    //讲图片组替换为网络图片,便于之后的删除操作
    public void changeNetPhotos(List<PhotoesInfo.Photo> photos) {

        String des = photos.get(0).getDes();
        if (des.contains("start_imag")) {
            myAdapter.setNetPhotos(photos);
            start_in_post.clearAnimation();
        }
        if (des.contains("protect_imag")) {
            myAdapter02.setNetPhotos(photos);
            protection_post.clearAnimation();
        }
        if (des.contains("work_site")) {
            myAdapter03.setNetPhotos(photos);
            WorkSite__post.clearAnimation();
        }
        if (des.contains("finish")) {
            myAdapter04.setNetPhotos(photos);
            finish__post.clearAnimation();
        }
    }
    //使用弹窗,实现服务项目的选择
    public void chooseService(View view) {
        showDialogLogin("");
    }
    /**
     * 显示服务选择狂
     */
    int mPosition=-1;
    private void showDialogLogin(final String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(UpadateCaseActivity.this);
        View view = View.inflate(UpadateCaseActivity.this, R.layout.dialog_service_choice, null);
        final RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recycler_view.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        List<Post.ServiceBean> list = GlobalData.getServices();
        ServiceChoiceAdapter serviceChoiceAdapter = new ServiceChoiceAdapter(R.layout.servive_choice_item, list);
        serviceChoiceAdapter.setPositon(mPosition);//初始化选中状态
        recycler_view.setAdapter(serviceChoiceAdapter);

        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.show();
        serviceChoiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                View viewByPosition = adapter.getViewByPosition(recycler_view, position, R.id.iv);
                viewByPosition.setVisibility(View.VISIBLE);
                Post.ServiceBean o = (Post.ServiceBean)adapter.getData().get(position);
                String serviceName = o.getName();
                service_ed.setText(serviceName);
                dialog.dismiss();
            }
        });


    }
    //使用弹窗日历控件,实现开工日期的选择
    public void chooseDate(View view) {
        showDialogDate("");
    }
    private void showDialogDate(final String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(UpadateCaseActivity.this);
        View view = View.inflate(UpadateCaseActivity.this, R.layout.dialog_date_choice, null);
        final MaterialCalendarView CalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.show();
        CalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();
                create_ed.setText(year+"年"+month+"月"+day+"日");
                Log.e("",year+"年"+month+"月"+day+"日");
            }
        });
    }
    //改变状态
    public void stateChange(View view){

    }
    private void showDialogState(final String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(UpadateCaseActivity.this);
        View view = View.inflate(UpadateCaseActivity.this, R.layout.dialog_service_choice, null);
        final RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recycler_view.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        List<Post.ServiceBean> list = new ArrayList<>();
        Post.ServiceBean b01 = new Post.ServiceBean("施工中",0,"");
        Post.ServiceBean b02 = new Post.ServiceBean("已完成",1,"");
        list.add(b01);
        list.add(b02);
        ServiceChoiceAdapter serviceChoiceAdapter = new ServiceChoiceAdapter(R.layout.servive_choice_item, list);
//        serviceChoiceAdapter.setPositon(mPosition);//初始化选中状态
        recycler_view.setAdapter(serviceChoiceAdapter);

        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.show();
        serviceChoiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                View viewByPosition = adapter.getViewByPosition(recycler_view, position, R.id.iv);
                viewByPosition.setVisibility(View.VISIBLE);
                Post.ServiceBean o = (Post.ServiceBean)adapter.getData().get(position);
                String serviceName = o.getName();
                service_ed.setText(serviceName);
                dialog.dismiss();
            }
        });


    }
}

