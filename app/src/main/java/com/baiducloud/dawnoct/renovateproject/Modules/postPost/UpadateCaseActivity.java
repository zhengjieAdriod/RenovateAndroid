package com.baiducloud.dawnoct.renovateproject.Modules.postPost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.Wedgits.MyGridView;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.PhotoAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
    Button start_in_post;
    @BindView(R.id.protection_post)
    Button protection_post;
    @BindView(R.id.WorkSite__post)
    Button WorkSite__post;
    @BindView(R.id.finish__post)
    Button finish__post;
    UpadateCasePresenter processPresenter;
    private List<Imagepxh> Imagepxhlist01;
    private List<Imagepxh> Imagepxhlist02;
    private List<Imagepxh> Imagepxhlist03;
    private List<Imagepxh> Imagepxhlist04;
    private List<Imagepxh> Imagepxhlist05;
    PhotoAdapter myAdapter, myAdapter02, myAdapter03, myAdapter04, myAdapterHead;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    Post.WorkerBean worker;
    Post mPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_process_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        initToolBar(mToolbar, true, "查看并编辑项目");
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
                break;
            case "protect":
                Imagepxhlist02 = myAdapter02.getList();
                postImages(mPost, Imagepxhlist02, "protect_imag");
                break;
            case "work_site":
                Imagepxhlist03 = myAdapter03.getList();
                postImages(mPost, Imagepxhlist03, "work_site");
                break;
            case "finish":
                Imagepxhlist04 = myAdapter04.getList();
                postImages(mPost, Imagepxhlist04, "finish");
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
        mPost.setCreated_time(create);
        mPost.setDistrict(district);
        mPost.setVillage(village);
        mPost.setFact(fact);
        mPost.setPredict(predict);
        mPost.setService(new Post.ServiceBean(service, 20, "test"));
        mPost.setState(state);
        if (TextUtils.isEmpty(village) || TextUtils.isEmpty(district) || TextUtils.isEmpty(service)
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
        }
        if (des.contains("protect_imag")) {
            myAdapter02.setNetPhotos(photos);
        }
        if (des.contains("work_site")) {
            myAdapter03.setNetPhotos(photos);
        }
        if (des.contains("finish")) {
            myAdapter04.setNetPhotos(photos);
        }
    }
    //todo 使用弹窗,实现服务项目的选择
    public void chooseService(View view) {

    }
    //todo 使用弹窗日历控件,实现开工日期的选择
    public void chooseDate(View view) {

    }
    //    public void postInfoAll(View view) {
//        Editable text = editText.getText();
//        mVillage = text.toString().trim();
//        //准备图片数据
//        Imagepxhlist01 = myAdapter.getList();
//        Imagepxhlist02 = myAdapter02.getList();
//        Imagepxhlist03 = myAdapter03.getList();
//        Imagepxhlist04 = myAdapter04.getList();
//        Map<String, RequestBody> map = new HashMap<>();
//        File file = null;
//        for (int i = 0; i < Imagepxhlist01.size(); i++) {
//            Imagepxh imagepxh = Imagepxhlist01.get(i);
//            file = new File(imagepxh.getPath());
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
////                    map.put("post_imag", requestBody);
//            //todo  千万要加上filename=""
//            map.put("start_imag" + i + "\"; filename=\"" + file.getName(), requestBody);
////                    map.put("post_imag\"; filename=" + file.getName(), requestBody);
////                    map.put("file" + i + "\";filename=\"" + file.getName(), requestBody);
//        }
//        for (int i = 0; i < Imagepxhlist02.size(); i++) {
//            Imagepxh imagepxh = Imagepxhlist02.get(i);
//            file = new File(imagepxh.getPath());
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//            map.put("protect_imag" + i + "\"; filename=\"" + file.getName(), requestBody);
//        }
//        for (int i = 0; i < Imagepxhlist03.size(); i++) {
//            Imagepxh imagepxh = Imagepxhlist03.get(i);
//            file = new File(imagepxh.getPath());
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//            map.put("work_site" + i + "\"; filename=\"" + file.getName(), requestBody);
//        }
//        for (int i = 0; i < Imagepxhlist04.size(); i++) {
//            Imagepxh imagepxh = Imagepxhlist04.get(i);
//            file = new File(imagepxh.getPath());
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//            map.put("finish" + i + "\"; filename=\"" + file.getName(), requestBody);
//        }
////                RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
////                MultipartBody.Part photo = MultipartBody.Part.createFormData("post_imag", "111.png", photoRequestBody);
//        //提交上传数据
//        Post post = new Post();
//        post.setVillage(mVillage);
//        post.setCreated_time("");
//        post.setDistrict("河北-邯郸");
//        processPresenter.updatePostData(post, map);//todo 访问网路
//    }
}

