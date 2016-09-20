package com.example.vizax.with.bean;

/**
 * Created by prjon 2016/9/14.
 */

public class User {
    private Integer userId;

    private String userNickname;

    private String userPassword;

    private String userStudentnumber;

    private String userRealname;

    private String userSex;

    private String userMobilephone;

    private String userQq;

    private String userToken;

    private Boolean userIsDelete;

    private String userHeadurl;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserStudentnumber() {
        return userStudentnumber;
    }

    public void setUserStudentnumber(String userStudentnumber) {
        this.userStudentnumber = userStudentnumber == null ? null : userStudentnumber.trim();
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname == null ? null : userRealname.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public String getUserMobilephone() {
        return userMobilephone;
    }

    public void setUserMobilephone(String userMobilephone) {
        this.userMobilephone = userMobilephone == null ? null : userMobilephone.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken == null ? null : userToken.trim();
    }

    public Boolean getUserIsDelete() {
        return userIsDelete;
    }

    public void setUserIsDelete(Boolean userIsDelete) {
        this.userIsDelete = userIsDelete;
    }

    public String getUserHeadurl() {
        return userHeadurl;
    }

    public void setUserHeadurl(String userHeadurl) {
        this.userHeadurl = userHeadurl == null ? null : userHeadurl.trim();
    }
}