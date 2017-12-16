package com.example.user.annoy_talk.ui.Select_peple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.ui.First_Fragment.F_Recycler_item;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-16.
 */

public class Select_adapter extends RecyclerView.Adapter<Select_adapter.Viewholder>{
    private Context context;
    private ArrayList<F_Recycler_item> items;

    public Select_adapter(Context context, ArrayList<F_Recycler_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_recycle_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
