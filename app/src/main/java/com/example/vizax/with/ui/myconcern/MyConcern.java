package com.example.vizax.with.ui.myconcern;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyConcern {

    @Override
    public String toString() {
        return "MyConcern{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

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

    public static class DataBean {
        private int concernedUserId;
        private boolean concerned;
        private String name;
        private String headUrl;

        public DataBean(int concernedUserId, boolean concerned, String name, String headUrl) {
            this.concernedUserId = concernedUserId;
            this.concerned = concerned;
            this.name = name;
            this.headUrl = headUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "concernedUserId=" + concernedUserId +
                    ", concerned=" + concerned +
                    ", name='" + name + '\'' +
                    ", headUrl='" + headUrl + '\'' +
                    '}';
        }

        public boolean isConcerned() {
            return concerned;
        }

        public void setConcerned(boolean concerned) {
            this.concerned = concerned;
        }

        public int getConcernedUserId() {
            return concernedUserId;
        }

        public void setConcernedUserId(int concernedUserId) {
            this.concernedUserId = concernedUserId;
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

