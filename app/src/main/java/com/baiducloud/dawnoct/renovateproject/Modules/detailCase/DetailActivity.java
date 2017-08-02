package com.baiducloud.dawnoct.renovateproject.Modules.detailCase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SuperKotlin.pictureviewer.ImagePagerActivity;
import com.SuperKotlin.pictureviewer.PictureConfig;
import com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker.LoginWorkerActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.postList.ListPostActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.CommentAdapter;
import com.baiducloud.dawnoct.renovateproject.ZAdapter.DetailAdapter;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Comment;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Owner;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.Zutils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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

    @BindView(R.id.comment_rcy)
    RecyclerView comment_rcy;

    DetailAdapter mAdapter01, mAdapter02, mAdapter03, mAdapter04;
    CommentAdapter commentAdapter;
    List<PhotoesInfo.Photo> list01, list02, list03, list04;
    List<Comment> mComments;

    Post post;
    DetailPresenter presenter;

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
        presenter = new DetailPresenter(this);
        //todo 网络获得post下的图片组
        if (post != null) {
            presenter.getPhotosInPost(post);
            presenter.getCommentsInPost(post);
        }

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
//        scheme01.setText(post.getScheme().getMeasures() + "/" + post.getScheme().getDamage_des());
        scheme01.setText("施工方案");

        start_in_imags.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter01 = new DetailAdapter(R.layout.detail_item, list01);

        mAdapter02 = new DetailAdapter(R.layout.detail_item, list02);
        mAdapter03 = new DetailAdapter(R.layout.detail_item, list03);
        mAdapter04 = new DetailAdapter(R.layout.detail_item, list04);
        start_in_imags.setAdapter(mAdapter01);
        protection_imags.setLayoutManager(new GridLayoutManager(this, 3));
        protection_imags.setAdapter(mAdapter02);
        work_site_imags.setLayoutManager(new GridLayoutManager(this, 3));
        work_site_imags.setAdapter(mAdapter03);
        finish_imags.setLayoutManager(new GridLayoutManager(this, 3));
        finish_imags.setAdapter(mAdapter04);
        commentAdapter = new CommentAdapter(R.layout.commt_item, mComments);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Comment comment = (Comment) adapter.getItem(position);
                workerCallBack(comment,position);
                return false;
            }
        });
        comment_rcy.setAdapter(commentAdapter);
        comment_rcy.setLayoutManager(new LinearLayoutManager(this));
        bigPhoto(mAdapter01);
        bigPhoto(mAdapter02);
        bigPhoto(mAdapter03);
        bigPhoto(mAdapter04);
    }

    private void bigPhoto(final DetailAdapter detailAdapter) {
        detailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PhotoesInfo.Photo> data = detailAdapter.getData();
                List<String> urls = new ArrayList<String>();
                for (PhotoesInfo.Photo photo : data) {
                    urls.add(RetrofitService.RENOVATE_HOST_PHPTO + photo.getPath());
                }
//                urls.add("https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg");
                //使用方式
                PictureConfig config = new PictureConfig.Builder()
                        .setListData((ArrayList<String>) urls)    //图片数据List<String> list
                        .setPosition(position)    //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("pictureviewer")    //图片下载文件夹地址
                        .needDownload(true)    //是否支持图片下载
                        .setPlacrHolder(R.mipmap.ic_launcher_round)//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                        .build();
                ImagePagerActivity.startActivity(DetailActivity.this, config);
                Log.d("", "");
            }
        });
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    public void showDetailPhotos(PhotoesInfo info) {
        List<PhotoesInfo.Photo> startImages = info.getStartImages();
        List<PhotoesInfo.Photo> protectionImages = info.getProtectionImages();
        List<PhotoesInfo.Photo> workSiteImages = info.getWorkSiteImages();
        List<PhotoesInfo.Photo> finishImages = info.getFinishImages();
        if (startImages.size() > 0) {
            mAdapter01.setNewData(startImages);
        }
        if (protectionImages.size() > 0) {
            mAdapter02.setNewData(protectionImages);
        }
        if (workSiteImages.size() > 0) {
            mAdapter03.setNewData(workSiteImages);
        }
        if (finishImages.size() > 0) {
            mAdapter04.setNewData(finishImages);
        }
//        list01.add(post.getStart_in());
//        list02.add(post.getProtection());
//        list03.add(post.getWork_site());
//        list04.add(post.getFinish());
    }

    public void showComments(List<Comment> comments) {
        commentAdapter.setNewData(comments);
    }

    /*业主点击评论时,弹出输入框或者登录框*/
    public void addComment(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("ownerData",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String telephone = sharedPreferences.getString("telephone", "");
        String ownerId = sharedPreferences.getString("ownerId", "");
        if (TextUtils.isEmpty(telephone) || TextUtils.isEmpty(ownerId)) {
            //弹出登录框
            showDialogLogin("owner");
            return;
        }
        showDialog(telephone, ownerId,"owner",null,0);
    }
    /*管家点击回复评论时*/
    private void workerCallBack(Comment comment,int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("workerData",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String telephone = sharedPreferences.getString("workerTelephone", "");
        String workerId = sharedPreferences.getString("workerId", "");
        if( !TextUtils.isEmpty(workerId) && !workerId.equals(post.getWorker().getPk())){
            ToastUtils.showToast("该管家没有权限回复评论");
            return;
        }
        if (TextUtils.isEmpty(telephone) || TextUtils.isEmpty(workerId)) {
            //弹出登录框
            showDialogLogin("worker");
            return;
        }
        showDialog("", "","worker",comment,position);
    }

    public void addCommentList(Comment comment) {
        temContent = "";
        commentAdapter.addDataTop(comment);
        ToastUtils.showToast("评论成功");
    }

    /**
     * 显示自定义对话框（输入文字）
     */
    private void showDialog(final String telephone, final String ownerId, final String type, final Comment comment, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        View view = View.inflate(DetailActivity.this, R.layout.layout_text_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.dialog_edittext);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView ok = (TextView) view.findViewById(R.id.tv_ok);

        builder.setView(view);
        builder.setCancelable(true);
//        editText.setText(message);
        final AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(DetailActivity.this, "您没有任何输入!", Toast.LENGTH_SHORT).show();
                } else {
                    //调用提交接口
                    if("owner".equals(type)){
                        postComment(telephone, ownerId, content);
                    }
                    if("worker".equals(type)){
                        //回复接口
                        comment.setCall_back(content);
                        presenter.addCallBack(comment,position);
                    }
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temContent = "";
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示登录框
     */
    private void showDialogLogin(final String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        View view = View.inflate(DetailActivity.this, R.layout.owner_login_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.dialog_edittext);
        final EditText editText_pws = (EditText) view.findViewById(R.id.dialog_edittext_pws);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView ok = (TextView) view.findViewById(R.id.tv_ok);

        builder.setView(view);
        builder.setCancelable(true);
//        editText.setText(message);
        final AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tele = editText.getText().toString();
                String pws = editText_pws.getText().toString();
                if (TextUtils.isEmpty(tele) || TextUtils.isEmpty(pws)) {
                    Toast.makeText(DetailActivity.this, "手机号或者密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //调用登录接口
                    if("owner".equals(type)){
                        presenter.ownerLogin(tele, pws);
                    }
                    if("worker".equals(type)){
                        presenter.workerLogin(tele,pws);
                    }
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    String temContent;

    private void postComment(String telephone, String ownerId, String content) {
        Comment c = new Comment();
        c.setOwnerId(ownerId);
        c.setPostId(post.getPk());
        c.setTelephone(telephone);
        c.setText(content);
        temContent = content;
        presenter.addComment(c);//访问评论接口
    }
    //管家登录成功后的回调
    public void netSecces(Post.WorkerBean workerBean) {
        SharedPreferences mySharedPreferences = getSharedPreferences("workerData",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("workerTelephone", workerBean.getTele());
        editor.putString("workerId", workerBean.getPk());
        editor.apply();
        //回复接口
//        presenter.addCallBack(comment,position);
    }
    public boolean loginSeccess(Owner owner) {
        SharedPreferences mySharedPreferences = getSharedPreferences("ownerData",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("telephone", owner.getTelephone());
        editor.putString("ownerId", owner.getPk());
        //提交当前数据
        editor.apply();
        //弹出评论框
//        showDialog(owner.getTelephone(),owner.getPk());
        //调用提交接口
        postComment(owner.getTelephone(), owner.getPk(), temContent);
        return true;
    }

    public boolean loginFail() {
        Toast.makeText(DetailActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*评论失败后,评论者需要重新登录*/
    public void ownerLoginAgian(String type) {
        showDialogLogin(type);
    }
    /*回复评论成功*/
    public void addCallBackInComment(Comment comment1,int position) {
        commentAdapter.setData(position,comment1);
    }
}
