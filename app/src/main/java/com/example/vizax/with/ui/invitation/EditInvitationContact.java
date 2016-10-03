package com.example.vizax.with.ui.invitation;

import android.widget.EditText;
import android.widget.TextView;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.fragment.DatePickerFragment;
import com.example.vizax.with.fragment.TimePickerFragment;

/**
 * Created by hasee on 2016/9/26.
 */

public interface EditInvitationContact {
    interface View extends BaseView{
        void showDatePicker(DatePickerFragment datePicker);
        void showTimePicker(TimePickerFragment timePicker);
        void showTitleListpopupwindow();
        void setUpper(String text);
        void dissTitleListpopupwindow();
        void setluanchInvitationTitleEdtTxt(String text);
        void setDescriptionError();
        void setSiteError();
        void setUpperError();
        void showCommitError(String text);
        void onDestroy();
    }
    interface Modle{

    }
    interface Presenter extends BasePresenter<View> {
        void showDatePicker(TextView launchDateTxt);
        void showTimePicker(TextView launchTimeTxt);
        void removeUpper(EditText launchUpper);
        void addUpper(EditText launchUpper);
        void showTitleListpopupwindow();
        void listpopupwindowItemOnclick(String text);
        void luanchInvitation(String type, String titletext, String descriptiontext, String sex, String invitation_date, String timetext, String site, String Upper, Boolean hidenBoolean, String title0);
    }
}
