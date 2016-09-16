
package com.example.vizax.with.ui.Insist;
import android.animation.Animator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;

import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.Insist.ContentFragment;
import com.example.vizax.with.ui.Insist.decorators.ToDayDecorator;
import com.example.vizax.with.ui.Insist.dialog.DateDialog;
import com.example.vizax.with.util.sidemenu.interfaces.Resourceble;
import com.example.vizax.with.util.sidemenu.interfaces.ScreenShotable;
import com.example.vizax.with.util.sidemenu.model.SlideMenuItem;
import com.example.vizax.with.util.sidemenu.util.ViewAnimator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.*;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InsistActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener,OnDateSelectedListener,OnMonthChangedListener,InsistContact.View {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
   // private int ripple = R.drawable.ripple_bg;
    private InsistPresenter mPresenter;
    private String INSIST = "签到";
    //Resources.getSystem().getColor(R.color.bg_calendar_1);
    private int year;
    private int color_calender ;
    private int color1_calender;
    private int color2_calender;
    private int color3_calender;
    private int color4_calender;
    private int color5_calender;
    private int color_mission;
    private int color1_mission;
    private int color2_mission;
    private int color3_mission;
    private int color4_mission;
    private int color5_mission;
    private LinearLayout linearLayout;
    private ToDayDecorator toDayDecorator = new ToDayDecorator();
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private SharedPreferences sp;

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.insist_scrollView)
    ScrollView scrVi;

//    @BindView(R.id.tick_btn_id)
//    Button tick_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insist_main);
        color_calender = getResources().getColor(R.color.bg_calendar_1);
        color_mission = getResources().getColor(R.color.btn_misson_1);
        contentFragment = ContentFragment.newInstance(color_calender,color1_mission);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator(this, list, contentFragment, mDrawerLayout, this);
        ButterKnife.bind(this);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        color1_calender = getResources().getColor(R.color.bg_calendar_1);
        color2_calender = getResources().getColor(R.color.bg_calendar_2);
        color3_calender = getResources().getColor(R.color.bg_calendar_3);
        color4_calender = getResources().getColor(R.color.bg_calendar_4);
        color5_calender = getResources().getColor(R.color.bg_calendar_5);
        color1_mission = getResources().getColor(R.color.btn_misson_1);
        color2_mission = getResources().getColor(R.color.btn_misson_2);
        color3_mission = getResources().getColor(R.color.btn_misson_3);
        color4_mission = getResources().getColor(R.color.btn_misson_4);
        color5_mission = getResources().getColor(R.color.btn_misson_5);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_add);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_add);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_add);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_add);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_add);
        list.add(menuItem4);
        System.out.println("setList");

        //模拟数据
        sp = getSharedPreferences("mySp",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Item0",R.drawable.icn_1);
        editor.putInt("Item1",R.drawable.icn_2);
        editor.putInt("Item2",R.drawable.icn_3);
        editor.putInt("Item3",R.drawable.icn_4);
        editor.putInt("Item4",R.drawable.icn_5);
        editor.commit();
        mPresenter = new InsistPresenter();
        mPresenter.attachView(this);
        mPresenter.getTaskCount();
    }


    private void setActionBar() {
        BaseToolBar toolbar = (BaseToolBar) findViewById(R.id.insist_baseToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
                //System.out.println("drawview = "+drawerView);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(drawerToggle);

        toolbar.setCenterViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(scrVi);
            }
        });
        toolbar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsistActivity.this.finish();
            }
        });
        toolbar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tick();
            }
        });
        //drawerToggle.syncState();


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //drawerToggle.onConfigurationChanged(newConfig);
    }
    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition , int color_cl,int color_mi) {
        //判断颜色不一致则替换
        //this.res = this.res == R.drawable.blue_bg ? R.drawable.white_bg : R.drawable.blue_bg;
        //测试
        //this.color = this.color == color1?color2:color1;
        //
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        }
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(color_cl,color_mi);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        System.out.println("ScreenShotable color ="+color_cl);
        reset.run();
        return contentFragment;
    }



    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return replaceFragment(screenShotable, position,color1_calender,color1_mission);
            case ContentFragment.BUILDING:
                return replaceFragment(screenShotable, position,color2_calender,color2_mission);
            case ContentFragment.BOOK:
                return replaceFragment(screenShotable, position,color3_calender,color3_mission);
            case ContentFragment.PAINT:
                return replaceFragment(screenShotable, position,color4_calender,color4_mission);
            case ContentFragment.CASE:
                return replaceFragment(screenShotable, position,color5_calender,color5_mission);
            default:
                return replaceFragment(screenShotable, position,color_calender,color1_mission);
        }
    }

    @Override
    public void disableHomeButton() {
        // getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        //getSupportActionBar().setHomeButtonEnabled(true);
        //drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        toDayDecorator.setDate(date.getDate());
    }
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        System.out.println(date.getDate());
    }

    public void tick(){
        //this.ripple = this.ripple== R.drawable.ripple_bg ? R.drawable.rippled_bg:R.drawable.ripple_bg;
        this.INSIST = this.INSIST.equals("签到")?"已签到":"签到";
//        tick_btn.setBackgroundResource(ripple);
//        tick_btn.setText(INSIST);
        System.out.println("onclick");
        toDayDecorator = new ToDayDecorator();
        runnable.run();

    }

    @OnClick(R.id.set_date_btn)
    void onTileSizeClicked() {
        DateSelect(this);
    }

    public void DateSelect(final Activity activity) {
        final DateDialog confirmDialog = new DateDialog(activity, "确定要退出吗?", "确认", "取消");
        confirmDialog.show();
        confirmDialog.setClicklistener(new DateDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                System.out.println("year="+confirmDialog.yearPicker.getValue()+"month="+confirmDialog.monthPicker.getValue());
                int year;
                int month;
                CalendarDay cl;

                year = confirmDialog.yearPicker.getValue();
                month = confirmDialog.monthPicker.getValue();
                cl = CalendarDay.from(year,month-1,1);
                widget.setCurrentDate(cl);
                // TODO Auto-generated method stub
                confirmDialog.dismiss();
                //toUserHome(context);
            }

            @Override
            public void doCancel() {
                // TODO Auto-generated method stub
                confirmDialog.dismiss();
            }
        });
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            widget.addDecorators(
                    //new MySelectorDecorator(this),
                    //new HighlightWeekendsDecorator(),
                    toDayDecorator

            );

        }
    };
    Runnable reset = new Runnable() {
        @Override
        public void run() {
            CalendarDay calendarDay = CalendarDay.today();
            widget.removeDecorators();
            widget.clearSelection();
            widget.setCurrentDate(calendarDay);
        }
    };


}
