package com.baiducloud.dawnoct.renovateproject.Modules.casesList;

import com.baiducloud.dawnoct.renovateproject.Wedgits.EmptyLayout;
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
    CasesListActivity mView;

    public CasesPresenter(CasesListActivity mView) {
        this.mView = mView;
    }


    public void getData(final int page, String district, String state) {
        Observable<RespondedInfo> cases = RetrofitService.getCasesTest(page, district, state);
        cases.compose(mView.<RespondedInfo>bindToLife())//解决内存泄漏的框架
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (page == 1) {
                            mView.showLoading();
                        }
                    }
                }).subscribe(new Subscriber<RespondedInfo>() {
            @Override
            public void onCompleted() {
                Logger.e("" + "");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("" + "wunlun1808");
                mView.hideLoading();
                mView.finishRefresh();
                if (page == 1) {
                    mView.showNetError(new EmptyLayout.OnRetryListener() {
                        @Override
                        public void onRetry() {
                            //点击后重新加载(回调刷新)
                            mView.getReFreshData();
                        }
                    });
                } else {//加载更多时出错
                    mView.errorLoadMore();
                }

            }

            @Override
            public void onNext(RespondedInfo res) {
                String msg = res.getMsg();
                mView.hideLoading();
                mView.finishRefresh();
                List<Post> posts = res.getData();
                mView.loadDataSuccess(posts);
            }
        });
    }
}
