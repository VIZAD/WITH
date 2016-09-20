package com.example.vizax.with.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/16.
 */

public class Test {


    /**
     * data : [{"originatorHeadUrl":"image/head.png","menbersUrl":"image/head1.png,image/head2.png","invitationTime":"2016-9-8 19:33","publishTime":"2016-9-8 19:33","originatorNickname":"阿道克","iconUrl":"icon/icon1.png","invitaionId":1,"currentNumber":2,"sexRequire":"只要女生","content":"我们将迎来本学期一场狼人杀比赛","title":"狼人杀","totalNumber":16,"place":"中巴","members":[{"sex":"男","phone":1562261234,"userId":"1","realName":"潘大爷","headUrl":"image/head.png"}],"isJoin":true}]
     * code : 200
     * msg : 信息
     */

    private int code;
    private String msg;
    /**
     * originatorHeadUrl : image/head.png
     * menbersUrl : image/head1.png,image/head2.png
     * invitationTime : 2016-9-8 19:33
     * publishTime : 2016-9-8 19:33
     * originatorNickname : 阿道克
     * iconUrl : icon/icon1.png
     * invitaionId : 1
     * currentNumber : 2
     * sexRequire : 只要女生
     * content : 我们将迎来本学期一场狼人杀比赛
     * title : 狼人杀
     * totalNumber : 16
     * place : 中巴
     * members : [{"sex":"男","phone":1562261234,"userId":"1","realName":"潘大爷","headUrl":"image/head.png"}]
     * isJoin : true
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
        private String originatorHeadUrl;
        private String menbersUrl;
        private String invitationTime;
        private String publishTime;
        private String originatorNickname;
        private String iconUrl;
        private int invitaionId;
        private int currentNumber;
        private String sexRequire;
        private String content;
        private String title;
        private int totalNumber;
        private String place;
        private boolean isJoin;
        /**
         * sex : 男
         * phone : 1562261234
         * userId : 1
         * realName : 潘大爷
         * headUrl : image/head.png
         */

        private List<MembersBean> members;

        public String getOriginatorHeadUrl() {
            return originatorHeadUrl;
        }

        public void setOriginatorHeadUrl(String originatorHeadUrl) {
            this.originatorHeadUrl = originatorHeadUrl;
        }

        public String getMenbersUrl() {
            return menbersUrl;
        }

        public void setMenbersUrl(String menbersUrl) {
            this.menbersUrl = menbersUrl;
        }

        public String getInvitationTime() {
            return invitationTime;
        }

        public void setInvitationTime(String invitationTime) {
            this.invitationTime = invitationTime;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getOriginatorNickname() {
            return originatorNickname;
        }

        public void setOriginatorNickname(String originatorNickname) {
            this.originatorNickname = originatorNickname;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getInvitaionId() {
            return invitaionId;
        }

        public void setInvitaionId(int invitaionId) {
            this.invitaionId = invitaionId;
        }

        public int getCurrentNumber() {
            return currentNumber;
        }

        public void setCurrentNumber(int currentNumber) {
            this.currentNumber = currentNumber;
        }

        public String getSexRequire() {
            return sexRequire;
        }

        public void setSexRequire(String sexRequire) {
            this.sexRequire = sexRequire;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public boolean isIsJoin() {
            return isJoin;
        }

        public void setIsJoin(boolean isJoin) {
            this.isJoin = isJoin;
        }

        public List<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(List<MembersBean> members) {
            this.members = members;
        }

        public static class MembersBean {
            private String sex;
            private int phone;
            private String userId;
            private String realName;
            private String headUrl;

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getPhone() {
                return phone;
            }

            public void setPhone(int phone) {
                this.phone = phone;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }
        }
    }
}
