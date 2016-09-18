package com.example.vizax.with.ui.Insist.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;
import com.example.vizax.with.ui.Insist.AddDateRcyViAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItemDialog extends Dialog implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter {

    private Context context;
    private ClickListenerInterface clickListenerInterface;
    private AddDateRcyViAdapter adapter;
    private int finalI;
    @BindView(R.id.insist_ic_bg_rcyVi)
    RecyclerView mRcyVi;
    @BindView(R.id.mission_title)
    EditText EdtTxt_title;
    @BindView(R.id.mission_content)
    EditText EdtTxt_content;
    @BindView(R.id.mission_add_focus_txt)
    TextView TxtVi_content;
    public String title;
    public String content;

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

    @Override
    public String format(int i) {
        return null;
    }

    public interface ClickListenerInterface {

        public int doConfirm();
        public int doCancel();
    }

    public AddItemDialog(Context context,int finalI, String title, String confirmButtonText, String cacelButtonText) {
        super(context, R.style.AppTheme);

        this.context = context;
        this.finalI = finalI;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Fresco.initialize(context);
        super.onCreate(savedInstanceState);
        init();

    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_item_dialog, null);
        setContentView(view);
        ButterKnife.bind(this);
        TxtVi_content.setFocusable(true);
        TxtVi_content.setFocusableInTouchMode(true);
        title = EdtTxt_title.getText().toString();
        content = EdtTxt_content.getText().toString();
        mRcyVi = (RecyclerView) view.findViewById(R.id.insist_ic_bg_rcyVi);
        List<String> list = new ArrayList<>();

        list.add(String.valueOf(R.drawable.icn_1));
        list.add(String.valueOf(R.drawable.icn_2));
        list.add(String.valueOf(R.drawable.icn_3));
        list.add(String.valueOf(R.drawable.icn_4));
        list.add(String.valueOf(R.drawable.icn_5));
        adapter = new AddDateRcyViAdapter(list,context);
        adapter.openLoadAnimation();

        adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.pic :
                        adapter.reset(list.get(i).toString(),i,finalI);
                        break;
                }

            }
        });

        mRcyVi.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        // rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRcyVi.setAdapter(adapter);



        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        //dialogWindow.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        //DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        //lp.alpha = 0.5f;
        setCanceledOnTouchOutside(true);
        lp.height = 780;
        lp.width = 1000;
        //lp.alpha = 0.6f;
        //lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);

    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    @OnClick(R.id.addItem_cfm_btn)
    void confim() {
        clickListenerInterface.doConfirm();
    }
    @OnClick(R.id.addItem_cancel_btn)
    public void cancel() {
        clickListenerInterface.doCancel();
    }



}