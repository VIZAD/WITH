package com.example.vizax.with.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Young on 2016/10/1.
 */
public class TimeUtil {

    public static String getTime(String time){
        Date nowDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        try {
            Date date=sdf.parse(time);
            long timeLong = nowDate.getTime() - date.getTime();
            System.out.println("时间是"+timeLong);
            if (timeLong<60*1000)
                return timeLong/1000 + "秒前";
            else if (timeLong<60*60*1000){
                timeLong = timeLong/1000 /60;
                return timeLong + "分钟前";
            }
            else if (timeLong<60*60*24*1000){
                timeLong = timeLong/60/60/1000;
                return timeLong+"小时前";
            }
            else if (timeLong<60*60*24*1000*7){
                timeLong = timeLong/1000/ 60 / 60 / 24;
                return timeLong + "天前";
            }
            else if (timeLong<60*60*24*1000*7*4){
                timeLong = timeLong/1000/ 60 / 60 / 24/7;
                return timeLong + "周前";
            }
            else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                return "longlongago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
}
