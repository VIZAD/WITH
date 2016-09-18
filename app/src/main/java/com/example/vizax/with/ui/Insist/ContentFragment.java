package com.example.vizax.with.ui.Insist;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.vizax.with.R;
import com.example.vizax.with.util.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private View containerView;
    protected ImageView mImageView;
    protected LinearLayout mLinearLayout_head;
    protected LinearLayout mLinearLayout_foot;
    protected int mColor_cl;
    protected int mColor_mi;
    private Bitmap mBitmap;

    public static ContentFragment newInstance(int color_cl,int color_mi) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color_cl", color_cl);
        bundle.putInt("color_mi", color_mi);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res = getArguments().getInt(Integer.class.getName());
        mColor_cl = getArguments().getInt("color_cl");
        mColor_mi = getArguments().getInt("color_mi");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insist, container, false);

//        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
//        mImageView.setImageResource(res);
        mLinearLayout_head = (LinearLayout) rootView.findViewById(R.id.head_linear);
        mLinearLayout_head.setBackgroundColor(mColor_cl);
        mLinearLayout_foot = (LinearLayout) rootView.findViewById(R.id.foot_linear);
        mLinearLayout_foot.setBackgroundColor(mColor_mi);
        System.out.println("create view color_cl = "+mColor_cl+" color_mi = " +mColor_mi);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.mBitmap = bitmap;
                System.out.println("takeScreenShot");
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }
}

