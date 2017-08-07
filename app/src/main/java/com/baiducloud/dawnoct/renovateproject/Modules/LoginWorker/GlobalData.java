package com.baiducloud.dawnoct.renovateproject.Modules.LoginWorker;

import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;

import java.util.List;

/**
 * Created by DawnOct on 2017/8/7.
 */

public class GlobalData {
    public static List<Post.ServiceBean> services;
    public static List<Post.ServiceBean> getServices(){
        return services;
    }
    public static void updateServices(List<Post.ServiceBean> newServices){
        services = newServices;
    }
}
