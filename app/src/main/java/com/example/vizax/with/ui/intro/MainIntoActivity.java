package com.example.vizax.with.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.myhome.HomeActivity;
import com.example.vizax.with.util.SharedUtil;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by prj on 2016/10/4.
 */

public class MainIntoActivity extends IntroActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setFullscreen(false);
        super.onCreate(savedInstanceState);

        if(SharedUtil.getBoolean(App.instance,FieldConstant.isfirstlogin,true)){
            startHomeActivity();
        }

        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT);
        setButtonBackVisible(true);
        setButtonNextVisible(true);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        addSlide(new SimpleSlide.Builder()
                .title("模块")
                .description("各种模块，总有一款适合你")
                .image(R.drawable.art_canteen_intro1)
                .background(R.color.insist_mission_btn1)
                .backgroundDark(R.color.insist_mood_bg1)
                .scrollable(true)
                .buttonCtaLabel("跳过")
                .buttonCtaClickListener(v -> startHomeActivity())
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("发起邀约")
                .description("无兄弟，不篮球")
                .image(R.drawable.art_canteen_intro2)
                .background(R.color.insist_mission_btn2)
                .backgroundDark(R.color.insist_mood_bg2)
                .buttonCtaLabel("跳过")
                .buttonCtaClickListener(v -> startHomeActivity())
                .scrollable(true)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("坚持")
                .description("目标：每天吃20根鸡腿")
                .image(R.drawable.art_canteen_intro3)
                .background(R.color.insist_mission_btn3)
                .backgroundDark(R.color.insist_mood_bg3)
                .buttonCtaLabel("跳过")
                .buttonCtaClickListener(v -> startHomeActivity())
                .scrollable(true)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("邀约已满")
                .description("申请特批，邀约已满也能加")
                .image(R.drawable.art_plane)
                .background(R.color.insist_mission_btn4)
                .backgroundDark(R.color.insist_mood_bg4)
                .buttonCtaLabel("跳过")
                .buttonCtaClickListener(v -> startHomeActivity())
                .scrollable(true)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("配对")
                .description("可以选择只和萌妹子约会哦")
                .image(R.drawable.ic_launcher_web)
                .background(R.color.insist_mission_btn5)
                .backgroundDark(R.color.insist_mood_bg5)
                .buttonCtaLabel("打开首页面")
                .buttonCtaClickListener(v -> startHomeActivity())
                .scrollable(true)
                .build());
    }

    private void startHomeActivity(){
        Intent intent = new Intent(App.instance, HomeActivity.class);
        SharedUtil.putBoolean(App.instance,FieldConstant.isfirstlogin,false);
        startActivity(intent);
        finish();
    }
}
