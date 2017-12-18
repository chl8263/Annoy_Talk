package com.example.user.annoy_talk.ui.Select_peple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.annoy_talk.R;

import java.util.ArrayList;

/**
 * Created by Note on 2017-12-18.
 */

public class Selected_adapter extends RecyclerView.Adapter<Selected_adapter.selectedViewHolder>{
    private Context context;
    private ArrayList<Selected_item> items;

    public Selected_adapter(Context context, ArrayList<Selected_item> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public selectedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_item, parent, false);
        return new selectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(selectedViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class selectedViewHolder extends RecyclerView.ViewHolder{

        public TextView name ;

        public selectedViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.selected_name);
        }
    }
}
