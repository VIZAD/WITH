package com.example.vizax.with.ui.Insist;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;

/**
 * Created by VIZAX on 2016/09/16.
 */

public interface InsistContact {


    interface View extends BaseView {



    }

    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<InsistContact.View> {

        void  getTaskCount();
    }
}
