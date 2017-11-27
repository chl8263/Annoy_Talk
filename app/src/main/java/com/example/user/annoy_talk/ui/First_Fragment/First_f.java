package com.example.user.annoy_talk.ui.First_Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.interfaces.Recv_Listener;
import com.example.user.annoy_talk.network.Connect_tcp;
import com.example.user.annoy_talk.util.Contact;

import java.util.ArrayList;

/**
 * Created by User on 2017-11-19.
 */

public class First_f extends Fragment{
    private String myNickname;
    private TextView myProfile;
    private RecyclerView first_recyclerView;
    private F_Recycle_Adapter f_recycle_adapter;
    private ArrayList<F_Recycler_item> items;
    //static Connect_tcp connect_tcp;
    public First_f(){

    }
    public static First_f newInstance(String name){
        Bundle args = new Bundle();
        args.putString("NICKNAME",name);
        First_f fragment = new First_f();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            myNickname = getArguments().getString("NICKNAME");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);
        myProfile = (TextView)view.findViewById(R.id.first_Myprofile_Name);
        myProfile.setText(myNickname);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.EXIT);
        getContext().registerReceiver(receiver, intentFilter);

        first_recyclerView = (RecyclerView)view.findViewById(R.id.first_RecyclerView);
        items = new ArrayList<F_Recycler_item>();

        f_recycle_adapter = new F_Recycle_Adapter(getContext(),items);
        first_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        first_recyclerView.setAdapter(f_recycle_adapter);
        Contact.connect_tcp = new Connect_tcp(getActivity().getApplicationContext(),recv_listener,myNickname);
        Contact.connect_tcp.start();
        return view;
    }

    @Override
    public void onStop() {
        if(receiver!=null) {
            getContext().unregisterReceiver(receiver);
            receiver=null;
        }
        super.onStop();

    }

    Recv_Listener recv_listener = new Recv_Listener() {
        @Override
        public void recv_refresh(final String content) {
            Log.e("come callback",content);
            /*Refresh_users refresh_users = new Refresh_users(content);
            refresh_users.execute();*/
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(content!=null) {
                        String users[] = content.split(",");
                        items.clear();
                        for (int i = 0; i < users.length; i++) {
                            items.add(new F_Recycler_item(users[i]));
                            Log.e("cc", users[i]);
                        }
                        f_recycle_adapter.notifyDataSetChanged();
                    }
                }
            });
            /*new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();*/
        }
    };
    private class Refresh_users extends AsyncTask{
        public String content;
        public Refresh_users(String content){
            this.content = content;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            String users []= content.split(",");
            items.clear();
            for(int i=0;i<users.length;i++) {
                items.add(new F_Recycler_item(users[i]));
                Log.e("asdsd",users[i]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            f_recycle_adapter.notifyDataSetChanged();
            super.onPostExecute(o);
        }
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Contact.EXIT)){
                Contact.connect_tcp.sendExit();
            }
        }
    };
}
