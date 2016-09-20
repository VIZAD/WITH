package com.example.vizax.with.ui.invitation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maozhilin on 16/9/19.
 */
public class InitInvitation {



    public static String setDate(){
        Calendar c ;
        int year ;
        int month ;
        int day ;
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String date = year+"年"+String.valueOf(month+1)+"月"+day+"日";
        return date;

    }
    public static List<String> setspinner()
    {
        List<String> data_list= new ArrayList<String>();

        data_list.add("篮球");
        data_list.add("足球");
        data_list.add("乒乓球");
        data_list.add("羽毛球");
        data_list.add("排球");
        data_list.add("网球");
        data_list.add("桌球");
        data_list.add("跑步");
        data_list.add("健身");
        data_list.add("游泳");
        data_list.add("户外");
        data_list.add("溜冰");
        return data_list;
    }
    public static String setTime(){
        Calendar c ;
        c = Calendar.getInstance();
        int hour;
        int minute;
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        String time;
        String Hour;
        String Minute;
        if(minute<10){
            Minute = "0"+minute;
        }else{
            Minute = String.valueOf(minute);
        }
        if(hour<10){
            Hour = "0"+hour;
        }else{
            Hour = String.valueOf(hour);
        }
        time = Hour+"："+Minute;
        return time;

    }
    public static List<String> setTitle(String subclass){



        List<String> title_list= new ArrayList<String>();

        switch (String.valueOf(subclass)){
            case "篮球":
                title_list.add("无兄弟，不篮球！");
                title_list.add("发起了篮球活动");
                title_list.add("组队一起打篮球，求With！");
                break;
            case "足球":
                title_list.add("无兄弟，不足球！");
                title_list.add("发起了足球活动");
                title_list.add("组队一起打足球，求With！");
                break;
        }

        return title_list;
    }

}
