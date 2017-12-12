package com.example.user.annoy_talk.ui.First_Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.ui.dialog.Matching_dialog;

import java.util.List;

/**
 * Created by choi on 2017-11-20.
 */

public class F_Recycle_Adapter extends RecyclerView.Adapter<F_Recycle_Adapter.ViewHolder> {
    private Context context;
    private List<F_Recycler_item> items;

    public F_Recycle_Adapter(Context context, List<F_Recycler_item> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.first_RecyclerView_Item_Name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.matching);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.matching:
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), Matching_dialog.class);
                    intent.putExtra("other_name", items.get(position).getName());
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
