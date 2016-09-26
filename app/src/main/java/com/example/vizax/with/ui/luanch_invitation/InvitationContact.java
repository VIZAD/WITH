package com.example.vizax.with.ui.luanch_invitation;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;



public interface InvitationContact {


    interface View extends BaseView{
        void showTypeSpinner();

        //void dimissTypeSpinner();

        void showTitleListPopupwindow();

        //void dimissTitleListPopupwindow();

        void showDatePicker();

        void showTimePicker();

        void showCommitError();
    }

    interface InvitationIModle{
    }

    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<View>{
        void setListPopupWindow(String );

    }
}
