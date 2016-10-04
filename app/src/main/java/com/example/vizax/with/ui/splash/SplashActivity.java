package com.example.vizax.with.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.vizax.with.ui.intro.MainIntoActivity;
import com.example.vizax.with.util.AppManager;
import com.example.vizax.with.util.ResourceUtil;
import com.example.vizax.with.util.StatusBarUtil;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;

/**
 * Created by prj on 2016/10/4.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);

        Intent intent = new Intent(this,MainIntoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
