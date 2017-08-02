package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

/**
 * Created by DawnOct on 2017/7/31.
 */

public class Comment {
    private String pk;
    private String name;
    private String telephone;
    private String text;
    private String created_time;
    private String postId;
    private String ownerId;
    private String call_back;

    public Comment() {
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public String getCall_back() {
        return call_back;
    }

    public void setCall_back(String call_back) {
        this.call_back = call_back;
    }
}
