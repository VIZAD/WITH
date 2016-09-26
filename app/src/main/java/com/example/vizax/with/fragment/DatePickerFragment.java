package com.example.vizax.with.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.vizax.with.EventBus.DateEventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * Created by maozhilin on 16/9/18.
 */
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    TextView launchDateTxt;
    DateEventMessage mDateEventMessage;
    Calendar c ;
    int year ;
    int month ;
    int day ;

    public DatePickerFragment(TextView launchDateTxt){
        this.launchDateTxt = launchDateTxt;
        mDateEventMessage = new DateEventMessage();
        c = Calendar.getInstance();

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

         year = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
}

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = year+"年"+String.valueOf(month+1)+"月"+day+"日";
        String invitation_date = year+"-"+String.valueOf(month+1)+"-"+day;
        mDateEventMessage.setDate(date);
        mDateEventMessage.setInvitation_date(invitation_date);
        EventBus.getDefault().post(mDateEventMessage);//把选择的日期广播出来
    }

}
