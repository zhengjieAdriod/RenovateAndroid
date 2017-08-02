package com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baiducloud.dawnoct.renovateproject.Modules.postList.ListPostActivity;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.Zutils.ToastUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/26.
 */

public class LoginWorkerActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;


    LoginWorkerPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_worker_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        presenter = new LoginWorkerPresenter(this);
        //从修改密码页面传过来
        Post.WorkerBean worker = (Post.WorkerBean) getIntent().getSerializableExtra("worker");
        if(worker!=null){
            et_name.setText(worker.getTele());
        }
    }

    public void login(View view) {
        String tele = et_name.getText().toString().trim();
        String password = et_pwd.getText().toString().trim();
        if(TextUtils.isEmpty(tele)||TextUtils.isEmpty(password)){
            ToastUtils.showToast("手机号或者密码不能为空");
            return;
        }
        presenter.login(tele,password);
    }
    //登录成功后的回调
    public void netSecces(Post.WorkerBean workerBean) {
        Intent intent = new Intent(LoginWorkerActivity.this, ListPostActivity.class);
        intent.putExtra("worker",workerBean);
        SharedPreferences mySharedPreferences = getSharedPreferences("workerData",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("workerTelephone", workerBean.getTele());
        editor.putString("workerId", workerBean.getPk());
        //提交当前数据
        editor.apply();
        startActivity(intent);
    }
    //忘记密码
    public void fogetPassword(View view) {
        //跳转
        Intent intent = new Intent(LoginWorkerActivity.this,NewPasswordActivity.class);
        startActivity(intent);
    }
}
