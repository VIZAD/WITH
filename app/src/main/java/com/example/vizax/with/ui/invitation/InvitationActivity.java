package com.example.vizax.with.ui.invitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.example.vizax.with.customView.BaseToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitationActivity extends AppCompatActivity {

    @BindView(R.id.launch_toolbar)
    BaseToolBar launchToolbar;
    @BindView(R.id.launch_invitationTitle_edtTxt)
    EditText launchInvitationTitleEdtTxt;
    @BindView(R.id.launch_description_ediTxt)
    EditText launchDescriptionEdiTxt;
    @BindView(R.id.launch_man_rdoBtn)
    RadioButton launchManRdoBtn;
    @BindView(R.id.launch_woman_rdoBtn)
    RadioButton launchWomanRdoBtn;
    @BindView(R.id.launch_unlimited_rdoBtn)
    RadioButton launchUnlimitedRdoBtn;
    @BindView(R.id.launch_sex_requirements_rdoGrp)
    RadioGroup launchSexRequirementsRdoGrp;
    @BindView(R.id.launch_date_Txt)
    TextView launchDateTxt;
    @BindView(R.id.launch_time_Txt)
    TextView launchTimeTxt;
    @BindView(R.id.launch_site_edtTxt)
    EditText launchSiteEdtTxt;
    @BindView(R.id.launch_remove_imgBtn)
    ImageButton launchRemoveImgBtn;
    @BindView(R.id.launch_upper)
    EditText launchUpper;
    @BindView(R.id.launch_add_imgBtn)
    ImageButton launchAddImgBtn;
    @BindView(R.id.launch_hide_information_swt)
    Switch launchHideInformationSwt;
    @BindView(R.id.launch_ensureBtn)
    Button launchEnsureBtn;
    @BindView(R.id.launch_cancelBtn)
    Button launchCancelBtn;

    private Spinner spinner;
    private List<String> subclass_list;
    private List<String> title_list;
    private ArrayAdapter<String> arr_adapter;
    private ListPopupWindow mListPop;
    private String subclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        EventBus.getDefault().register(this);//注册
        ButterKnife.bind(this);
        InitInvitation init = new InitInvitation();

        launchDateTxt.setText(init.setDate());
        launchTimeTxt.setText(init.setTime());
        initSpinner(init);
        launchUnlimitedRdoBtn.setChecked(true);


    }

    private void listpopupwindow(InitInvitation init,String subclass) {
        title_list = init.setTitle(subclass);
        mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,title_list));
        mListPop.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListPop.setAnchorView(launchInvitationTitleEdtTxt);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListPop.setModal(true);//设置是否是模式
        mListPop.setOnItemClickListener((parent, view, position, id) -> {
            launchInvitationTitleEdtTxt.setText(title_list.get(position));
            mListPop.dismiss();
        });
        launchInvitationTitleEdtTxt.setOnClickListener(v -> mListPop.show());
    }


//    public void initDate(){
//        c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DAY_OF_MONTH);
//        String date = year+"年"+String.valueOf(month+1)+"月"+day+"日";
//        launchDateTxt.setText(date);
//    }
//    public void initTime(){
//        c = Calendar.getInstance();
//        hour = c.get(Calendar.HOUR_OF_DAY);
//        minute = c.get(Calendar.MINUTE);
//        String time;
//        if(minute<10){
//            time = hour+":0"+minute;
//        }else{
//            time = hour+":"+minute;
//        }
//        launchTimeTxt.setText(time);
//    }

    public void initSpinner(InitInvitation init){
        spinner = (Spinner) findViewById(R.id.launch_selectActivity_spinner);

        subclass_list= init.setspinner();

//        data_list.add("篮球");
//        data_list.add("足球");
//        data_list.add("乒乓球");
//        data_list.add("羽毛球");
//        data_list.add("排球");
//        data_list.add("网球");
//        data_list.add("桌球");
//        data_list.add("跑步");
//        data_list.add("健身");
//        data_list.add("游泳");
//        data_list.add("户外");
//        data_list.add("溜冰");
        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subclass_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subclass = subclass_list.get(position);
                listpopupwindow(init,subclass);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Subscribe
    public void onEvent(DateEventMessage mDateEventMessage){//接收DateEventMessage类的广播信息
        launchDateTxt.setText(mDateEventMessage.getDate());
    }
    @Subscribe
    public void onEvent(TimeEventMessage mTimeEventMessage){//接收TimeEventMessage类的广播信息
        launchTimeTxt.setText(mTimeEventMessage.getTime());
    }

    @OnClick({R.id.launch_toolbar, R.id.launch_invitationTitle_edtTxt, R.id.launch_description_ediTxt, R.id.launch_man_rdoBtn, R.id.launch_woman_rdoBtn, R.id.launch_unlimited_rdoBtn, R.id.launch_sex_requirements_rdoGrp, R.id.launch_date_Txt, R.id.launch_time_Txt, R.id.launch_site_edtTxt, R.id.launch_remove_imgBtn, R.id.launch_upper, R.id.launch_add_imgBtn, R.id.launch_hide_information_swt, R.id.launch_ensureBtn, R.id.launch_cancelBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.launch_toolbar:
                break;
            case R.id.launch_invitationTitle_edtTxt:
                break;
            case R.id.launch_description_ediTxt:
                break;
            case R.id.launch_man_rdoBtn:
                break;
            case R.id.launch_woman_rdoBtn:
                break;
            case R.id.launch_unlimited_rdoBtn:
                break;
            case R.id.launch_sex_requirements_rdoGrp:
                break;
            case R.id.launch_date_Txt:
                DatePickerFragment datePicker = new DatePickerFragment(launchDateTxt);
                datePicker.show(getFragmentManager(),"datePicker");
                break;
            case R.id.launch_time_Txt:
                TimePickerFragment  timePicker = new TimePickerFragment(launchTimeTxt);
                timePicker.show(getFragmentManager(), "timePicker");
                break;
            case R.id.launch_site_edtTxt:
                break;
            case R.id.launch_remove_imgBtn:
                if(launchUpper.getText().toString().equals("1")||launchUpper.getText().toString().equals("")||launchUpper.getText().toString().equals("0")||launchUpper.getText().toString().equals("00")) {
                    launchUpper.setText("99");
                }else if(Integer.parseInt(launchUpper.getText().toString())>1&&launchUpper.getText().toString()!=""){
                    launchUpper.setText(String.valueOf(Integer.parseInt(launchUpper.getText().toString())-1));
                }
                break;
            case R.id.launch_upper:
                break;
            case R.id.launch_add_imgBtn:
                if(launchUpper.getText().toString().equals("99")||launchUpper.getText().toString().equals("")) {
                    launchUpper.setText("1");
                }else if(Integer.parseInt(launchUpper.getText().toString())<99){
                    launchUpper.setText(String.valueOf(Integer.parseInt(launchUpper.getText().toString())+1));
                }
                break;
            case R.id.launch_hide_information_swt:
                break;
            case R.id.launch_ensureBtn:

                break;
            case R.id.launch_cancelBtn:
                break;
        }
    }
}
