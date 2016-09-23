
package com.example.vizax.with.ui.Insist;
import android.animation.Animator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.vizax.with.ui.Insist.decorators.OneDayDecorator;
import com.example.vizax.with.ui.Insist.decorators.ToDayDecorator;
import com.example.vizax.with.ui.Insist.dialog.DateDialog;
import com.example.vizax.with.util.AnimationUtil;
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
import io.codetail.widget.RevealLinearLayout;

public class InsistActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener,OnDateSelectedListener,OnMonthChangedListener,InsistContact.View {
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> mList = new ArrayList<>();
    private ArrayList<String> mRemark_txt = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator mViewAnimator;
    private InsistPresenter mPresenter;
    private String INSIST = "签到";
    private InsistColor mInsistColor;
    private String mSelectedDate;
    private String mSelectedMonth;
    private String mSelectedCase = "1";
    private String TaskId = null;
    private ToDayDecorator toDayDecorator = new ToDayDecorator();
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private Misson mMisson;
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
    EditText mTxtVi_foot_txt;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.left_drawer)
    LinearLayout mLinearLayout;


    @BindView(R.id.toolbar)
    BaseToolBar mToolBar;

    @BindView(R.id.content_frame)
    View mView;

    @BindView(R.id.insist_top_rll)
    RevealLinearLayout mRevaelLinearLayout;

    @BindView(R.id.foot_bg_ll)
    LinearLayout mLinearLayout_foot;

    @BindView(R.id.insist_edit_btn)
    Button insist_edit_btn;

    @Override
    protected int initContentView() {
        return R.layout.insist_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mInsistColor =  new InsistColor(this);
        mToolBar.setCenterText("坚持");
        mToolBar.setLeftIcon(getResources().getDrawable(R.drawable.back_ic));
        mToolBar.setRightIcon(getResources().getDrawable(R.drawable.calendar_unselect));
        contentFragment = ContentFragment.newInstance(mInsistColor.COLOR_CALENDER,mInsistColor.COLOR_CALENDER);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mLinearLayout.setOnClickListener(v -> mDrawerLayout.closeDrawers());
        mViewAnimator = new ViewAnimator(this, mList, contentFragment, mDrawerLayout, this);
        mMaterialCalendarView.setOnDateChangedListener(this);
        mMaterialCalendarView.setOnMonthChangedListener(this);
        mMaterialCalendarView.addDecorators(
                //new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                new OneDayDecorator()
                //toDayDecorator
        );
        setActionBar();
        mPresenter = new InsistPresenter();
        mPresenter.attachView(this);
        createMenuList();
        mPresenter.getTask();
        mTxtVi_title.setFocusable(true);
        mTxtVi_title.setFocusableInTouchMode(true);
        //初始化界面
        initContentView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AnimationUtil.showCircularReveal(mView,2,1000);
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;     // 屏幕宽度（像素）
//        int height = metric.heightPixels;   // 屏幕高度（像素）
//        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240)
//        System.out.println("width = "+width+" height = "+height+" density = "+density + "densityDpi ="+densityDpi);
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

        //setSupportActionBar(mToolBar);
        //getSupportActionBar().setHomeButtonEnabled(true);
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
                if (slideOffset > 0.6 && mLinearLayout.getChildCount() == 0) {
                    int heights =getHeights();
                    int length = mRevaelLinearLayout.getHeight()-mToolBar.getHeight()-mLinearLayout_foot.getHeight()-(heights*5);
                    mViewAnimator.showMenuContent(heights,length);
                }
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
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mScrVi);
            }
        });
        mToolBar.setLeftViewOnClickListener(view -> InsistActivity.this.finish());
        mToolBar.setRightViewOnClickListener(view -> tick());

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
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
            findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
            animator.start();
        } else {
            AnimationUtil.showCircularReveal(mView, 2,500);
            findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
            /*animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
            findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
            animator.start();*/
        }

        ContentFragment contentFragment = ContentFragment.newInstance(color_cl,color_mi);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        System.out.println("ScreenShotable color ="+color_md);
        mTxtVi_title.setTextColor(color_md);
        mTxtVi_center_txt.setTextColor(color_md);
        //mTxtVi_foot_txt.setTextColor(color_md);
        mTxtVi_title.setText(mMisson.getData().getCurrTasks().get(Integer.parseInt(mSelectedCase)-1).getTitle().toString());
        mTxtVi_center_txt.setText(mMisson.getData().getCurrTasks().get(Integer.parseInt(mSelectedCase)-1).getContent().toString());

        reset.run();

        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                //// TODO: 2016/09/16
                mSelectedCase = "1";
                TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(0).getTaskId());
                return replaceFragment(screenShotable, position,mInsistColor.COLOR1_CALENDER,mInsistColor.COLOR1_MISSION,mInsistColor.COLOR1_MOOD);
            case ContentFragment.BUILDING:
                mSelectedCase = "2";
                TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(1).getTaskId());
                return replaceFragment(screenShotable, position,mInsistColor.COLOR2_CALENDER,mInsistColor.COLOR2_MISSION,mInsistColor.COLOR2_MOOD);
            case ContentFragment.BOOK:
                mSelectedCase = "3";
                TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(2).getTaskId());
                return replaceFragment(screenShotable, position,mInsistColor.COLOR3_CALENDER,mInsistColor.COLOR3_MISSION,mInsistColor.COLOR3_MOOD);
            case ContentFragment.PAINT:
                mSelectedCase = "4";
                TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(3).getTaskId());
                return replaceFragment(screenShotable, position,mInsistColor.COLOR4_CALENDER,mInsistColor.COLOR4_MISSION,mInsistColor.COLOR4_MOOD);
            case ContentFragment.CASE:
                mSelectedCase = "5";
                TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(4).getTaskId());
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
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth()+1);
        if(date.getMonth()<9) {
            month = "0"+month;
        }
        String day = String.valueOf(date.getDay());
        if(date.getDay()<9) {
            day = "0"+day;
        }
        mSelectedDate = year+"-"+month+"-"+day;
        mSelectedMonth = year+"-"+month;
        System.out.println("date = "+year+"-"+month+"-"+day);
        mTxtVi_foot_txt.setText(mRemark_txt.get(date.getDay()-1));


    }
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        System.out.println(date.getDate());
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth()+1);
        if(date.getMonth()<9) {
            month = "0"+month;
        }
        mSelectedMonth = year+"-"+month;
        System.out.println("Task Id = "+TaskId);
        mPresenter.TaskMessages(year+"-"+month,TaskId);
    }

    public void tick(){
        //this.ripple = this.ripple== R.drawable.ripple_bg ? R.drawable.rippled_bg:R.drawable.ripple_bg;
        //this.INSIST = this.INSIST.equals("签到")?"已签到":"签到";
        mToolBar.setRightIcon(R.drawable.signed);
        mToolBar.setRightViewEnable(false);
        System.out.println("onclick");
        toDayDecorator = new ToDayDecorator();
        runnable.run();
        mPresenter.JourPunch(TaskId);
    }

    public int getHeights() {
        Log.i("ToorBarW",mToolBar.getHeight()+"");
        Log.i("LinearLayoutW",mLinearLayout_foot.getHeight()+"");
        Log.i("RevaelLinearLayoutW", mRevaelLinearLayout.getWidth()+"");
        int heights = (mRevaelLinearLayout.getHeight()-mToolBar.getHeight()-mLinearLayout_foot.getHeight())/5;
        Log.i("height",heights+"");
        Log.i("length",mRevaelLinearLayout.getHeight()-mToolBar.getHeight()-mLinearLayout_foot.getHeight()+"");
        Log.i("Linner",mLinearLayout.getHeight()+"");

        return heights;
    }

    public void DateSelect(final Activity activity) {
        final DateDialog confirmDialog = new DateDialog(activity, "确定吗?", "确认", "取消");
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
        //mTxtVi_foot_txt.setText(taskMsg.getData().getTask().getTeskContent());
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
            editor.commit();
        }
        for (int j = 0; j<misson.getData().getCurrTasks().size(); j++) {
            switch (misson.getData().getCurrTasks().get(j).getTask_icon_type()) {
                case 0:editor.putInt("Item" + j, R.drawable.icn_1);
                    editor.putInt("TaskId" + j, misson.getData().getCurrTasks().get(j).getTaskId());
                    break;
                case 1:editor.putInt("Item" + j, R.drawable.icn_2);
                    editor.putInt("TaskId" + j,  misson.getData().getCurrTasks().get(j).getTaskId());
                    break;
                case 2:editor.putInt("Item" + j, R.drawable.icn_3);
                    editor.putInt("TaskId" + j,  misson.getData().getCurrTasks().get(j).getTaskId());
                    break;
                case 3:editor.putInt("Item" + j, R.drawable.icn_4);
                    editor.putInt("TaskId" + j,  misson.getData().getCurrTasks().get(j).getTaskId());
                    break;
                case 4:editor.putInt("Item" + j, R.drawable.icn_5);
                    editor.putInt("TaskId" + j,  misson.getData().getCurrTasks().get(j).getTaskId());
                    break;
            }
            System.out.println("misson icon = "+misson.getData().getCurrTasks().get(j).getTask_icon_type());
        }
        editor.commit();
        CalendarDay today = CalendarDay.today();
        String year = String.valueOf(today.getYear());
        String month = String.valueOf(today.getMonth()+1);
        if(today.getMonth()<9) {
            month = "0"+month;
        }
        mPresenter.TaskMessages(year+"-"+month, String.valueOf(misson.getData().getCurrTasks().get(0).getTaskId()));
        mMisson = misson;
        TaskId = String.valueOf(mMisson.getData().getCurrTasks().get(0).getTaskId());
        mSelectedMonth = year+"-"+month;
        System.out.println("set data");
    }

    protected  void setDays (TaskMsg taskMsg){
        mRemark_txt = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int DayNum = 0;
        int checked = 0;
        calendar.set(CalendarDay.today().getYear(),CalendarDay.today().getMonth(),1);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        ArrayList<CalendarDay> dates_remark = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            if (i == taskMsg.getData().getCalendar().get(DayNum).getDay() - 1 && taskMsg.getData().getCalendar().get(DayNum).isJour_punch()) {
                dates.add(day);
                mToolBar.setRightIcon(R.drawable.signed);
                mToolBar.setRightViewEnable(false);
                checked = 1;
            }
            if (i == taskMsg.getData().getCalendar().get(DayNum).getDay() - 1 && !taskMsg.getData().getCalendar().get(DayNum).getRemark().equals("")) {
                dates_remark.add(day);
                mRemark_txt.add(taskMsg.getData().getCalendar().get(DayNum).getRemark().toString());
                System.out.println("remark ="+taskMsg.getData().getCalendar().get(DayNum).getRemark().toString());
                checked = 2;
            }
            else {
                mRemark_txt.add("");
                System.out.println("没有设定的日期为"+(i+1));
                System.out.println("Now remark"+taskMsg.getData().getCalendar().get(DayNum).getRemark().equals(""));
            }
            if(DayNum < taskMsg.getData().getCalendar().size() - 1 && checked!=0) {
                System.out.println("num ="+DayNum);
                DayNum++;
                checked = 0;
            }
            calendar.add(Calendar.DATE, 1);
        }

        mMaterialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
        mMaterialCalendarView.addDecorator(new EventDocDecorator(Color.RED, dates_remark));
    }

    @OnClick(R.id.set_date_btn)
    void onTileSizeClicked() {
        DateSelect(this);
    }

    @OnClick(R.id.insist_edit_btn)
    void postEditMsg() {
        mPresenter.JourEdit(TaskId,mSelectedDate,mTxtVi_foot_txt.getText().toString());
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
                        new HighlightWeekendsDecorator(),
                        new OneDayDecorator()
                        //toDayDecorator
                );
                mToolBar.setRightIcon(R.drawable.calendar_unselect);
                mToolBar.setRightViewEnable(true);
            System.out.println("TaskId = "+TaskId);
            mPresenter.TaskMessages(mSelectedMonth, TaskId);
        }
    };


}
