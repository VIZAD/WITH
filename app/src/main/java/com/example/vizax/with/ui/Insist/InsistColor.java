package com.example.vizax.with.ui.Insist;

import android.app.Activity;

import com.example.vizax.with.R;

/**
 * Created by VIZAX on 2016/09/18.
 */

public class InsistColor {
    public Activity activity;
    public  int COLOR_CALENDER ;
    public  int COLOR1_CALENDER;
    public  int COLOR2_CALENDER;
    public  int COLOR3_CALENDER;
    public  int COLOR4_CALENDER;
    public  int COLOR5_CALENDER;
    public  int COLOR1_MISSION;
    public  int COLOR2_MISSION;
    public  int COLOR3_MISSION;
    public  int COLOR4_MISSION;
    public  int COLOR5_MISSION;
    public  int COLOR1_MOOD;
    public  int COLOR2_MOOD;
    public  int COLOR3_MOOD;
    public  int COLOR4_MOOD;
    public  int COLOR5_MOOD;
    public  int COLOR1_TXT;
    public  int COLOR2_TXT;
    public  int COLOR3_TXT;
    public  int COLOR4_TXT;
    public  int COLOR5_TXT;

    public InsistColor( Activity activity) {
        this.activity = activity;
        init();
    }
    private void init() {
        COLOR_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg1);
        COLOR1_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg1);
        COLOR2_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg2);
        COLOR3_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg3);
        COLOR4_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg4);
        COLOR5_CALENDER = activity.getResources().getColor(R.color.insist_calendar_bg5);
        COLOR1_MISSION = activity.getResources().getColor(R.color.insist_mission_btn1);
        COLOR2_MISSION = activity.getResources().getColor(R.color.insist_mission_btn2);
        COLOR3_MISSION = activity.getResources().getColor(R.color.insist_mission_btn3);
        COLOR4_MISSION = activity.getResources().getColor(R.color.insist_mission_btn4);
        COLOR5_MISSION = activity.getResources().getColor(R.color.insist_mission_btn5);
        COLOR1_MOOD = activity.getResources().getColor(R.color.insist_mood_bg1);
        COLOR2_MOOD = activity.getResources().getColor(R.color.insist_mood_bg2);
        COLOR3_MOOD = activity.getResources().getColor(R.color.insist_mood_bg3);
        COLOR4_MOOD = activity.getResources().getColor(R.color.insist_mood_bg4);
        COLOR5_MOOD = activity.getResources().getColor(R.color.insist_mood_bg4);
        COLOR1_TXT = activity.getResources().getColor(R.color.white);
        COLOR2_TXT = activity.getResources().getColor(R.color.white);
        COLOR3_TXT = activity.getResources().getColor(R.color.white);
        COLOR4_TXT = activity.getResources().getColor(R.color.white);
        COLOR5_TXT = activity.getResources().getColor(R.color.white);
    }



}
