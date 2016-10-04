package com.example.vizax.with.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vizax.with.ui.intro.MainIntoActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;

/**
 * Created by prj on 2016/10/4.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this,MainIntoActivity.class);
        startActivity(intent);
        finish();
    }
}
