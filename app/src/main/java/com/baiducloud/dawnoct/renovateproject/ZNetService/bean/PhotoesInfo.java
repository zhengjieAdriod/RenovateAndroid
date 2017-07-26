package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

import android.net.Uri;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DawnOct on 2017/7/24.
 */

public class PhotoesInfo {
    private String msg;
    private String code;
    private List<Photo> startImages;
    private List<Photo> protectionImages;
    private List<Photo> workSiteImages;
    private List<Photo> finishImages;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Photo> getStartImages() {
        return startImages;
    }

    public void setStartImages(List<Photo> startImages) {
        this.startImages = startImages;
    }

    public List<Photo> getProtectionImages() {
        return protectionImages;
    }

    public void setProtectionImages(List<Photo> protectionImages) {
        this.protectionImages = protectionImages;
    }

    public List<Photo> getWorkSiteImages() {
        return workSiteImages;
    }

    public void setWorkSiteImages(List<Photo> workSiteImages) {
        this.workSiteImages = workSiteImages;
    }

    public List<Photo> getFinishImages() {
        return finishImages;
    }

    public void setFinishImages(List<Photo> finishImages) {
        this.finishImages = finishImages;
    }


    public static class Photo extends Imagepxh {
//        private String des;
//        private String path;

        public Photo(String pk, String path, String des) {
            super(pk, path, des);
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }


    }
}
