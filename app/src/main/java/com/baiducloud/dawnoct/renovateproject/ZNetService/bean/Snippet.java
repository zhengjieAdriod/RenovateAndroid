package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

import java.io.Serializable;

/**
 * Created by DawnOct on 2017/7/11.
 */

public class Snippet implements Serializable{

    /**
     * id : 28
     * title : oloo
     * code : pp
     * linenos : false
     */

    private int id;
    private String title;
    private String code;
    private boolean linenos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isLinenos() {
        return linenos;
    }

    public void setLinenos(boolean linenos) {
        this.linenos = linenos;
    }
}
