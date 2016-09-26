package com.example.vizax.with.ui.invitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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
    private String date;
    private String invitation_date;
    private List<String> subclass_list;
    private List<String> title_list;
    private ArrayAdapter<String> arr_adapter;
    private ListPopupWindow mListPop;
    private String subclass;
    private InitInvitation init;
    private RadioButton checked_RdoBtn;
    private String sex;
    private Boolean hidenBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        EventBus.getDefault().register(this);//注册
        ButterKnife.bind(this);
        init = new InitInvitation();

        launchDateTxt.setText(init.setDate());
        launchTimeTxt.setText(init.setTime());
        initSpinner(init);
        launchUnlimitedRdoBtn.setChecked(true);

        launchHideInformationSwt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    hidenBoolean=true;
                }
                else
                    hidenBoolean=false;
            }
        });



    }

    private void listpopupwindow(InitInvitation init,String subclass) {
        title_list = init.setTitle(subclass);
        mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,R.array.incitation));
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




    public void initSpinner(InitInvitation init){
        spinner = (Spinner) findViewById(R.id.launch_selectActivity_spinner);
        subclass_list= init.setspinner();
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
        invitation_date = mDateEventMessage.getInvitation_date();
    }
    @Subscribe
    public void onEvent(TimeEventMessage mTimeEventMessage){//接收TimeEventMessage类的广播信息
        launchTimeTxt.setText(mTimeEventMessage.getTime());
    }

    @OnClick({R.id.launch_toolbar, R.id.launch_invitationTitle_edtTxt, R.id.launch_description_ediTxt, R.id.launch_man_rdoBtn, R.id.launch_woman_rdoBtn, R.id.launch_unlimited_rdoBtn, R.id.launch_sex_requirements_rdoGrp, R.id.launch_date_Txt, R.id.launch_time_Txt, R.id.launch_site_edtTxt, R.id.launch_remove_imgBtn, R.id.launch_upper, R.id.launch_add_imgBtn, R.id.launch_hide_information_swt, R.id.launch_ensureBtn, R.id.launch_cancelBtn})
    public void onClick(View view) {
        switch (view.getId()) {

            //选择活动的日期
            case R.id.launch_date_Txt:
                DatePickerFragment datePicker = new DatePickerFragment(launchDateTxt);
                datePicker.show(getFragmentManager(),"datePicker");
                break;

            //选择活动的时间
            case R.id.launch_time_Txt:
                TimePickerFragment  timePicker = new TimePickerFragment(launchTimeTxt);
                timePicker.show(getFragmentManager(), "timePicker");
                break;

            //人数上限－1
            case R.id.launch_remove_imgBtn:
                if(launchUpper.getText().toString().equals("1")||launchUpper.getText().toString().equals("")||launchUpper.getText().toString().equals("0")||launchUpper.getText().toString().equals("00")) {
                    launchUpper.setText("99");
                }else if(Integer.parseInt(launchUpper.getText().toString())>1&&launchUpper.getText().toString()!=""){
                    launchUpper.setText(String.valueOf(Integer.parseInt(launchUpper.getText().toString())-1));
                }
                break;

            //人数上限＋1
            case R.id.launch_add_imgBtn:
                if(launchUpper.getText().toString().equals("99")||launchUpper.getText().toString().equals("")) {
                    launchUpper.setText("1");
                }else if(Integer.parseInt(launchUpper.getText().toString())<99){
                    launchUpper.setText(String.valueOf(Integer.parseInt(launchUpper.getText().toString())+1));
                }
                break;

            //确定发起活动
            case R.id.launch_ensureBtn:
                if(launchInvitationTitleEdtTxt.getText().toString().equals(""))
                launchInvitationTitleEdtTxt.setText(title_list.get(0));
                if(launchDescriptionEdiTxt.getText().toString().equals("")){
                launchDescriptionEdiTxt.setError("内容描述不能为空!");
                }
                if(launchSiteEdtTxt.getText().toString().equals("")){
                launchSiteEdtTxt.setError("活动地点不能为空!");
                }
                if(launchUpper.getText().toString().equals("")){
                launchUpper.setError("活动人数不能为空！");
                }

                checked_RdoBtn= (RadioButton) findViewById(launchSexRequirementsRdoGrp.getCheckedRadioButtonId());
                sex = checked_RdoBtn.getText().toString();
                break;
            case R.id.launch_cancelBtn:
                break;
        }
    }
}
