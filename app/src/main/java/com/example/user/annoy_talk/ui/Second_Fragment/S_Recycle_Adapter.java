package com.example.user.annoy_talk.ui.Second_Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.ui.ChatActivity.Chat_activity;

import java.util.List;

/**
 * Created by choi on 2017-11-20.
 */

public class S_Recycle_Adapter extends RecyclerView.Adapter<S_Recycle_Adapter.ViewHoler>{
    private Context context;
    private List<S_Recycler_item> items ;

    public S_Recycle_Adapter(Context context, List<S_Recycler_item> items) {
        this.context = context;
        this.items = items;
    }

    //private
    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_recycle_item,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.lastContent.setText(items.get(position).getLast_content());
        holder.time.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView lastContent;
        public TextView time;
        private LinearLayout click;
        public ViewHoler(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.second_RecyclerView_Item_Name);
            lastContent = (TextView)itemView.findViewById(R.id.second_RecyclerView_Item_Content);
            time = (TextView)itemView.findViewById(R.id.second_RecyclerView_Item_Time);
            click = (LinearLayout)itemView.findViewById(R.id.chat_click);
            click.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.chat_click:
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), Chat_activity.class);
                    intent.putExtra("other_name",items.get(position).getName());
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
