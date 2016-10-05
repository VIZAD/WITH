package com.example.vizax.with.bean;

import java.util.List;

/**
 * Created by Xuhai on 2016/9/23.
 */

public class MyMessageBean {
    public MyMessageBean(List<DataBean> data) {
        this.data = data;
    }

    /**
     * data : [{"readed":true,"invationId":2,"invitationTotalNumber":10,"invitationTime":"2016-9-12 16:40","sendTime":"2016-9-10 22:30","invitationCurrNumber":8,"messageType":1,"content":"取消活动","applyUserId":2,"invitationTitle":"接师妹","name":"吴一鸣","headUrl":"headimg/head.png","messageId":1,"invitationPlace":"青年广场"}]
     * code : 200
     * msg : http://www.bejson.com
     */


    private String code;
    private String msg;
    /**
     * readed : true
     * invationId : 2
     * invitationTotalNumber : 10
     * invitationTime : 2016-9-12 16:40
     * sendTime : 2016-9-10 22:30
     * invitationCurrNumber : 8
     * messageType : 1
     * content : 取消活动
     * applyUserId : 2
     * invitationTitle : 接师妹
     * name : 吴一鸣
     * headUrl : headimg/head.png
     * messageId : 1
     * invitationPlace : 青年广场
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "readed=" + readed +
                    ", invationId=" + invationId +
                    ", invitationTotalNumber=" + invitationTotalNumber +
                    ", invitationTime='" + invitationTime + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    ", invitationCurrNumber=" + invitationCurrNumber +
                    ", messageType=" + messageType +
                    ", content='" + content + '\'' +
                    ", applyUserId=" + applyUserId +
                    ", invitationTitle='" + invitationTitle + '\'' +
                    ", name='" + name + '\'' +
                    ", headUrl='" + headUrl + '\'' +
                    ", messageId=" + messageId +
                    ", invitationPlace='" + invitationPlace + '\'' +
                    '}';
        }

        private boolean readed;
        private int invationId;
        private int invitationTotalNumber;
        private String invitationTime;
        private String sendTime;
        private int invitationCurrNumber;
        private int messageType;
        private String content;
        private int applyUserId;
        private String invitationTitle;
        private String name;
        private String headUrl;
        private int messageId;
        private String invitationPlace;

        public boolean isReaded() {
            return readed;
        }

        public void setReaded(boolean readed) {
            this.readed = readed;
        }

        public int getInvationId() {
            return invationId;
        }

        public void setInvationId(int invationId) {
            this.invationId = invationId;
        }

        public int getInvitationTotalNumber() {
            return invitationTotalNumber;
        }

        public void setInvitationTotalNumber(int invitationTotalNumber) {
            this.invitationTotalNumber = invitationTotalNumber;
        }

        public String getInvitationTime() {
            return invitationTime;
        }

        public void setInvitationTime(String invitationTime) {
            this.invitationTime = invitationTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public int getInvitationCurrNumber() {
            return invitationCurrNumber;
        }

        public void setInvitationCurrNumber(int invitationCurrNumber) {
            this.invitationCurrNumber = invitationCurrNumber;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(int applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getInvitationTitle() {
            return invitationTitle;
        }

        public void setInvitationTitle(String invitationTitle) {
            this.invitationTitle = invitationTitle;
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

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getInvitationPlace() {
            return invitationPlace;
        }

        public void setInvitationPlace(String invitationPlace) {
            this.invitationPlace = invitationPlace;
        }
    }

    @Override
    public String toString() {
        return "MyMessageBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
