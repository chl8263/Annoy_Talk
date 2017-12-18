package com.example.user.annoy_talk.ui.Select_peple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.interfaces.Select_Callback;
import com.example.user.annoy_talk.ui.First_Fragment.F_Recycler_item;

import java.util.List;

/**
 * Created by choi on 2017-12-16.
 */

public class Select_adapter extends RecyclerView.Adapter<Select_adapter.ViewHolders> {
    private Context context;
    private List<F_Recycler_item> items;
    private Select_Callback callback;

    public Select_adapter(Context context, List<F_Recycler_item> items, Select_Callback callback) {
        this.context = context;
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_recycle_item, parent, false);
        return new Select_adapter.ViewHolders(view);
    }


    @Override
    public void onBindViewHolder(ViewHolders holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.otherSpeech.setText(items.get(position).getAge() + " , " + items.get(position).getSex());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, otherSpeech;
        public LinearLayout linearLayout;

        public ViewHolders(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.first_RecyclerView_Item_Name);
            otherSpeech = (TextView) itemView.findViewById(R.id.otherSpeech);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.matching);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.matching:
                    int position = getAdapterPosition();
                    callback.select_add(items.get(position).getName());
                    break;
            }
        }
    }
   /* public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name,otherSpeech;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.first_RecyclerView_Item_Name);
            otherSpeech = (TextView) itemView.findViewById(R.id.otherSpeech);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.matching);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }*/
}
