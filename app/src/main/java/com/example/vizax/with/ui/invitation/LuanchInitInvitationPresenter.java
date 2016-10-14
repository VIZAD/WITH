package com.example.vizax.with.ui.invitation;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.fragment.DatePickerFragment;
import com.example.vizax.with.fragment.TimePickerFragment;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.StringUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by maozhilin on 16/9/19.
 */
public class LuanchInitInvitationPresenter implements LuanchInvitationContact.Presenter{

    private LuanchInvitationModel model;
    private static String date1;
    LuanchInvitationContact.View view;
    private Context context;
    Resources resources ;
    LuanchInitInvitationPresenter(Context context){
        this.context = context;

    }

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
    public  List<String> setspinner(int typeId)
    {
        System.out.println("typeId="+typeId);
        List<String> data_list= new ArrayList<String>();
        if(typeId>=7&&typeId<=19||typeId==1)
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.sports));
        else if(typeId>=20&&typeId<=28||typeId==2)
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.onlineGame));
        else if(typeId>=29&&typeId<=38||typeId==3)
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.brpg));
        else if(typeId>=39&&typeId<=46||typeId==4)
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.study));
        else if(typeId>=47&&typeId<=53||typeId==5)
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.date));
        else
            data_list = Arrays.asList(context.getResources().getStringArray(R.array.other));
        return data_list;
    }
    public  String setTime(){
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
    public  List<String> setTitle(String subclass){
        List<String> title_list= new ArrayList<String>();
                switch (String.valueOf(subclass)){
                    case "篮球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_basketball));
                    break;
                    case "足球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_football));
                        break;
                    case "乒乓球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_pingpongball));
                        break;
                    case "羽毛球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_badminton));
                        break;
                    case "排球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_volleyball));
                        break;
                    case "网球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_tennis));
                        break;
                    case "桌球":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_billiards));
                        break;
                    case "跑步":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_run));
                        break;
                    case "健身":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_bodybuilding));
                        break;
                    case "游泳":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_swimming));
                        break;
                    case "户外":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_outdoors));
                        break;
                    case "溜冰":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_skating));
                        break;
                    case "其他运动":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_other));
                        break;
                    case "自习":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_selfstudy));
                        break;
                    case "英语口语":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_spokenenglish));
                        break;
                    case "英语四六级":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_cet));
                        break;
                    case "晨读":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_morningreading));
                        break;
                    case "图书馆":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_read));
                        break;
                    case "考研":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_pubmed));
                        break;
                    case "BEC":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_bec));
                        break;
                    case "其他学习":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_other));
                        break;
                    case "三国杀":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_wwtk));
                        break;
                    case "狼人杀":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_werewolfkill));
                        break;
                    case "UNO":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_uno));
                        break;
                    case "围棋":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_go));
                        break;
                    case "象棋":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_chinesechess));
                        break;
                    case "五子棋":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_gobang));
                        break;
                    case "德州扑克":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_texasholdem));
                        break;
                    case "21点":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_point21));
                        break;
                    case "其他":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_other));
                        break;
                    case "英雄联盟":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_lol));
                        break;
                    case "Dota2":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_dota2));
                        break;
                    case "守望先锋":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_overwatch));
                        break;
                    case "CF":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_cf));
                        break;
                    case "DNF":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_dnf));
                        break;
                    case "王者荣耀":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_kingglory));
                        break;
                    case "斗地主":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_fal));
                        break;
                    case "其他网游":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_other));
                        break;
                    case "吃饭":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_dinner));
                        break;
                    case "唱K":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_karaoke));
                        break;
                    case "电影":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_film));
                        break;
                    case "散步":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_walk));
                        break;
                    case "演唱会":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_concert));
                        break;
                    case "露营":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.title_camping));
                        break;
                    case "其他约会":
                        title_list = Arrays.asList(context.getResources().getStringArray(R.array.other));
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
        //获取邀约类别id
        invitationtype = StringUtil.invitationIdUtil(type);
        Log.w("aaaa",invitationtype);
       /* String[] str=new String[]{"篮球","排球","桌球","足球","乒乓球","羽毛球","网球","跑步","健身","游泳","户外","溜冰","其他运动","英雄联盟","守望先锋"
                ,"三国杀","Dota2","王者荣耀","CF","斗地主","DNF","其他网游","象棋","围棋","五子棋","斗地主","德州扑克","21点","三国杀","狼人杀","UNO"
                ,"其他线下游戏","自习","英语口语","英语四六级","晨读","看书","考研","BEC","其他学习","电影","吃饭","唱K","露营","散步","演唱会","其他约会"};
        for(int j=0;j<str.length;j++)
        {
            if(type==str[j])
            {
                invitationtype=(j+7)+"";
            }
        }*/
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
            model=new LuanchInvitationModel();
            model.commit("1", invitationtype, titletext, descriptiontext, sex, date, site, Upper, hidenBoolean, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    view.showCommitError("发起活动失败");
                }
                @Override
                public void onResponse(String response, int id) {
                    //Log.w("aaaaaaaaaa",response);
                    BaseBean baseBean = GsonUtil.toString(response,BaseBean.class);
                    if (baseBean.getCode().equals("200")) {
                        view.showCommitError("发起活动成功");
                        view.destroy();
                    }
                    else
                        view.showCommitError("发起活动失败");
                }
            });
        }
    }

    @Override
    public void attachView(@NonNull LuanchInvitationContact.View view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }
}
