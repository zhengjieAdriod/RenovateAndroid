package com.baiducloud.dawnoct.renovateproject.Modules.casesList;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class CasesPresenter {
    CasesActivity mView;

    public CasesPresenter(CasesActivity mView) {
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
            public void onNext(RespondedInfo res) {
                Logger.e("" + "");
                List<Post> posts = res.getData();
                mView.loadDataFirst(posts);
            }
        });
    }
}
