package com.baiducloud.dawnoct.renovateproject;

import android.app.Application;
import android.content.Context;

import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.Zutils.ToastUtils;


/**
 * Created by Steven Tang on 2017/4/12.
 */

public class AndroidApplication extends Application {
    private static AndroidApplication appContext;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        sContext = getApplicationContext();
        RetrofitService.init();//初始化数据接口
        ToastUtils.init(sContext);
    }

    public static Context getContext() {
        return sContext;
    }
}
