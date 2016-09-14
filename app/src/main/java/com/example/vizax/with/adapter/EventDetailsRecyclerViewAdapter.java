package com.example.vizax.with.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vizax.with.R;

/**
 * Created by Young on 2016/9/13.
 */
public class EventDetailsRecyclerViewAdapter extends RecyclerView.Adapter<EventDetailsRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    public EventDetailsRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public EventDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_applicants, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final EventDetailsRecyclerViewAdapter.MyViewHolder holder, final int position) {
                    if(position == 0){
                        holder.faqiren.setVisibility(View.VISIBLE);
                        holder.user.setVisibility(View.VISIBLE);
                    }else {
                        holder.useritem.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                if(holder.remove.getVisibility() == View.GONE)
                                holder.remove.setVisibility(View.VISIBLE);
                                else
                                    holder.remove.setVisibility(View.GONE);
                                return true;
                            }
                        });
                    }


    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView faqiren,user;
        ImageView remove;
        RelativeLayout useritem;
        public MyViewHolder(View itemView) {
            super(itemView);
            faqiren = (TextView) itemView.findViewById(R.id.item_applicants_faqirendetail);
            user = (TextView) itemView.findViewById(R.id.item_applicants_userdetail);
            remove = (ImageView) itemView.findViewById(R.id.tiren);
            useritem = (RelativeLayout) itemView.findViewById(R.id.item_applicants_relativelayout);
        }
    }


}
