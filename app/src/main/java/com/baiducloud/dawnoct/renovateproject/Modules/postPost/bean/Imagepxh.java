package com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean;

import android.net.Uri;

public class Imagepxh {
    public Uri image_uri;
    public String path;
    public boolean isLocal;
    public String des;
    public String pk;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }


    public Imagepxh(String pk, String path, String des) {
        this.pk = pk;
        this.path = path;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public Imagepxh(Uri image_uri, String path, boolean isLocal, String des) {
        this.image_uri = image_uri;
        this.path = path;
        this.isLocal = isLocal;
        this.des = des;
    }

    public Imagepxh(Uri image_uri, String path, boolean isLocal) {
        this.image_uri = image_uri;
        this.path = path;
        this.isLocal = isLocal;
    }

    public Imagepxh(Uri uri, String path) {
        super();
        this.image_uri = uri;
        this.path = path;
    }


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Uri getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
    }

    public int hashCode() {
        if (image_uri != null)
            return image_uri.hashCode() + path.hashCode();
        return 0;
    }

    public boolean equals(Object oo) {
        return image_uri != null ? image_uri.hashCode() == ((Imagepxh) oo).image_uri.hashCode() || path.hashCode() == ((Imagepxh) oo).path.hashCode() : false;
    }


}
