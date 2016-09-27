package com.example.vizax.with.ui.login.bean;

/**
 * Created by Brazen on 16/9/21.
 */
public class VerifyBean {
    /**
     * phone : 15622625081
     */

    private DataBean data;
    /**
     * data : {"phone":15622625081}
     * code : 200
     * msg : 用户未注册
     */

    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public  class DataBean {
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
