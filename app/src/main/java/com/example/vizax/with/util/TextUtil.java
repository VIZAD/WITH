package com.example.vizax.with.util;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

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

    public static String ToDate(Button bt){
        return bt.getText().toString().replace("#","-");
    }

    public static String ToSearch(Button bt){
        return bt.getText().toString().replace("-","#");
    }
}
