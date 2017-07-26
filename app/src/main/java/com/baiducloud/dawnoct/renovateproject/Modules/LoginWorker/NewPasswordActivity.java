package com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.Zutils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/26.
 */

public class NewPasswordActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwd02)
    EditText et_pwd02;

    LoginWorkerPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_worker_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        presenter = new LoginWorkerPresenter(this);
    }
    //修改密码成功后的回调
    public void netSecces(Post.WorkerBean workerBean) {
        Intent intent = new Intent(NewPasswordActivity.this, LoginWorkerActivity.class);
        intent.putExtra("worker",workerBean);
        startActivity(intent);
    }
    //确定按钮的点击
    public void confirm(View view) {
        String tele = et_name.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        String pwd02 = et_pwd02.getText().toString().trim();
        if(TextUtils.isEmpty(tele)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd02)){
            ToastUtils.showToast("用户名或者密码不能为空");
            return;
        }
        if(!pwd.equals(pwd02)){
            ToastUtils.showToast("两次输入的密码不一致");
            return;
        }
        presenter.changePassword(tele,pwd02);
    }
}
