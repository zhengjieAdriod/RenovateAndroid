package com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import java.util.Objects;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by DawnOct on 2017/7/26.
 */

public class LoginWorkerPresenter {
    BaseActivity mView;

    public LoginWorkerPresenter(BaseActivity loginWorkerActivity) {
        this.mView = loginWorkerActivity;
    }

    public void login(String tele, String password) {
        Observable<RespondedInfo> workerLoginObservable = RetrofitService.workerLogin(tele, password);
        workerLoginObservable.subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");
            }

            @Override
            public void onNext(RespondedInfo info) {
                Log.e("zj", "");
                if("200".equals(info.getCode())){
                    mView.netSecces(info.getWorker());
                }
            }
        });
    }

    public void changePassword(String tele,String pass) {
        Observable<RespondedInfo> workerLoginObservable = RetrofitService.newPasswordWorker(tele, pass);
        workerLoginObservable.subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");
            }

            @Override
            public void onNext(RespondedInfo info) {
                Log.e("zj", "");
                if("200".equals(info.getCode())){
                    mView.netSecces(info.getWorker());
                }
            }
        });
    }
}
