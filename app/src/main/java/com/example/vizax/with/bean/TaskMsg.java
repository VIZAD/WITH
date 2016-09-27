package com.example.vizax.with.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by VIZAX on 2016/09/16.
 */

public class TaskMsg implements Parcelable {
    /**
     * task : {"taskId":1,"teskContent":"每天早上6点读30分钟","teskTitle":"背英语单词"}
     * calendar : [{"remark":"今天头疼，没背","jour_punch":true,"day":1}]
     */

    private DataBean data;
    /**
     * data : {"task":{"taskId":1,"teskContent":"每天早上6点读30分钟","teskTitle":"背英语单词"},"calendar":[{"remark":"今天头疼，没背","jour_punch":true,"day":1}]}
     * code : 200
     * msg : xxxx
     */

    private String code;
    private String msg;


    protected TaskMsg(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    public static final Creator<TaskMsg> CREATOR = new Creator<TaskMsg>() {
        @Override
        public TaskMsg createFromParcel(Parcel in) {
            return new TaskMsg(in);
        }

        @Override
        public TaskMsg[] newArray(int size) {
            return new TaskMsg[size];
        }
    };

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
    }

    public static class DataBean implements Parcelable {
        /**
         * taskId : 1
         * teskContent : 每天早上6点读30分钟
         * teskTitle : 背英语单词
         */

        private TaskBean task;
        /**
         * remark : 今天头疼，没背
         * jour_punch : true
         * day : 1
         */

        private List<CalendarBean> calendar;

        protected DataBean(Parcel in) {
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

        public TaskBean getTask() {
            return task;
        }

        public void setTask(TaskBean task) {
            this.task = task;
        }

        public List<CalendarBean> getCalendar() {
            return calendar;
        }

        public void setCalendar(List<CalendarBean> calendar) {
            this.calendar = calendar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class TaskBean {
            private int taskId;
            private String teskContent;
            private String teskTitle;

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }

            public String getTeskContent() {
                return teskContent;
            }

            public void setTeskContent(String teskContent) {
                this.teskContent = teskContent;
            }

            public String getTeskTitle() {
                return teskTitle;
            }

            public void setTeskTitle(String teskTitle) {
                this.teskTitle = teskTitle;
            }
        }

        public static class CalendarBean implements Parcelable {
            private String remark;
            private boolean jour_punch;
            private int day;

            protected CalendarBean(Parcel in) {
                remark = in.readString();
                jour_punch = in.readByte() != 0;
                day = in.readInt();
            }

            public static final Creator<CalendarBean> CREATOR = new Creator<CalendarBean>() {
                @Override
                public CalendarBean createFromParcel(Parcel in) {
                    return new CalendarBean(in);
                }

                @Override
                public CalendarBean[] newArray(int size) {
                    return new CalendarBean[size];
                }
            };

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public boolean isJour_punch() {
                return jour_punch;
            }

            public void setJour_punch(boolean jour_punch) {
                this.jour_punch = jour_punch;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(remark);
                dest.writeByte((byte) (jour_punch ? 1 : 0));
                dest.writeInt(day);
            }
        }
    }
}
