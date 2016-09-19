package com.example.vizax.with.ui.Insist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateDialog extends Dialog implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter {

    private Context context;
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;
    @BindView(R.id.yearPicker)
    public
    NumberPicker yearPicker;
    @BindView(R.id.monthPicker)
    public
    NumberPicker monthPicker;

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {
//        System.out.println("picker ="+numberPicker+" i = "+i);
//        int day = i;
//        if(day==2&numberPicker==monthPicker) {
//            Toast.makeText(this.getContext(), "day=28", Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {


    }

    @Override
    public String format(int i) {
        return null;
    }

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public DateDialog(Context context, String title, String confirmButtonText, String cacelButtonText) {
        super(context, R.style.AppTheme);
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();

    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.date_dialog, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.date_title);
        TextView tvConfirm = (TextView) view.findViewById(R.id.date_confirm_btn);
        TextView tvCancel = (TextView) view.findViewById(R.id.date_cancel_btn);
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);



        ButterKnife.bind(this);
        CalendarDay cl = CalendarDay.today();
        yearPicker.setValue(cl.getYear());
        yearPicker.setOnScrollListener(this);
        monthPicker.setOnValueChangedListener(this);
        yearPicker.setMaxValue(2036);
        yearPicker.setMinValue(2016);


        monthPicker.setOnScrollListener(this);
        monthPicker.setOnValueChangedListener(this);
        monthPicker.setValue(cl.getMonth());
        monthPicker.setMaxValue(12);
        monthPicker.setMinValue(1);


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        //dialogWindow.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        //lp.alpha = 0.5f;
        setCanceledOnTouchOutside(true);
        lp.height = (int) (d.heightPixels*0.5);
        lp.width = (int) (d.widthPixels*0.5);
        //lp.alpha = 0.6f;
        //lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);

    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    @OnClick(R.id.date_confirm_btn)
    void confim() {
        clickListenerInterface.doConfirm();
    }
    @OnClick(R.id.date_cancel_btn)
    public void cancel() {
        clickListenerInterface.doCancel();
    }




}