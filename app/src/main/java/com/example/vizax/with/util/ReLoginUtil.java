package com.example.vizax.with.util;

import android.content.Context;
import android.content.Intent;

import com.example.vizax.with.App;
import com.example.vizax.with.ui.login.MainActivity;

/**
 * Created by yum on 2016/10/3.
 */

public class ReLoginUtil {
    public static void relogin(){
        Intent intent = new Intent(App.instance, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.instance.startActivity(intent);
    }
}
