package com.example.vizax.with.ui.Insist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vizax.with.R;

import java.util.List;

/**
 * Created by VIZAX on 2016/09/13.
 */

public class AddDateRcyViAdapter extends BaseQuickAdapter<String> {

        int selected = -1;
        int finalI = -1;
        String url;
       SharedPreferences sp;

        public AddDateRcyViAdapter(List<String> list,Context context) {
            super(R.layout.add_item_dialog_item, list);
            sp = context.getSharedPreferences("mySp", Activity.MODE_PRIVATE);
        }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        //SimpleDraweeView view = baseViewHolder.getView(R.id.pic);
        //view.setImageURI(Uri.parse(s));
        baseViewHolder.setImageResource(R.id.pic,Integer.parseInt((Uri.parse(s).toString())));
//            baseViewHolder.setBackgroundRes(R.id.pic, R.drawable.item_up);
            baseViewHolder.setBackgroundRes(R.id.pic, R.drawable.buttonshape);
            if(s.equals(url)) {
                baseViewHolder.setBackgroundRes(R.id.pic, R.drawable.buttonshape_down);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("Item" + finalI, Integer.parseInt((Uri.parse(s).toString())));
                editor.commit();
                System.out.println("draw = "+(Uri.parse(s).toString()));
            }
        baseViewHolder.setOnClickListener(R.id.pic, new OnItemChildClickListener());

    }

    public void reset(String url, int position, int finalI) {
        this.url = url;
        selected = position;
        this.finalI = finalI;
        notifyDataSetChanged();
    }




}
