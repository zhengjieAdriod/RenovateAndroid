package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class ResponceInfo {
    private String code;
    private List<Post> data;
    private List<PhotoesInfo.Photo> photos;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PhotoesInfo.Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoesInfo.Photo> photos) {
        this.photos = photos;
    }

}
