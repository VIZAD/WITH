package com.example.vizax.with.ui.myconcern;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyConcern {

    /**
     * data : [{"concernedUserId":2,"isConcerned":true,"name":"吴一鸣","headUrl":"headimg/head.png"}]
     * code : 200
     * msg : xxx
     */

    private int code;
    private String msg;
    /**
     * concernedUserId : 2
     * isConcerned : true
     * name : 吴一鸣
     * headUrl : headimg/head.png
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyConcern{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private int concernedUserId;
        private boolean isConcerned;
        private String name;
        private String headUrl;

        public DataBean(int concernedUserId, boolean isConcerned, String name, String headUrl) {
            this.concernedUserId = concernedUserId;
            this.isConcerned = isConcerned;
            this.name = name;
            this.headUrl = headUrl;
        }

        public int getConcernedUserId() {
            return concernedUserId;
        }

        public void setConcernedUserId(int concernedUserId) {
            this.concernedUserId = concernedUserId;
        }

        public boolean isIsConcerned() {
            return isConcerned;
        }

        public void setIsConcerned(boolean isConcerned) {
            this.isConcerned = isConcerned;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }
    }
}

