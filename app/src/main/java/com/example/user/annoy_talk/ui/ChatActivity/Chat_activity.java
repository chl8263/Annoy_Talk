package com.example.user.annoy_talk.ui.ChatActivity;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.util.Contact;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-04.
 */

public class Chat_activity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private EditText inputText;
    private Button submit;
    String other;
    String others[];
    String all="";
    private ChatActivity_Adapter chatActivity_adapter;
    private ArrayList<Chat_item> items;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private boolean isFABOpen=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        other = intent.getStringExtra("other_name");
        others = other.split(",");
        for(int i=0;i<others.length;i++){
            all+=others[i];
            if(i!=others.length-1){
                all+="/";
            }
        }
        init();
        init_chat();
        initFab();
    }
    @Override
    public void onStop() {
        if(receiver!=null) {
            unregisterReceiver(receiver);
            receiver=null;
        }
        super.onStop();

    }

    private void initFab(){
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
    }
    private void showFABMenu(){
        isFABOpen=true;
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fab1.animate().translationY(-55);
        fab2.animate().translationY(-100);
        fab3.animate().translationY(-145);
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotationBy(-180);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fab1.setVisibility(View.GONE);
                    fab2.setVisibility(View.GONE);
                    fab3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void init_chat(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact.connect_tcp.sendinitChat(all+"/"+Contact.myname);
            }
        }).start();
    }
    private void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.recvChatContent);
        registerReceiver(receiver, intentFilter);
        inputText = (EditText) findViewById(R.id.inputText);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener((View.OnClickListener) this);
        items = new ArrayList<Chat_item>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_activity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatActivity_adapter = new ChatActivity_Adapter(getApplicationContext(), items);
        recyclerView.setAdapter(chatActivity_adapter);
        recyclerView.scrollToPosition(chatActivity_adapter.getItemCount() - 1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Contact.connect_tcp.sendChat(Contact.myname + "/" + all ,inputText.getText().toString());
                    }
                }).start();
                inputText.setText("");
                break;
        }
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            items.clear();
            if(intent.getAction().equals(Contact.recvChatContent)){
                Log.e("ChatActivity_Content",intent.getStringExtra("recvChatContent"));
                String splitMain[] = intent.getStringExtra("recvChatContent").split("/");
                for(int i=0;i<splitMain.length;i++){
                    String subSplit [] = splitMain[i].split(",");
                    items.add(new Chat_item(subSplit[0],subSplit[1],0));
                }
                chatActivity_adapter.notifyDataSetChanged();
            }
        }
    };

}
