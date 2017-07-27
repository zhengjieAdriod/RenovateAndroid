package com.baiducloud.dawnoct.renovateproject.Modules.detailCase;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
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


    public void getPhotosInPost(Post post) {
        Observable<PhotoesInfo> postObservable = RetrofitService.getPhotosByPostId(post.getPk());
        postObservable.subscribe(new Subscriber<PhotoesInfo>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");
            }

            @Override
            public void onNext(PhotoesInfo info) {
                if ("200".endsWith(info.getCode())) {
                    mView.showDetailPhotos(info);
                }
                Log.e("zj", "");

            }
        });
    }
}
