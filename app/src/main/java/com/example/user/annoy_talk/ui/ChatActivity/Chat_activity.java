package com.example.user.annoy_talk.ui.ChatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private ChatActivity_Adapter chatActivity_adapter;
    private ArrayList<Chat_item> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        other = intent.getStringExtra("other_name");
        init();

    }

    private void init() {
        inputText = (EditText) findViewById(R.id.inputText);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener((View.OnClickListener) this);
        items = new ArrayList<Chat_item>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_activity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatActivity_adapter = new ChatActivity_Adapter(getApplicationContext(), items);
        recyclerView.setAdapter(chatActivity_adapter);
        recyclerView.scrollToPosition(chatActivity_adapter.getItemCount() - 1);

        items.add(new Chat_item("asd", 0));
        items.add(new Chat_item("asd", 1));
        items.add(new Chat_item("asd", 0));
        items.add(new Chat_item("asd", 1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Contact.connect_tcp.sendChat(Contact.myname + "/" + other ,inputText.getText().toString());
                    }
                }).start();

                inputText.setText("");
                break;
        }
    }


}
