package com.example.vizax.with.ui.invitation;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.fragment.DatePickerFragment;
import com.example.vizax.with.fragment.TimePickerFragment;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by maozhilin on 16/9/19.
 */
public class InitInvitationPresenter implements luanch_InvitationContact.Presenter{

    private InvitationModel model;
    private static String date1;
    luanch_InvitationContact.View view;

    public static String setDate(){
        Calendar c ;
        int year ;
        int month ;
        int day ;
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String date = year+"年"+String.valueOf(month+1)+"月"+day+"日";
        date1= year+"-"+String.valueOf(month+1)+"-"+day;
        return date;
    }
    /*public static String setDate1(){
        Calendar c ;
        int year ;
        int month ;
        int day ;
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        date1= year+"-"+String.valueOf(month+1)+"-"+day;
        return date1;
    }*/
    public static List<String> setspinner()
    {
        List<String> data_list= new ArrayList<String>();
        data_list.add("篮球");
        data_list.add("足球");
        data_list.add("乒乓球");
        data_list.add("羽毛球");
        data_list.add("排球");
        data_list.add("网球");
        data_list.add("桌球");
        data_list.add("跑步");
        data_list.add("健身");
        data_list.add("游泳");
        data_list.add("户外");
        data_list.add("溜冰");
        data_list.add("其他运动");
        return data_list;
    }
    public static String setTime(){
        Calendar c ;
        c = Calendar.getInstance();
        int hour;
        int minute;
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        String time;
        String Hour;
        String Minute;
        if(minute<10){
            Minute = "0"+minute;
        }else{
            Minute = String.valueOf(minute);
        }
        if(hour<10){
            Hour = "0"+hour;
        }else{
            Hour = String.valueOf(hour);
        }
        time = Hour+":"+Minute;
        return time;

    }
    public static List<String> setTitle(String subclass){
        List<String> title_list= new ArrayList<String>();

        switch (String.valueOf(subclass)){
            case "篮球":
                title_list.add("无兄弟，不篮球！");
                title_list.add("发起了篮球活动");
                title_list.add("组队一起打篮球，求With！");
                break;
            case "足球":
                title_list.add("无兄弟，不足球！");
                title_list.add("发起了足球活动");
                title_list.add("组队一起打足球，求With！");
                break;
        }

        return title_list;
    }

    @Override
    public void listpopupwindowItemOnclick(String text){
        view.setluanchInvitationTitleEdtTxt(text);
        view.dissTitleListpopupwindow();
    }

    @Override
    public void showTitleListpopupwindow() {
        view.showTitleListpopupwindow();
    }

    @Override
    public void showDatePicker(TextView launchDateTxt) {
        DatePickerFragment datePicker = new DatePickerFragment(launchDateTxt);
        view.showDatePicker(datePicker);
    }

    @Override
    public void showTimePicker(TextView launchTimeTxt) {
        TimePickerFragment timePicker = new TimePickerFragment(launchTimeTxt);
        view.showTimePicker(timePicker);
    }

    @Override
    public void removeUpper(EditText launchUpper) {
        if(launchUpper.getText().toString().equals("01")||launchUpper.getText().toString().equals("1")||launchUpper.getText().toString().equals("")||launchUpper.getText().toString().equals("0")||launchUpper.getText().toString().equals("00")) {
            view.setUpper("99");
        }else if(Integer.parseInt(launchUpper.getText().toString())>1&&launchUpper.getText().toString()!=""){
            view.setUpper(String.valueOf(Integer.parseInt(launchUpper.getText().toString())-1));
        }
    }

    @Override
    public void addUpper(EditText launchUpper) {
        if(launchUpper.getText().toString().equals("99")||launchUpper.getText().toString().equals("")) {
            view.setUpper("1");
        }else if(Integer.parseInt(launchUpper.getText().toString())<99){
            view.setUpper(String.valueOf(Integer.parseInt(launchUpper.getText().toString())+1));
        }
    }

    @Override
    public void luanchInvitation(String type, String titletext, String descriptiontext, String sex, String invitation_date, String timetext, String site, String Upper, Boolean hidenBoolean,String title0) {
        String date;
        String invitationtype="";
        String[] str=new String[]{"篮球","排球","桌球","足球","乒乓球","羽毛球","网球","跑步","健身","游泳","户外","溜冰","其他运动","英雄联盟","守望先锋"
                ,"三国杀","Dota2","王者荣耀","CF","斗地主","DNF","其他网游","象棋","围棋","五子棋","斗地主","德州扑克","21点","三国杀","狼人杀","UNO"
                ,"其他线下游戏","自习","英语口语","英语四六级","晨读","看书","考研","BEC","其他学习","电影","吃饭","唱K","露营","散步","演唱会","其他约会"};
        for(int j=0;j<str.length;j++)
        {
            if(type==str[j])
            {
                invitationtype=(j+7)+"";
            }
        }
        if(sex.equals("男")){
            sex="0";
        }else if(sex.equals("女")) {
            sex="1";
        }else
            sex="2";
        int i=0;
        if(hidenBoolean==null)
        {hidenBoolean=false;}
        if(titletext.equals("")) {
            view.setluanchInvitationTitleEdtTxt(title0);
            titletext=title0;
        }
        if(descriptiontext.equals("")){
            view.setDescriptionError();
            i=1;
        }
        if(site.equals("")){
            view.setSiteError();
            i=1;
        }
        if(Upper.equals("")){
            view.setUpperError();
            i=1;
        }
        if(invitation_date==null) {
            date = date1 + " " + timetext;
        }
        else {
            date = invitation_date+" " + timetext;
        }
        Calendar calendar=Calendar.getInstance();
        Date datetime1=calendar.getTime();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date datetime=sim.parse(date);
            int j = datetime.compareTo(datetime1);
            if(j<0) {
                i=1;
                view.showCommitError("活动开始时间错误！应该为未来时间");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(i==0)
        {
            model=new InvitationModel();
            model.commit("1", invitationtype, titletext, descriptiontext, sex, date, site, Upper, hidenBoolean, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    view.showCommitError("aaaaaaaa发起活动失败");
                }
                @Override
                public void onResponse(String response, int id) {
                    Log.i("aaaaaaaaaa",response);
                    BaseBean<String> baseBean = GsonUtil.toString(response,BaseBean.class);
                    if (baseBean.getCode().equals("200")) {
                        view.showCommitError("发起活动成功");
                        view.onDestroy();
                    }
                    else
                        view.showCommitError("bbbbbbbbb发起活动失败");
                }
            });
        }
    }

    @Override
    public void attachView(@NonNull luanch_InvitationContact.View view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }
}
