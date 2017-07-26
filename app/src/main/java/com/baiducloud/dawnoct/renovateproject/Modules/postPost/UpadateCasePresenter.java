package com.baiducloud.dawnoct.renovateproject.Modules.postPost;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class UpadateCasePresenter {
    UpadateCaseActivity mView;

    public UpadateCasePresenter(UpadateCaseActivity mView) {
        this.mView = mView;
    }

    /*上传图片*/
    public void updatePostData(Post post, Map<String, RequestBody> map) {
        Observable<RespondedInfo> postObservable = RetrofitService.updatePostSnippetWith(post, map);
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
                if ("200".equals(info.getCode())) {
                    Post post1 = info.getPost();
                    EventBus.getDefault().post(post1);
                    List<PhotoesInfo.Photo> photos = info.getPhotos();
                    if (photos.size() > 0)
                        mView.changeNetPhotos(photos);
                }
            }
        });
    }

    /*获得图片组进行展示*/
    public void getData(Post post) {
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
                    mView.showView(info);
                }
                Log.e("zj", "");

            }
        });
    }

    public void deleteNetPhoto(String pk, String photoType) {
        Observable<String> postObservable = RetrofitService.deletePhotoOfPost(pk, photoType);
        postObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("zj", "");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zj", "");
            }

            @Override
            public void onNext(String info) {

                Log.e("zj", "");

            }
        });
    }
}
