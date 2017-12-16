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
import android.widget.LinearLayout;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.Streaming.VideoActivity;
import com.example.user.annoy_talk.Streaming.VoiceActivity;
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
    private LinearLayout fabLayout1, fabLayout2;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private View fabBGLayout;
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
        fabBGLayout=findViewById(R.id.fabBGLayout);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
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
        fabBGLayout.setVisibility(View.VISIBLE);
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(135);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotationBy(-135);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
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
            case R.id.fab1:
                Intent intent2 = new Intent(getApplicationContext(), VideoActivity.class);
                intent2.putExtra("other","SEND/"+other);
                startActivity(intent2);
                break;
            case R.id.fab2:
                Intent intent1 = new Intent(getApplicationContext(), VoiceActivity.class);
                intent1.putExtra("other","SEND/"+other);
                startActivity(intent1);
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
