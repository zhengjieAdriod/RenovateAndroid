package com.baiducloud.dawnoct.renovateproject.Modules.postPost;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class AddCasePresenter {
    AddNewCaseActivity mView;

    public AddCasePresenter(AddNewCaseActivity mView) {
        this.mView = mView;
    }


    public void postNewData(Post post, Map<String, RequestBody> map) {
        Observable<RespondedInfo> postObservable = RetrofitService.postSnippetWith(post, map);
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
            public void onNext(RespondedInfo info) {
                Log.e("zj", "");
                //跳转到列表页
                if ("200".equals(info.getCode())){
                    Post post1 = info.getData().get(0);
                    mView.addPostFinish(post1);
                }

            }
        });
    }
}
