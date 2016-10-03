package com.example.vizax.with.ui.mymessage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.MyMessageBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.myconcern.SpaceItemDecoration;
import com.example.vizax.with.util.SharedUtil;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xuhai on 2016/9/23.
 */

public class MyMessageActivity extends BaseActivity implements MyMessageContact.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.swipe_menu_recyclerview)
    SwipeMenuRecyclerView mMessageView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private TextView mDialogName;
    private TextView mDialogType;
    private TextView mDialogTitle;
    private TextView mDialogTime;
    private TextView mDialogPlace;
    private TextView mDialogCurrNumber;
    private TextView mDialogTotalNumber;

    private MyMessagePresenter mMessagePresenter;
    private LinearLayoutManager mManager;
    private int lastId = 2;
    private List<MyMessageBean.DataBean> mMessageList;
    private MyMessageAdapter mMessageAdapter;

    private View view;

    @Override
    protected int initContentView() {
        return R.layout.activity_my_message;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);

        //设置ToolBar
        toolbar.setCenterText(getResources().getString(R.string.my_news_title));
        toolbar.setLeftIcon(R.drawable.ic_keyboard_arrow_left);

        //新建Presenter
        mMessagePresenter = new MyMessagePresenter(getApplicationContext());
        mMessagePresenter.attachView(this);
        //设置RecyclerView
        mManager = new LinearLayoutManager(this);
        mMessageView.setLayoutManager(mManager);

        //HasFixedSize可提高性能
        mMessageView.setHasFixedSize(true);
        //设置Item默认动画
        mMessageView.setItemAnimator(new DefaultItemAnimator());

        //设置Item间距
        mMessageView.addItemDecoration(new SpaceItemDecoration(30));

        //设置菜单创建器(侧滑删除)
        mMessageView.setSwipeMenuCreator(swipeMenuCreator);

        //设置侧滑菜单点击事件
        mMessageView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);


        //初始化适配器
        mMessageList = new ArrayList<>();
        mMessageAdapter = new MyMessageAdapter(mMessageList);
        mMessageAdapter.setOnItemClickListener(itemClickListener);//Item点击事件监听
        mMessageView.setAdapter(mMessageAdapter);

        //加载数据
        mMessagePresenter.loadMessageData(SharedUtil.getString(getBaseContext(),FieldConstant.token),0,20);

        view = View.inflate(this, R.layout.dialog_my_message, null);

        //刷新监听
        swipeLayout.setOnRefreshListener(refreshListener);

        //加载监听
        mMessageView.addOnScrollListener(onScrollListener);
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mMessageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(false);

                }
            },2000);
        }
    };

    //上拉加载
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {
                //Toast.makeText(MyMessageActivity.this, "已到底部，上拉加载", Toast.LENGTH_LONG).show();
                mMessageAdapter.notifyDataSetChanged();
            }
        }
    };

    //创建菜单
    private SwipeMenuCreator swipeMenuCreator = new  SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext())
                    .setBackgroundDrawable(R.drawable.message_selector)
                    .setImage(R.drawable.message_delete)
                    .setWidth(dp2px(90))
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    //侧滑删除事件
    private OnSwipeMenuItemClickListener swipeMenuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            if (menuPosition == 0) {
                mMessagePresenter.deleteMessage(mMessageAdapter,adapterPosition,mMessageList.get(adapterPosition).getMessageId());
            }
        }
    };

    //点击Item弹出模态窗
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            mMessagePresenter.readMessage(mMessageAdapter,position,mMessageList.get(position).getMessageId());
            //View view = View.inflate(MyMessageActivity.this,R.layout.dialog_my_message,null);
            MaterialDialog dialog = null;
            MaterialDialog.Builder builder = new MaterialDialog.Builder(MyMessageActivity.this);
            builder.customView(view,true);
            builder.neutralText("取消");
            if (mMessageAdapter.getmDatas().get(position).getMessageType()==FieldConstant.MESSAGE_TYPE_APPLY) {
                builder.positiveText("批准");
                builder.neutralColorRes(R.color.gray);
                builder.negativeText("拒绝");
                builder.negativeColorRes(R.color.red);
                builder.onPositive((dialog1, which) -> {
                    mMessagePresenter.agreeMessage(mMessageAdapter, position, mMessageList.get(position).getMessageId());
                })
                 .onNegative((dialog1, which) -> {
                            mMessagePresenter.rejectMessage(mMessageAdapter, position, mMessageList.get(position).getMessageId());
                 });
            }
            dialog = builder.build();
            dialog.show();

            initDatas();
            mDialogName.setText(mMessageList.get(position).getName());
            mDialogTitle.setText(mMessageList.get(position).getInvitationTitle());

            Date date = new Date(mMessageList.get(position).getInvitationTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mDialogTime.setText(sdf.format(date));

            mDialogPlace.setText(mMessageList.get(position).getInvitationPlace());
            //mDialogCurrNumber.setText(mMessageList.get(position).getInvitationCurrNumber());
            //mDialogTotalNumber.setText(mMessageList.get(position).getInvitationTotalNumber());

            switch (mMessageList.get(position).getMessageType()) {
                case 0:
                    mDialogType.setText("【特批信息】");
                    break;
                case 1:
                    mDialogType.setText("【新增活动】");
                    break;
                case 2:
                    mDialogType.setText("【退出信息】");
                    break;
                case 3:
                    mDialogType.setText("【申请成功】");
                    break;
                case 4:
                    mDialogType.setText("【申请失败】");
                    break;
                case 5:
                    mDialogType.setText("【活动解散】");
                    break;
                default:
                    break;
            }

            Toast.makeText(MyMessageActivity.this,mMessageList.get(position).getName(),Toast.LENGTH_LONG).show();


        }
    };

    private void initDatas() {
        mDialogName = (TextView) view.findViewById(R.id.dialog_message_name);
        mDialogType = (TextView) view.findViewById(R.id.dialog_message_type);
        mDialogTitle = (TextView) view.findViewById(R.id.dialog_message_title);
        mDialogTime = (TextView) view.findViewById(R.id.dialog_message_invitation_time);
        mDialogPlace = (TextView) view.findViewById(R.id.dialog_message_place);
        mDialogCurrNumber = (TextView) view.findViewById(R.id.dialog_message_curr_number);
        mDialogTotalNumber = (TextView) view.findViewById(R.id.dialog_message_total_number);
    }

    //px转dp
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    public void loadDatas(List<MyMessageBean.DataBean> mMessageList,int lastId) {
        this.mMessageList = mMessageList;
        mMessageAdapter.setmDatas(mMessageList);
        mMessageAdapter.notifyDataSetChanged();
        this.lastId = lastId;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMessagePresenter.detachView();
    }
}
