package com.example.vizax.with.base;

import android.support.annotation.NonNull;

/**
 * Created by prj on 2016/9/13.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T View);

    void detachView();
}
