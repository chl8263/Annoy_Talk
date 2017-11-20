package com.example.user.annoy_talk.ui.Second_Fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by choi on 2017-11-20.
 */

public class S_Recycle_Adapter extends RecyclerView.Adapter<S_Recycle_Adapter.ViewHoler>{
    private Context context;
    private
    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHoler extends RecyclerView.ViewHolder{

        public ViewHoler(View itemView) {
            super(itemView);
        }
    }
}
