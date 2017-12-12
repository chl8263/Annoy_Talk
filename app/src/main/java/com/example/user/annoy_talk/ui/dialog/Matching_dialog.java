package com.example.user.annoy_talk.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.util.Contact;

/**
 * Created by choi on 2017-11-27.
 */

public class Matching_dialog extends AppCompatActivity implements View.OnClickListener{
    private TextView matching_Cancel;
    private TextView matching_Ok;
    private String other_name;
    private String other_namesplite[];
    String a="";
    String b="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //타이틀바 없애는코드
        setContentView(R.layout.matching_dialog);
        setFinishOnTouchOutside(false);         //다이얼로그 테마로 다이얼로그를 띄울때 다른곳을 터치할시에 꺼지는것을 방지
        init();
    }
    private void init(){
        matching_Ok = (TextView)findViewById(R.id.matching_Ok);
        matching_Cancel = (TextView)findViewById(R.id.matching_Cancel);
        matching_Ok.setOnClickListener(this);
        matching_Cancel.setOnClickListener(this);
        Intent intent = getIntent();

        other_name = intent.getStringExtra("other_name");
        Log.e("ㅆㅆㅆㅆㅆㅆㅆㅆㅆㅆㅆㅆ",other_name);
        other_namesplite = other_name.split(",");

        for(int i=0;i<other_namesplite.length;i++){
            a+=other_namesplite[i];
            if(i!=other_namesplite.length-1){
                a+="/";
            }
        }
        for(int i=0;i<other_namesplite.length;i++){
            b+=other_namesplite[i];
            if(i!=other_namesplite.length-1){
                b+=",";
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.matching_Ok:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Contact.connect_tcp.sendChatRoom(Contact.myname+"/"+a+","+Contact.myname+","+b);
                    }
                }).start();
                finish();
                break;
            case R.id.matching_Cancel:
                finish();
                break;
        }
    }
}
