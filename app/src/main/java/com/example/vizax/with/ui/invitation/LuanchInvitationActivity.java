package com.example.vizax.with.ui.invitation;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.Toast;

import com.example.vizax.with.EventBus.DateEventMessage;
import com.example.vizax.with.EventBus.TimeEventMessage;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.fragment.DatePickerFragment;
import com.example.vizax.with.fragment.TimePickerFragment;
import com.example.vizax.with.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class LuanchInvitationActivity extends SwipeBackActivity implements LuanchInvitationContact.View{

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
    /*@BindView(R.id.launch_hide_information_swt)
    Switch launchHideInformationSwt;*/
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
    private String sex;
    private String invitation_date;
    private LuanchInitInvitationPresenter init;
    private Boolean hidenBoolean;
    private RadioButton check_RdoBtn;
    private int typeId ;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        EventBus.getDefault().register(this);//注册
        ButterKnife.bind(this);
        typeId =  Integer.parseInt(getIntent().getStringExtra("typeId"));
        init = new LuanchInitInvitationPresenter(this);
        init.attachView(this);
        launchDateTxt.setText(init.setDate());
        launchTimeTxt.setText(init.setTime());
        initSpinner(init);
        launchUnlimitedRdoBtn.setChecked(true);
        sex=launchUnlimitedRdoBtn.getText().toString();
        //Log.w("haha123",typeId+"!!!!");
        /*launchHideInformationSwt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    hidenBoolean=true;
                }
                else
                    hidenBoolean=false;
            }
        });*/
        launchSexRequirementsRdoGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                check_RdoBtn= (RadioButton) findViewById(launchSexRequirementsRdoGrp.getCheckedRadioButtonId());
                sex=check_RdoBtn.getText().toString();
            }
        });
        //设置swipback参数
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        launchToolbar.setLeftViewOnClickListener(v -> finish());
    }
    @Override
    public void setluanchInvitationTitleEdtTxt(String text){
        launchInvitationTitleEdtTxt.setText(text);
    }

    @Override
    public void setDescriptionError() {
        launchDescriptionEdiTxt.setError("内容描述不能为空!");
    }

    @Override
    public void setSiteError() {
        launchSiteEdtTxt.setError("活动地点不能为空!");
    }

    @Override
    public void setUpperError() {
        launchUpper.setError("活动人数不能为空！");
    }

    @Override
    public String[] getResources1() {
        String[]  items= getResources().getStringArray(R.array.sports);
        return items;
    }

    @Override
    public void showTitleListpopupwindow() {
        mListPop.show();
    }

    @Override
    public void setUpper(String text) {
        launchUpper.setText(text);
    }

    @Override
    public void showDatePicker(DatePickerFragment datePicker) {
        datePicker.show(getFragmentManager(),"datePicker");
    }
    @Override
    public void dissTitleListpopupwindow(){
        mListPop.dismiss();
    }
    @Override
    public void showTimePicker(TimePickerFragment timePicker) {
        timePicker.show(getFragmentManager(), "timePicker");
    }
    @Override
    public void showCommitError(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    private void listpopupwindow(LuanchInitInvitationPresenter init,String subclass) {
        title_list = init.setTitle(subclass);
        mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,title_list));
        mListPop.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListPop.setAnchorView(launchInvitationTitleEdtTxt);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListPop.setModal(true);//设置是否是模式
        mListPop.setOnItemClickListener((parent, view, position, id) -> {
            init.listpopupwindowItemOnclick(title_list.get(position));
        });
        launchInvitationTitleEdtTxt.setOnClickListener(v -> {
            init.showTitleListpopupwindow();
        });
    }
    public void initSpinner(LuanchInitInvitationPresenter init){
        spinner = (Spinner) findViewById(R.id.launch_selectActivity_spinner);
        subclass_list= init.setspinner(typeId);
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
        invitation_date=mDateEventMessage.getDate1();
    }
    @Subscribe
    public void onEvent(TimeEventMessage mTimeEventMessage){//接收TimeEventMessage类的广播信息
        launchTimeTxt.setText(mTimeEventMessage.getTime());
    }

    @OnClick({R.id.launch_toolbar, R.id.launch_invitationTitle_edtTxt, R.id.launch_description_ediTxt, R.id.launch_man_rdoBtn, R.id.launch_woman_rdoBtn, R.id.launch_unlimited_rdoBtn, R.id.launch_sex_requirements_rdoGrp, R.id.launch_date_Txt, R.id.launch_time_Txt, R.id.launch_site_edtTxt, R.id.launch_remove_imgBtn, R.id.launch_upper, R.id.launch_add_imgBtn, R.id.launch_ensureBtn, R.id.launch_cancelBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.launch_date_Txt:
                init.showDatePicker(launchDateTxt);
                break;
            case R.id.launch_time_Txt:
                init.showTimePicker(launchTimeTxt);
                break;
            case R.id.launch_remove_imgBtn:
                init.removeUpper(launchUpper);
                break;
            case R.id.launch_add_imgBtn:
                init.addUpper(launchUpper);
                break;
            case R.id.launch_ensureBtn:
                init.luanchInvitation(subclass,launchInvitationTitleEdtTxt.getText().toString(),launchDescriptionEdiTxt.getText().toString(),
                        sex,invitation_date,launchTimeTxt.getText().toString(),launchSiteEdtTxt.getText().toString(),launchUpper.getText().toString(),
                        false,title_list.get(0));
                break;
        }
    }

    @Override
    public void destroy() {
        init.detachView();
        setResult(2);
        finish();
    }
}
