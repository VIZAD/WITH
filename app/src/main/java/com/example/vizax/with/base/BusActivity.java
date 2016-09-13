package com.example.vizax.with.base;

import rx.Subscription;

/**
 * Created by prj on 2016/8/19.
 */

public abstract class BusActivity extends BaseActivity{

    public Subscription rxSubscription;

    @Override protected void onDestroy() {
        super.onDestroy();
        if(rxSubscription!=null&&rxSubscription.isUnsubscribed()){
            rxSubscription.unsubscribe();
        }
    }
}
