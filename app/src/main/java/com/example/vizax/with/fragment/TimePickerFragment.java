package com.example.vizax.with.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vizax.with.EventBus.TimeEventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * Created by maozhilin on 16/9/19.
 */
public class TimePickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {
    TextView launchTimeTxt;
    TimeEventMessage mTimeEventMessage;
    Calendar calendar;
    int hour;
    int minute;

    public TimePickerFragment(TextView launchTimeTxt){
        this.launchTimeTxt = launchTimeTxt;
        mTimeEventMessage = new TimeEventMessage();
        calendar = Calendar.getInstance();

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute,
                true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        //处理设置的时间，这里我们作为示例，在日志中输出我们选择的时间
        Log.d("onTimeSet", "hourOfDay: "+hour + "Minute: "+minute);
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
        mTimeEventMessage.setTime(time);
        EventBus.getDefault().post(mTimeEventMessage);
    }
}
