package com.example.vizax.with.ui.invitation;

import android.widget.EditText;
import android.widget.TextView;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.fragment.DatePickerFragment;
import com.example.vizax.with.fragment.TimePickerFragment;
import com.zhy.http.okhttp.callback.StringCallback;


public interface LuanchInvitationContact {

    interface View extends BaseView{
        String[] getResources1();
        void showTitleListpopupwindow();
        void setUpper(String text);
        void showDatePicker(DatePickerFragment datePicker);
        void dissTitleListpopupwindow();
        void showTimePicker(TimePickerFragment timePicker);
        void showCommitError(String text);
        void setluanchInvitationTitleEdtTxt(String text);
        void setDescriptionError();
        void setSiteError();
        void setUpperError();
        void destroy();
    }
    interface Modle{
        void commit(String token,String type, String titletext,String descriptiontext,String sex,String datetime,String site,String upper,Boolean hidenBoolean,StringCallback stringCallback);
    }

    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<View>{
        void listpopupwindowItemOnclick(String text);
        void showTitleListpopupwindow();
        void showDatePicker(TextView launchDateTxt);
        void showTimePicker(TextView launchTimeTxt);
        void removeUpper(EditText text);
        void addUpper(EditText text);
        void luanchInvitation(String type,String titletext,String descriptiontext,String sex,String invitation_date,String timetext,String site,String Upper,Boolean hidenBoolean,String title0);
    }
}
