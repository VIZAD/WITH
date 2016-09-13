package com.example.vizax.with.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sll on 2015/5/16.
 */
public class SettingPrefUtil {

  public static int getSwipeBackEdgeMode(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return Integer.parseInt(prefs.getString("pSwipeBackEdgeMode", "0"));
  }
}
