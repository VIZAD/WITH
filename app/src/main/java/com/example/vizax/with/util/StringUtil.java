package com.example.vizax.with.util;

/**
 * 字符串裁剪工具类
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
}
