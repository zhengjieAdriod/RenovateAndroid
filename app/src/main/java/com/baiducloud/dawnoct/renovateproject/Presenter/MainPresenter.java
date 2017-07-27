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

    }
}
