package com.baiducloud.dawnoct.renovateproject.Presenter;

import com.baiducloud.dawnoct.renovateproject.Views.MainActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class MainPresenter {
    MainActivity mView;

    public MainPresenter(MainActivity mView) {
        this.mView = mView;
    }


    public void getData() {
        Observable<RespondedInfo> cases = RetrofitService.getCasesTest();
        cases.compose(mView.<RespondedInfo>bindToLife())//解决内存泄漏的框架
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Logger.e("" + "");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("" + "wunlun1808");
            }

            @Override
            public void onNext(RespondedInfo posts) {
                Logger.e("" + "");
            }
        });
    }
}
