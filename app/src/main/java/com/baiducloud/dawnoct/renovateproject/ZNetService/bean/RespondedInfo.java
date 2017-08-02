package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class RespondedInfo {
    private String code;
    private List<Post> data;
    private Post post;
    private List<Post.WorkerBean> workers;
    private Post.WorkerBean worker;
    private List<PhotoesInfo.Photo> photos;
    private List<Comment> comments;
    private List<Owner> owners;
    private Comment comment;
    private String msg;
    private int comment_size;

    public Post.WorkerBean getWorker() {
        return worker;
    }

    public void setWorker(Post.WorkerBean worker) {
        this.worker = worker;
    }


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

    public List<Post.WorkerBean> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Post.WorkerBean> worker) {
        this.workers = worker;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public int getComment_size() {
        return comment_size;
    }

    public void setComment_size(int comment_size) {
        this.comment_size = comment_size;
    }
}
