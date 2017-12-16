package com.example.user.annoy_talk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * Created by choi on 2017-11-24.
 */

public class NicknameActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton nickbtn;
    private EditText nickname,age;
    private ToggleButton sex;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        //initStatusbar();
        init();
    }
    private void init(){
        nickname = (EditText)findViewById(R.id.nickname);
        age = (EditText)findViewById(R.id.age);
        sex = (ToggleButton)findViewById(R.id.sex);
        nickbtn = (FloatingActionButton)findViewById(R.id.nickbtn);
        nickbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nickbtn:
                String sexx = null;

                if(sex.isChecked()){
                    sexx="남";
                }else sexx="여";
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("nickname",nickname.getText().toString());
                intent.putExtra("age",age.getText().toString());
                intent.putExtra("sex",sexx);
                startActivity(intent);
                Log.e("check_nick",nickname.getText().toString());

                finish();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initStatusbar() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#ffc0cb"));
            }
        } else getWindow().setStatusBarColor(Color.parseColor("#000"));
    }
}
