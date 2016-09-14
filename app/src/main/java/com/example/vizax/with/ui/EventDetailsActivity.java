package com.example.vizax.with.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.adapter.EventDetailsRecyclerViewAdapter;
import com.example.vizax.with.adapter.MyFocusRecyclerViewAdapter;
import com.example.vizax.with.adapter.UserImgListecyclerViewAdapter;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class EventDetailsActivity extends SwipeBackActivity {
   private RecyclerView mRecyclerView;
    private EventDetailsRecyclerViewAdapter mAdapter;
    private UserImgListecyclerViewAdapter mUserImgAdapter;
    private  RecyclerView mUserImgRecyclerView;
    private String mKeyTrackingMode;
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        mRecyclerView = (RecyclerView) findViewById(R.id.event_details_recyclerview);
        mUserImgRecyclerView = (RecyclerView)findViewById(R.id.item_invitation_userimglist);
        mAdapter = new EventDetailsRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter);

        mUserImgAdapter = new UserImgListecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mUserImgRecyclerView.setLayoutManager(linearLayoutManager);
        mUserImgRecyclerView.setAdapter(mUserImgAdapter);

       // mKeyTrackingMode = getString(R.string.key_tracking_mode);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //saveTrackingMode(SwipeBackLayout.EDGE_LEFT);
    }

}
