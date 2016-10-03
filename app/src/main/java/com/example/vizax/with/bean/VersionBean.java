package com.example.vizax.with.bean;

public class VersionBean {


    /**
     * code : 200
     * msg : 成功
     * data : {"versionId":3,"versionCode":5,"versionName":4,"versionContent":"hahaha测试;做下测试而已","versionForceUpdate":0,"versionUrl":"http://117.169.71.165/imtt.dd.qq.com/16891/580B22B6281E704CAB3079BC8210A88D.apk?mkey=57e9226712336c2a&f=8a5d&c=0&fsname=com.supertreasure_1.2_3.apk&csr=4d5s&p=.apk"}
     */

    private int code;
    private String msg;
    /**
     * versionId : 3
     * versionCode : 5
     * versionName : 4.0
     * versionContent : hahaha测试;做下测试而已
     * versionForceUpdate : 0
     * versionUrl : http://117.169.71.165/imtt.dd.qq.com/16891/580B22B6281E704CAB3079BC8210A88D.apk?mkey=57e9226712336c2a&f=8a5d&c=0&fsname=com.supertreasure_1.2_3.apk&csr=4d5s&p=.apk
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int versionId;
        private int versionCode;
        private double versionName;
        private String versionContent;
        private int versionForceUpdate;
        private String versionUrl;

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public double getVersionName() {
            return versionName;
        }

        public void setVersionName(double versionName) {
            this.versionName = versionName;
        }

        public String getVersionContent() {
            return versionContent;
        }

        public void setVersionContent(String versionContent) {
            this.versionContent = versionContent;
        }

        public int getVersionForceUpdate() {
            return versionForceUpdate;
        }

        public void setVersionForceUpdate(int versionForceUpdate) {
            this.versionForceUpdate = versionForceUpdate;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }
    }
}