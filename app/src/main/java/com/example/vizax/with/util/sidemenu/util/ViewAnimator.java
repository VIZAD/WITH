package com.example.vizax.with.util.sidemenu.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import com.example.vizax.with.ui.Insist.ContentFragment;
import com.example.vizax.with.ui.Insist.InsistPresenter;
import com.example.vizax.with.ui.Insist.dialog.AddItemDialog;
import com.example.vizax.with.util.sidemenu.animation.FlipAnimation;
import com.example.vizax.with.util.sidemenu.interfaces.Resourceble;
import com.example.vizax.with.util.sidemenu.interfaces.ScreenShotable;
import com.example.vizax.with.util.sidemenu.model.SlideMenuItem;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.DraweeView;
import com.example.vizax.with.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin on 12.01.2015.
 */
public class ViewAnimator<T extends Resourceble> {
    private final int ANIMATION_DURATION = 175;
    public static final int CIRCULAR_REVEAL_ANIMATION_DURATION = 500;

    private Activity activity;
    private List<T> list;
    private List<SlideMenuItem> mList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private int mOnLongClick = 0;
    private ScreenShotable screenShotable;
    private DrawerLayout drawerLayout;
    private ViewAnimatorListener animatorListener;
    private InsistPresenter mPresenter;
    private SharedPreferences sp;

    public ViewAnimator(Activity activity, List<T> items, ScreenShotable screenShotable, final DrawerLayout drawerLayout, ViewAnimatorListener animatorListener){
        this.activity = activity;
        this.list = items;
        this.screenShotable = screenShotable;
        this.drawerLayout = drawerLayout;
        this.animatorListener = animatorListener;
        init();
        System.out.println("view");
    }

    public void init() {
        sp = activity.getSharedPreferences("mySp",Activity.MODE_PRIVATE);
        SlideMenuItem menuItem1 = new SlideMenuItem(ContentFragment.CLOSE, sp.getInt("Item0", R.drawable.icn_add));
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BUILDING, sp.getInt("Item1", R.drawable.icn_add));
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.BOOK, sp.getInt("Item2", R.drawable.icn_add));
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.PAINT, sp.getInt("Item3", R.drawable.icn_add));
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.CASE, sp.getInt("Item4", R.drawable.icn_add));
        mList.add(menuItem1);
        mList.add(menuItem2);
        mList.add(menuItem3);
        mList.add(menuItem4);
        mList.add(menuItem5);
    }


    public void showMenuContent() {
        setViewsClickable(false);
        viewList.clear();
        double size = list.size();
        mOnLongClick = 0;
        for (int i = 0; i < size; i++) {
            View viewMenu = activity.getLayoutInflater().inflate(R.layout.menu_list_item, null);
            final int finalI = i;
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] location = {0, 0};
                    v.getLocationOnScreen(location);
                    //System.out.println("图片Resources = "+((DraweeView) viewMenu.findViewById(R.id.menu_item_image)).getResources()+" close = "+R.drawable.icn_close);
                    if(list.get(finalI).getImageRes()!= R.drawable.icn_close&&list.get(finalI).getImageRes()!= R.drawable.icn_add) {
                        switchItem(list.get(finalI), location[1] + v.getHeight() / 2);

                        System.out.println("click item");
                    }
                    else if (list.get(finalI).getImageRes()== R.drawable.icn_add) {
                        AddIcon(activity,viewMenu,finalI);
                        System.out.println("click plus");
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("确认删除吗？");
                        builder.setTitle("提示");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.remove("Item"+finalI);
                                editor.commit();
                                ((DraweeView) viewMenu.findViewById(R.id.menu_item_image)).setImageResource(R.drawable.icn_add);
                                SlideMenuItem add = new SlideMenuItem(list.get(finalI).getName(), R.drawable.icn_add);
                                mList.remove(finalI);
                                mList.add(finalI, (SlideMenuItem) add);
                                list.set(finalI,(T)add);
                                dialog.dismiss();

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }
            });
            //长按事件
            viewMenu.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int[] location = {0, 0};
                    v.getLocationOnScreen(location);
                    //switchItem(list.get(finalI), location[1] + v.getHeight() / 2);
                    if(list.get(finalI).getImageRes()!= R.drawable.icn_close) {
                        //((DraweeView) v.findViewById(R.id.menu_item_image)).setImageResource(R.drawable.icn_close);
                        SlideMenuItem close = new SlideMenuItem(list.get(finalI).getName(), R.drawable.icn_close);
                        list.set(finalI, (T) close);
                        mOnLongClick = 1;
                    }
                    else {
                        //((DraweeView) v.findViewById(R.id.menu_item_image)).setImageResource(R.drawable.icn_close);
                        SlideMenuItem close = new SlideMenuItem(list.get(finalI).getName(),mList.get(finalI).getImageRes());
                        list.set(finalI, (T) close);
                        mOnLongClick = 0;

                    }
                    System.out.println("long click");
                    playFlipAnimation((DraweeView) v.findViewById(R.id.menu_item_image),finalI);

                    return true;
                }
            });


            ((DraweeView) viewMenu.findViewById(R.id.menu_item_image)).setImageResource(mList.get(i).getImageRes());
            SlideMenuItem close = new SlideMenuItem(list.get(finalI).getName(),mList.get(finalI).getImageRes());
            list.set(finalI,(T)close);
            System.out.println("RC_list =" +(mList.get(i).getImageRes()));
            viewMenu.setVisibility(View.GONE);
            viewMenu.setEnabled(false);
            viewList.add(viewMenu);
            animatorListener.addViewToContainer(viewMenu);
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }
                    if (position == viewList.size() - 1) {
                        screenShotable.takeScreenShot();
                        setViewsClickable(true);
                    }
                }
            }, (long) delay);
        }


    }

    private void hideMenuContent() {
        System.out.println("HD");
        setViewsClickable(false);
        double size = list.size();
        for (int i = list.size(); i >= 0; i--) {
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateHideView((int) position);
                    }
                }
            }, (long) delay);
        }

    }

    private void setViewsClickable(boolean clickable) {
        animatorListener.disableHomeButton();
        for (View view : viewList) {
            view.setEnabled(clickable);
        }
    }

    private void animateView(int position) {
        final View view = viewList.get(position);
        view.setVisibility(View.VISIBLE);
        FlipAnimation rotation =
                new FlipAnimation(90, 0, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }



    private void animateHideView(final int position) {
        final View view = viewList.get(position);
        FlipAnimation rotation =
                new FlipAnimation(0, 90, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
                if (position == viewList.size() - 1) {
                    animatorListener.enableHomeButton();
                    drawerLayout.closeDrawers();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }





    //翻牌效果
    private void playFlipAnimation(DraweeView draweeView,int finalI) {
        AnimatorSet animatorSetOut = (AnimatorSet) AnimatorInflater
                .loadAnimator(this.activity, R.animator.card_flip_left_out);

        final AnimatorSet animatorSetIn = (AnimatorSet) AnimatorInflater
                .loadAnimator(this.activity, R.animator.card_flip_left_enter );

        animatorSetOut.setTarget(draweeView);
        animatorSetIn.setTarget(draweeView);

        animatorSetOut.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {// 翻转90度之后，换图

                if (mOnLongClick==1){
                    draweeView.setImageResource(R.drawable.icn_close);
                }
                else {
                    draweeView.setImageResource(mList.get(finalI).getImageRes());
                }

                animatorSetIn.start();
            }
        });

        animatorSetIn.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO
            }
        });
        animatorSetOut.start();
    }

    //判断
    private void switchItem(Resourceble slideMenuItem, int topPosition) {
        this.screenShotable = animatorListener.onSwitch(slideMenuItem, screenShotable, topPosition);
        hideMenuContent();

    }

    /**
     * 弹出添加任务dialog
     * @param activity
     */
    public int AddIcon(final Activity activity,View viewMenu,int finalI) {
        final AddItemDialog confirmDialog = new AddItemDialog(activity,finalI, "添加任务", "确认", "取消");
        confirmDialog.show();
        confirmDialog.setClicklistener(new AddItemDialog.ClickListenerInterface() {
            @Override
            public int doConfirm() {
                ((DraweeView) viewMenu.findViewById(R.id.menu_item_image)).setImageResource(sp.getInt("Item"+finalI, R.drawable.icn_add));
                SlideMenuItem add = new SlideMenuItem(list.get(finalI).getName(),sp.getInt("Item"+finalI, R.drawable.icn_add));
                mList.set(finalI, (SlideMenuItem) add);
                list.set(finalI,(T)add);
                confirmDialog.dismiss();
                mPresenter = new InsistPresenter();
                mPresenter.createTask(confirmDialog.title,confirmDialog.content,finalI);
                return 0;
            }

            @Override
            public int doCancel() {
                confirmDialog.dismiss();
                return 0;
            }
        });
        return 0;
    }

    public interface ViewAnimatorListener {

        public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position);

        public void disableHomeButton();

        public void enableHomeButton();

        public void addViewToContainer(View view);

    }
}
