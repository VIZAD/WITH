package com.example.vizax.with.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by prj on 2016/9/14.
 */

public class InvitationBaseBean {

    private String msg;
    private String code;

    public List<InvitationBean> getData() {
        return data;
    }

    public void setData(List<InvitationBean> data) {
        this.data = data;
    }

    private List<InvitationBean> data;

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
