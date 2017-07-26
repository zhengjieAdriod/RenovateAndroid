package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;


/**
 * Created by Steven Tang on 2017/4/12.
 */

public class MsgInfo {
    /**
     * actionType : 302
     * parameters : {"ut":1472090431909}
     * token : e87eac40-4a62-48c1-8c52-5db1270345aa
     * ver : 1.0
     * os : ios 9.35
     */

    private int actionType;
    private String parameters;
    private String token;
    private String ver;
    private String os;


    public MsgInfo(String parameters) {
        this.actionType = 302;
        this.parameters = parameters;
        this.token = "e87eac40-4a62-48c1-8c52-5db1270345aa";
        this.ver = "1.0";
        this.os = "Android";
    }
    public MsgInfo(int actionType,String parameters) {
        this.actionType = actionType;
        this.parameters = parameters;

    }
    public MsgInfo(int actionType,String parameters,String token) {
        this.actionType = actionType;
        this.parameters = parameters;
        this.token = token;
        this.ver = "1.0";
        this.os = "Android";
    }
    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}

