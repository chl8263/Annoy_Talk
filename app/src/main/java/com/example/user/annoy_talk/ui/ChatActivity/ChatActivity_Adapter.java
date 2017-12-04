package com.example.user.annoy_talk.ui.ChatActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.annoy_talk.R;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-04.
 */

public class ChatActivity_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Chat_item> items;
    public final int VIEW_TYPE_YOU = 1;
    public final int VIEW_TYPE_ME = 0;

    public ChatActivity_Adapter(Context context, ArrayList<Chat_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getViewType() == VIEW_TYPE_ME) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_YOU;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_me, parent, false);
            return new ViewHolder_ME(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_other, parent, false);
            return new ViewHolder_OTHER(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder_ME) {
            ((ViewHolder_ME) holder).content.setText(items.get(position).getContent());
        } else {
            ((ViewHolder_OTHER) holder).content.setText(items.get(position).getContent());
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder_ME extends RecyclerView.ViewHolder {
        public TextView content;

        public ViewHolder_ME(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.chat_me_item_content);
        }
    }

    public class ViewHolder_OTHER extends RecyclerView.ViewHolder {
        public TextView content;

        public ViewHolder_OTHER(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.chat_item_content);

        }
    }
}
