package com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by DawnOct on 2017/7/26.
 */

public class LoginWorkerPresenter {
    BaseActivity mView;

    public LoginWorkerPresenter(BaseActivity loginWorkerActivity) {
        this.mView = loginWorkerActivity;
    }

    public void login(String tele, String password) {
        //除了登录业务,另外加上更新常量的业务. 两个业务完成后才进行跳转业务
        //merge 与 mergeDelayError的区别是: 只有一个错误,立马走onError()的回调,而mergeDelayError, 则是有几个对的就走几次onNext()
        Observable observable = Observable.mergeDelayError(getLogin(tele,password), getServices());
        observable.subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "完成跳转");
                mView.goActivity();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");

            }
// change me us you and me just do it and u
            @Override
            public void onNext(RespondedInfo info) {
                if ("200".equals(info.getCode())) {
                    if (info.getWorker() != null) {
                        mView.netSecces(info.getWorker());
                        Log.e("zj", "登录完成");
                    } else if (info.getServices() != null && info.getServices().size() > 0) {
                        GlobalData.updateServices(info.getServices());
                        Log.e("zj", "服务拉取完成");
                    }
                }
            }
        });
    }
    private Observable getLogin(String tele,String password) {
        return RetrofitService.workerLogin(tele, password);
    }
    private Observable getServices() {
        return RetrofitService.getServices();
    }

    public void changePassword(String tele, String pass) {
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
                if ("200".equals(info.getCode())) {
                    mView.netSecces(info.getWorker());
                }
            }
        });
    }
}
