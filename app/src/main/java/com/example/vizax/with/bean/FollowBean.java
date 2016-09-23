package com.example.vizax.with.bean;

/**
 * Created by Young on 2016/9/22.
 */
public class FollowBean {
    /**
     * isConcerned : true
     */

    private DataBean data;
    /**
     * data : {"isConcerned":true}
     * code : 200
     * msg : 删除邀约成功
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

    public static class DataBean {
        private boolean isConcerned;

        public boolean isIsConcerned() {
            return isConcerned;
        }

        public void setIsConcerned(boolean isConcerned) {
            this.isConcerned = isConcerned;
        }
    }
}
