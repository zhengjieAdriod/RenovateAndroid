package com.baiducloud.dawnoct.renovateproject.EventPost;

/**
 * Created by DawnOct on 2017/7/26.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
