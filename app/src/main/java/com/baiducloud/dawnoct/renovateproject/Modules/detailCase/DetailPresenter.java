package com.baiducloud.dawnoct.renovateproject.Modules.detailCase;

import android.util.Log;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Comment;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Owner;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
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

public class DetailPresenter {
    DetailActivity mView;

    public DetailPresenter(DetailActivity mView) {
        this.mView = mView;
    }

    /*获得图片组*/
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

    /*获得评论*/
    public void getCommentsInPost(Post post) {
        Observable<RespondedInfo> postObservable = RetrofitService.getComments(post.getPk());
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
                if ("200".endsWith(info.getCode())) {
                    List<Comment> comments = info.getComments();
                    mView.showComments(comments);
                }
                Log.e("zj", "");

            }
        });
    }
    /*添加评论*/
    public void addComment( Comment comment) {
        Observable<RespondedInfo> postObservable = RetrofitService.addComment(comment);
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
                if ("200".endsWith(info.getCode())) {
                    Comment comment1 = info.getComment();
                    mView.addCommentList(comment1);
                }
                if("206".equals(info.getCode())){//当前owner帐号,不对应当前的post, 因此需要提醒评论者重新登录
                    mView.ownerLoginAgian("owner");

                }
                Log.e("zj", "");

            }
        });
    }
    /*回复评论*/
    public void addCallBack(final Comment comment, final int positon) {
        Observable<RespondedInfo> postObservable = RetrofitService.addCallBack(comment);
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
                if ("200".endsWith(info.getCode())) {
                    Comment comment1 = info.getComment();
                    mView.addCallBackInComment(comment1,positon);
                }
                if("206".equals(info.getCode())){//当前owner帐号,不对应当前的post, 因此需要提醒评论者重新登录
                    mView.ownerLoginAgian("owner");
                    Log.e("zj", "");
                }
            }
        });
    }
    /*业主登录*/
    public void ownerLogin(String telephone, String password) {
        Observable<RespondedInfo> postObservable = RetrofitService.ownerLogin(telephone,password);
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
                if ("200".endsWith(info.getCode())&&info.getOwners().size()>0) {
                    //登录成功
                    List<Owner> owners = info.getOwners();
                    Owner owner = owners.get(0);
                    mView.loginSeccess(owner);
                }else {
                    //登录失败
                    mView.loginFail();
                }
                Log.e("zj", "");

            }
        });
    }

    /*管家登录*/
    public void workerLogin(String tele, String password) {
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
}
