package com.example.vizax.with.util;

/**
 * 字符串工具类
 * Created by Young on 2016/9/16.
 */
public class StringUtil {
    private static String  result;

    /**
     * 裁剪文本长度
     * @param contents 字符串
     * @param length 裁剪的长度
     * @return
     */
    public static String cutContents(String contents,int length){
        if(contents.length() >= length){
            result = contents.substring(0,length)+"...";

        }else{
            result = contents;
        }
        return result;
    }

    public static  String phoneUtil(String phoneNum){
        return phoneNum.substring(0,3)+"****"+phoneNum.substring(7,10);
    }
}
