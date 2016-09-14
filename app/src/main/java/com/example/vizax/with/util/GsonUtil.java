package com.example.vizax.with.util;

import com.google.gson.Gson;

/**
 * Created by prj on 2016/9/14.
 */

public class GsonUtil {

    //Json转字符串
    public static <T> T toString(String response,Class<T> classZ){
       return  new Gson().fromJson(response,classZ);
    } ;

    //字符串转Json
    public static String toJson(Object object){
        return new Gson().toJson(object);
    }
}
