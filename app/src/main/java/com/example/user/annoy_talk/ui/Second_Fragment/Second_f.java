package com.example.user.annoy_talk.ui.Second_Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.util.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 2017-11-19.
 */

public class Second_f extends Fragment {
    private RecyclerView second_recyclerView;
    private S_Recycle_Adapter s_recycle_adapter;
    private ArrayList<S_Recycler_item> items;

    public Second_f() {

    }

    public static Second_f newInstance() {
        Bundle args = new Bundle();
        Second_f fragment = new Second_f();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
            receiver = null;
        }
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        second_recyclerView = (RecyclerView) view.findViewById(R.id.second_RecyclerView);
        second_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        items = new ArrayList<S_Recycler_item>();
        s_recycle_adapter = new S_Recycle_Adapter(getContext(), items);
        second_recyclerView.setAdapter(s_recycle_adapter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.recvChatroom);
        intentFilter.addAction(Contact.EXIT);
        intentFilter.addAction(Contact.lastContent);
        getContext().registerReceiver(receiver, intentFilter);
        return view;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Contact.recvChatroom)) {

                Log.e("send_recvChatroom", intent.getStringExtra("chatroom"));

                String users = intent.getStringExtra("chatroom");
                String usersSplit[] = users.split("/");
                if (usersSplit.length > 2) {
                    String roomname = "";
                    Log.e("send_recvChatroom", Contact.myname);
                    for (int i = 0; i < usersSplit.length; i++) {
                        if (Contact.myname.equals(usersSplit[i])) {
                            for (int j = 0; j < usersSplit.length; j++) {
                                if (!usersSplit[j].equals(Contact.myname)) {
                                    roomname += usersSplit[j];
                                    if (j != usersSplit.length - 1) {
                                        roomname += ",";
                                    }
                                }
                            }
                            char check = roomname.charAt(roomname.length() - 1);
                            if (check == ',') {
                                roomname = roomname.substring(0, roomname.length() - 1);
                            }
                            items.add(new S_Recycler_item(roomname, "", ""));
                            s_recycle_adapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    String roomname;
                    Log.e("send_recvChatroom", Contact.myname);
                    for (int i = 0; i < usersSplit.length; i++) {
                        if (!Contact.myname.equals(usersSplit[i])) {
                            roomname = usersSplit[i];
                            items.add(new S_Recycler_item(roomname, "asd", "asd"));
                            s_recycle_adapter.notifyDataSetChanged();
                        }

                    }
                }
            }
            if (intent.getAction().equals(Contact.EXIT)) {
                Log.e("first ", "input");
                String content = intent.getStringExtra("exit_msg");
                Log.e("second_exit_msg", content);
                for (int i = 0; i < items.size(); i++) {
                    String splite[] = items.get(i).getName().split(",");
                    ArrayList<String> list = new ArrayList<String>();
                    if(splite.length>1){
                        for(int j=0;j<splite.length;j++){
                            list.add(splite[j]);
                            splite[j].equals(content);
                            items.remove(i);
                            break;
                        }

                    }else {
                        if (items.get(i).getName().equals(splite[0])) {
                            items.remove(i);
                        }
                    }
                }
                s_recycle_adapter.notifyDataSetChanged();

            }
            if (intent.getAction().equals(Contact.lastContent)) {
                String content = intent.getStringExtra("lastcontent");
                Log.e("second_lastContent", content);
                String splite[] = content.split(",");
                String last = splite[1];
                String roomname[] = splite[0].split("/");
                String other = "";
                for (int i = 0; i < roomname.length; i++) {
                    if (roomname[i].equals(Contact.myname)) {
                        for (int j = 0; j < roomname.length; j++) {
                            if (!Contact.myname.equals(roomname[j])) {
                                other += roomname[j];
                                other += ",";
                            }
                        }
                    }
                }
                Log.e("other", other);
                String a[] = other.split(",");
                String real = "";
                if (a.length >= 2) {//>=?
                    int sum = 0;
                    for (int i = 0; i < items.size(); i++) {
                        String qq[] = items.get(i).getName().split(",");
                        if (qq.length == a.length) {
                            for (int j = 0; j < a.length; j++) {
                                for (int k = 0; k < qq.length; k++) {
                                    if (a[j].equals(qq[k])) {
                                        sum++;
                                    }
                                }

                            }

                        }
                        if (sum == a.length) {
                            Log.e("통과", "꺄ㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑㅑ");
                            items.get(i).setLast_content(last);
                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat setformat = new SimpleDateFormat("HH:mm");
                            String time = setformat.format(date);
                            Log.e("time", time);
                            items.get(i).setTime(time);
                        }

                    }
                } else {
                    String bb = a[0];
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getName().equals(bb)) {
                            items.get(i).setLast_content(last);
                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat setformat = new SimpleDateFormat("HH:mm");
                            String time = setformat.format(date);
                            Log.e("time", time);
                            items.get(i).setTime(time);
                        }
                    }
                }
                s_recycle_adapter.notifyDataSetChanged();
            }
        }

    };
}
