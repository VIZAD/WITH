package com.example.vizax.with.ui.login.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Brazen on 16/9/21.
 */
public class UserBean implements Serializable {
    /**
     * phone : 18318744486
     * sex : 0
     * unReadedNumber : 8
     * token : dsdsadsadasa
     * nickName : 安道尔
     * userUrl : http://img4.hao123.com/data/1_901e41dcffca28d765bc95ef81ed1913_0
     * userId : 1
     * studentID : 201324133111
     * class : 13软件工程一班
     * realName : 杰哥
     * qq : 270949894
     */

    private DataBean data;
    /**
     * data : {"phone":"18318744486","sex":0,"unReadedNumber":8,"token":"dsdsadsadasa","nickName":"安道尔","userUrl":"http://img4.hao123.com/data/1_901e41dcffca28d765bc95ef81ed1913_0","userId":1,"studentID":"201324133111","class":"13软件工程一班","realName":"杰哥","qq":"270949894"}
     * code : 200
     * msg : 信息
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "phone='" + phone + '\'' +
                    ", sex=" + sex +
                    ", unReadedNumber=" + unReadedNumber +
                    ", token='" + token + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userUrl='" + userUrl + '\'' +
                    ", userId=" + userId +
                    ", studentID='" + studentID + '\'' +
                    ", classX='" + classX + '\'' +
                    ", realName='" + realName + '\'' +
                    ", qq='" + qq + '\'' +
                    '}';
        }

        private String phone;
        private int sex;
        private int unReadedNumber;
        private String token;
        private String nickName;
        private String userUrl;
        private int userId;
        private String studentID;
        @SerializedName("class")
        private String classX;
        private String realName;
        private String qq;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getUnReadedNumber() {
            return unReadedNumber;
        }

        public void setUnReadedNumber(int unReadedNumber) {
            this.unReadedNumber = unReadedNumber;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserUrl() {
            return userUrl;
        }

        public void setUserUrl(String userUrl) {
            this.userUrl = userUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getStudentID() {
            return studentID;
        }

        public void setStudentID(String studentID) {
            this.studentID = studentID;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }
    }
}
