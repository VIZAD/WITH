
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.vizax.with.R;

import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.Misson;
import com.example.vizax.with.bean.TaskMsg;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.Insist.decorators.EventDecorator;
import com.example.vizax.with.ui.Insist.decorators.EventDocDecorator;
import com.example.vizax.with.ui.Insist.decorators.HighlightWeekendsDecorator;
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
import java.util.Calendar;
import java.util.List;

import butterknife.*;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InsistActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener,OnDateSelectedListener,OnMonthChangedListener,InsistContact.View {
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> mList = new ArrayList<>();
    private ArrayList<String> mRemark_txt = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator mViewAnimator;
   // private int ripple = R.drawable.ripple_bg;
    private InsistPresenter mPresenter;
    private String INSIST = "签到";
    //Resources.getSystem().getColor(R.color.bg_calendar_1);
    private InsistColor mInsistColor;
    private ToDayDecorator toDayDecorator = new ToDayDecorator();
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private SharedPreferences sp;

    @BindView(R.id.calendarView)
    MaterialCalendarView mMaterialCalendarView;

    @BindView(R.id.insist_scrollView)
    ScrollView mScrVi;

    @BindView(R.id.title_id)
    TextView mTxtVi_title;

    @BindView(R.id.center_text_id)
    TextView mTxtVi_center_txt;

    @BindView(R.id.foot_text_id)
    TextView mTxtVi_foot_txt;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.left_drawer)
    LinearLayout mLinearLayout;

    @BindView(R.id.insist_baseToolBar)
    BaseToolBar mToolBar;

    @BindView(R.id.content_frame)
    View mView;

    @Override
    protected int initContentView() {
        return R.layout.activity_insist;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mInsistColor =  new InsistColor(this);
        contentFragment = ContentFragment.newInstance(mInsistColor.COLOR_CALENDER,mInsistColor.COLOR_CALENDER);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        mViewAnimator = new ViewAnimator(this, mList, contentFragment, mDrawerLayout, this);
        mMaterialCalendarView.setOnDateChangedListener(this);
        mMaterialCalendarView.setOnMonthChangedListener(this);
        mMaterialCalendarView.addDecorators(
                //new MySelectorDecorator(this),
                new HighlightWeekendsDecorator()
                //toDayDecorator
        );
        mPresenter = new InsistPresenter();
        mPresenter.attachView(this);
        setActionBar();
        createMenuList();
        mPresenter.getTask();
        //暂时使用假数据
        mPresenter.TaskMessages("2016-10-1 9:20:21","1");
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    private void createMenuList() {
        //侧滑ITEM的初始化
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_add);
        mList.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_add);
        mList.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_add);
        mList.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_add);
        mList.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_add);
        mList.add(menuItem4);
        System.out.println("setList");
    }

    private void setActionBar() {

        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolBar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mLinearLayout.removeAllViews();
                mLinearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && mLinearLayout.getChildCount() == 0)
                    mViewAnimator.showMenuContent();
                //System.out.println("drawview = "+drawerView);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(drawerToggle);

        mToolBar.setCenterViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mScrVi);
            }
        });
        mToolBar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsistActivity.this.finish();
            }
        });
        mToolBar.setRightViewOnClickListener(new View.OnClickListener() {
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
    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition , int color_cl,int color_mi,int color_md) {
        //判断颜色不一致则替换
        //this.res = this.res == R.drawable.blue_bg ? R.drawable.white_bg : R.drawable.blue_bg;
        //测试
        //this.color = this.color == color1?color2:color1;
        //

        int finalRadius = Math.max(mView.getWidth(), mView.getHeight());
        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(mView, 0, topPosition, 0, finalRadius);
        }
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(color_cl,color_mi);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        System.out.println("ScreenShotable color ="+color_md);
        mTxtVi_title.setTextColor(color_md);
        mTxtVi_center_txt.setTextColor(color_md);
        //mTxtVi_foot_txt.setTextColor(color_md);
        reset.run();

        return contentFragment;
    }



    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                //// TODO: 2016/09/16
                return replaceFragment(screenShotable, position,mInsistColor.COLOR1_CALENDER,mInsistColor.COLOR1_MISSION,mInsistColor.COLOR1_MOOD);
            case ContentFragment.BUILDING:
                return replaceFragment(screenShotable, position,mInsistColor.COLOR2_CALENDER,mInsistColor.COLOR2_MISSION,mInsistColor.COLOR2_MOOD);
            case ContentFragment.BOOK:
                return replaceFragment(screenShotable, position,mInsistColor.COLOR3_CALENDER,mInsistColor.COLOR3_MISSION,mInsistColor.COLOR3_MOOD);
            case ContentFragment.PAINT:
                return replaceFragment(screenShotable, position,mInsistColor.COLOR4_CALENDER,mInsistColor.COLOR4_MISSION,mInsistColor.COLOR4_MOOD);
            case ContentFragment.CASE:
                return replaceFragment(screenShotable, position,mInsistColor.COLOR5_CALENDER,mInsistColor.COLOR5_MISSION,mInsistColor.COLOR5_MOOD);
            default:
                return replaceFragment(screenShotable, position,mInsistColor.COLOR1_CALENDER,mInsistColor.COLOR1_MISSION,mInsistColor.COLOR1_MOOD);
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
        mLinearLayout.addView(view);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        toDayDecorator.setDate(date.getDate());
        if(mRemark_txt.size()>date.getDay()) {
            // TODO: 2016/09/16  
        }
        //mTxtVi_foot_txt.setText(mRemark_txt.get(date.getDay()));
    }
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        System.out.println(date.getDate());
        String cl = date.getYear()+"-"+(date.getMonth()+1);
        System.out.println("cl = "+cl);
        mPresenter.TaskMessages(cl,"1");
    }

    public void tick(){
        //this.ripple = this.ripple== R.drawable.ripple_bg ? R.drawable.rippled_bg:R.drawable.ripple_bg;
        //this.INSIST = this.INSIST.equals("签到")?"已签到":"签到";
        mToolBar.setRightIcon(R.drawable.back_ic);
        mToolBar.setRightViewEnable(false);
        System.out.println("onclick");
        toDayDecorator = new ToDayDecorator();
        runnable.run();
        mPresenter.JourPunch("1");
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
                mMaterialCalendarView.setCurrentDate(cl);
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


    @Override
    public void setClData(TaskMsg taskMsg) {
        mTxtVi_foot_txt.setText(taskMsg.getData().getTask().getTeskContent());
        setDays(taskMsg);
    }

    @Override
    public void setData(Misson misson) {
        mTxtVi_title.setText(misson.getData().getCurrTasks().get(0).getTitle().toString());
        mTxtVi_center_txt.setText(misson.getData().getCurrTasks().get(0).getContent().toString());
        sp = getSharedPreferences("mySp",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for(int i = 0; i < 5;i++) {
            editor.putInt("Item" + i, R.drawable.icn_add);
        }
        for (int j = 0; j<misson.getData().getCurrTasks().size(); j++) {
            switch (misson.getData().getCurrTasks().get(j).getTask_icon_type()) {
                case 1:editor.putInt("Item" + j, R.drawable.icn_1);
                    break;
                case 2:editor.putInt("Item" + j, R.drawable.icn_2);
                    break;
                case 3:editor.putInt("Item" + j, R.drawable.icn_3);
                    break;
                case 4:editor.putInt("Item" + j, R.drawable.icn_4);
                    break;
                case 5:editor.putInt("Item" + j, R.drawable.icn_5);
                    break;
            }
        }
        editor.commit();
    }

        protected  void setDays (TaskMsg taskMsg){
            Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.MONTH,0);
            int DayNum = 0;
            int DayNum_remark=0;
            calendar.set(CalendarDay.today().getYear(),CalendarDay.today().getMonth(),1);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            ArrayList<CalendarDay> dates_remark = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                if (i == taskMsg.getData().getCalendar().get(DayNum).getDay() - 1 && taskMsg.getData().getCalendar().get(DayNum).isJour_punch()) {
                    dates.add(day);
                    if(DayNum < taskMsg.getData().getCalendar().size() - 1) {
                        DayNum++;
                    }
                }
                if (i == taskMsg.getData().getCalendar().get(DayNum).getDay() - 1 && !taskMsg.getData().getCalendar().get(DayNum_remark).getRemark().equals("")) {
                    dates_remark.add(day);
                    mRemark_txt.add(taskMsg.getData().getCalendar().get(DayNum_remark).getRemark().toString());
                    if(DayNum_remark < taskMsg.getData().getCalendar().size() - 1) {
                        DayNum_remark++;
                    }
                }

                calendar.add(Calendar.DATE, 1);
            }
            mMaterialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
            mMaterialCalendarView.addDecorator(new EventDocDecorator(Color.RED, dates_remark));
        }




    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mMaterialCalendarView.addDecorators(
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
            mMaterialCalendarView.removeDecorators();
            mMaterialCalendarView.clearSelection();
            mMaterialCalendarView.setCurrentDate(calendarDay);
            mMaterialCalendarView.addDecorators(
                    //new MySelectorDecorator(this),
                    new HighlightWeekendsDecorator()
                    //toDayDecorator
            );
        }
    };


}
