package com.example.vizax.with.util;

import com.example.vizax.with.R;
import com.example.vizax.with.customView.ReboundScrollView;

/**
 * Created by Young on 2016/9/20.
 */
public class ArrayUtil {
    int result;
    public static int getArray(String title){
       if(title.equals("运动")||title.equals("篮球")||title.equals("足球")||title.equals("羽毛球")||title.equals("乒乓球")||title.equals("跑步")||title.equals("游泳")){
           return R.array.sports;
       }else if(title.equals("学习")||title.equals("自习")||title.equals("看书")||title.equals("考研")||title.equals("BEC")||title.equals("英语口语")||title.equals("英语四六级")){
           return R.array.study;
       }else if(title.equals("桌游")||title.equals("三国杀")||title.equals("UNO")||title.equals("狼人杀")||title.equals("象棋")||title.equals("21点")||title.equals("围棋")||title.equals("五子棋")||title.equals("德州扑克")){
           return R.array.brpg;
       }else if(title.equals("网游")||title.equals("英雄联盟")||title.equals("DOTA2")||title.equals("王者荣耀")||title.equals("守望先锋")||title.equals("CF")||title.equals("斗地主")||title.equals("地下城与勇士")){
           return R.array.onlineGame;
       }else if(title.equals("聚会")||title.equals("吃饭")||title.equals("唱K")||title.equals("露营")||title.equals("演唱会")||title.equals("电影")){
           return R.array.date;
       }else {
           return 0;
       }
    }
}
