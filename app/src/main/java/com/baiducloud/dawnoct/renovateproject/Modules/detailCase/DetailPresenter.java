package com.baiducloud.dawnoct.renovateproject.Modules.detailCase;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class DetailPresenter {
    DetailActivity mView;

    public DetailPresenter(DetailActivity mView) {
        this.mView = mView;
    }


    public void getData() {
        Observable<List<Post>> cases = RetrofitService.getCases();
        cases.compose(mView.<List<Post>>bindToLife())//解决内存泄漏的框架
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).subscribe(new Subscriber<List<Post>>() {
            @Override
            public void onCompleted() {
                Logger.e("" + "");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("" + "wunlun1808");
            }

            @Override
            public void onNext(List<Post> posts) {
                Logger.e("" + "");
                mView.loadDataFirst(posts);
            }
        });
    }
}
