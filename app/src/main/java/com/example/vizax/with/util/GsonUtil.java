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
    }

    //Json转字符串，传入对象
    public static Object toString(String response,Object object){
        try {
            return new Gson().fromJson(response,new TypeToken<Object>(){}.getType());
        }catch (Throwable e){
            return null;
        }
    }

    //字符串转Json
    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

    /**
     * ParameterizedType对象，对于Object、接口和原始类型返回null，对于数组class则是返回Object.class。
     * ParameterizedType是表示带有泛型参数的类型的Java类型，
     * JDK1.5引入了泛型之后，Java中所有的Class都实现了Type接口，ParameterizedType则是继承了Type接口，
     * 所有包含泛型的Class类都会实现这个接口。
     * @param raw
     * @param args
     * @return
     */
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
