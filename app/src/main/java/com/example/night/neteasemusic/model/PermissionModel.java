package com.example.night.neteasemusic.model;

/**
 * Created by Night on 2017/10/5.
 * Desc:
 */

public class PermissionModel {

    private String name;
    //权限名称  
    private String permisson;
    //申请理由
    private String reason;
    //请求码
    private int requestCode;

    public PermissionModel(String name, String permisson, String reason, int requestCode) {
        this.name = name;
        this.permisson = permisson;
        this.reason = reason;
        this.requestCode = requestCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermisson() {
        return permisson;
    }

    public void setPermisson(String permisson) {
        this.permisson = permisson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
