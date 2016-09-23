package com.example.vizax.with.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Young on 2016/9/21.
 */
public class UserInforBean {
    /**
     * phone : 15622625081
     * sex : 0
     * isConcerned : true
     * studentId : 201324133121
     * name : 吴一鸣
     * headUrl : headimg/head.png
     * qq : 270949894
     */

    private DataBean data;
    /**
     * data : {"phone":15622625081,"sex":0,"isConcerned":true,"studentId":"201324133121","name":"吴一鸣","headUrl":"headimg/head.png","qq":"270949894"}
     * code : 200
     * msg : xxx
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

    public static class DataBean implements Parcelable{
        private long phone;
        private int sex;
        private boolean isConcerned;
        private String studentId;
        private String name;
        private String headUrl;
        private String qq;

        protected DataBean(Parcel in) {
            phone = in.readLong();
            sex = in.readInt();
            isConcerned = in.readByte() != 0;
            studentId = in.readString();
            name = in.readString();
            headUrl = in.readString();
            qq = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public boolean isIsConcerned() {
            return isConcerned;
        }

        public void setIsConcerned(boolean isConcerned) {
            this.isConcerned = isConcerned;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
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

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(String.valueOf(phone));
            dest.writeString(String.valueOf(sex));
            dest.writeString(String.valueOf(isConcerned));
            dest.writeString(studentId);
            dest.writeString(name);
            dest.writeString(headUrl);
            dest.writeString(qq);
        }
    }
}
