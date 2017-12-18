package com.example.user.annoy_talk.ui.Select_peple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.interfaces.Select_Callback;
import com.example.user.annoy_talk.ui.First_Fragment.F_Recycler_item;
import com.example.user.annoy_talk.util.Contact;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-16.
 */

public class SelectActivity extends AppCompatActivity{
    private RecyclerView select;
    private RecyclerView selected;
    private Select_adapter selectAdapter;
    private Selected_adapter selected_adapter;
    private ArrayList<F_Recycler_item> items;
    private ArrayList<Selected_item> itemssss;
    private FloatingActionButton multi_ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //타이틀바 없애는코드

        setContentView(R.layout.activity_select);

        init();

    }
    private void init(){
        multi_ok = (FloatingActionButton)findViewById(R.id.multi_ok);
        multi_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameee="";
                String nameee2="";
                for(int i=0;i<itemssss.size();i++){

                    nameee+=itemssss.get(i).getName();
                    if (i != items.size() - 1) {
                        nameee += "/";
                    }
                }
                for(int i=0;i<itemssss.size();i++){

                    nameee2+=itemssss.get(i).getName();
                    if (i != items.size() - 1) {
                        nameee2 += ",";
                    }
                }
                final String finalNameee = nameee;
                final String finalNameee1 = nameee2;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Contact.connect_tcp.sendChatRoom(Contact.myname+"/"+ finalNameee +","+Contact.myname+","+ finalNameee1);
                    }
                }).start();
                finish();
            }
        });
        select = (RecyclerView)findViewById(R.id.select);
        selected = (RecyclerView)findViewById(R.id.select_ok);
        Intent intent = getIntent();
        items = (ArrayList<F_Recycler_item>)intent.getSerializableExtra("item");

        selectAdapter = new Select_adapter(this,items,select_callback);
        select.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        select.setAdapter(selectAdapter);

        itemssss= new ArrayList<Selected_item>();
        selected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selected_adapter = new Selected_adapter(this,itemssss);
        selected.setAdapter(selected_adapter);
    }
    private Select_Callback select_callback = new Select_Callback() {
        @Override
        public void select_add(String name) {
            Log.e("aaaaaaaaaaa",name);
            itemssss.add(new Selected_item(name));
            selected_adapter.notifyItemInserted(itemssss.size()-1);

        }
    };


}

