package com.example.vizax.with.util;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prj on 2016/8/19.
 */

public class TextUtil {

    public static boolean isEmpty(String s){
        return s == null || TextUtils.isEmpty(s);
    }

    public static String getText(String s){
        return s==null?"":s;
    }

    public static String getText(Button bt){
        return bt.getText().toString();
    }

    public static String getText(TextView tv){
        return TextUtil.getText(tv.getText().toString());
    }

    public static String getText(EditText tv){
        return TextUtil.getText(tv.getText().toString());
    }

    public static String ToDate(Button bt){
        return bt.getText().toString().replace("#","-");
    }

    public static String ToSearch(Button bt){
        return bt.getText().toString().replace("-","#");
    }

    /**

     * @prama: str 要判断是否包含特殊字符的目标字符串

     */
    public static Matcher compileExChar(String str){
        String limitEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m;
    }
}
