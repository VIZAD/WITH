package com.example.vizax.with.bean;

import java.io.Serializable;

/**
 * Created by prj on 2016/9/16.
 */

public class BaseEmptyBean implements Serializable{

    private String msg;
    private String code;

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
}
