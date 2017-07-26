package com.baiducloud.dawnoct.pictruetest;

import android.net.Uri;

public class Imagepxh {
    public Uri image_uri;
    public String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Imagepxh(Uri uri, String path) {
        super();
        this.image_uri = uri;
        this.path = path;
    }

    public Uri getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
    }

    public int hashCode() {
        return image_uri.hashCode() + path.hashCode();
    }

    public boolean equals(Object oo) {
        return (image_uri.hashCode() == ((Imagepxh) oo).image_uri.hashCode() || path.hashCode() == ((Imagepxh) oo).path.hashCode());
    }


}
