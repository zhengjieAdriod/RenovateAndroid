package com.baiducloud.dawnoct.renovateproject.Modules.postList;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class ListPostPresenter {
    ListPostActivity mView;

    public ListPostPresenter(ListPostActivity mView) {
        this.mView = mView;
    }


    public void getData(String workerId) {
        Observable<RespondedInfo> postObservable = RetrofitService.getCasesByWorker(workerId);
        postObservable.subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");
            }

            @Override
            public void onNext(RespondedInfo post) {
                if ("200".endsWith(post.getCode())) {
                    List<Post> posts = post.getData();
                    mView.setNetData(posts);
                }
                Log.e("zj", "");
            }
        });
    }
}
