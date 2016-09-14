package com.example.vizax.with.util;

import com.example.vizax.with.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by prj on 2016/9/14.
 */

public class GsonUtil {

    //Json转字符串
    public static <T> T toString(String response,Class<T> classZ){
        Gson gson = new Gson();
        Type objectType = type(BaseBean.class,classZ);
        return gson.fromJson(response,objectType);
        /*try {
            return new Gson().fromJson(response,new TypeToken<T>(){}.getType());
        }catch (Throwable e){
            return null;
        }*/
       //return  new Gson().fromJson(response,classZ);
    }

    //字符串转Json
    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

    public static ParameterizedType type(final Class raw,final Type... args){
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
