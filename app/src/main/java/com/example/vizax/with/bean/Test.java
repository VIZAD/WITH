package com.example.vizax.with.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/16.
 */

public class Test {

    /**
     * data : [{"summary":"我是...xxx","concernedUserId":2,"isConcerned":true,"name":"吴一鸣","headUrl":"headimg/head.png"}]
     * code : BeJson
     * msg : http://www.bejson.com
     */

    private String code;
    private String msg;
    /**
     * summary : 我是...xxx
     * concernedUserId : 2
     * isConcerned : true
     * name : 吴一鸣
     * headUrl : headimg/head.png
     */

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        private String summary;
        private int concernedUserId;
        private boolean isConcerned;
        private String name;
        private String headUrl;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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
