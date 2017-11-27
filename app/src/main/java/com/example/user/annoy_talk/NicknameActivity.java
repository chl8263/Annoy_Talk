package com.example.user.annoy_talk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by choi on 2017-11-24.
 */

public class NicknameActivity extends AppCompatActivity implements View.OnClickListener{
    private Button nickbtn;
    private EditText nickname;
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
        nickbtn = (Button)findViewById(R.id.nickbtn);
        nickbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nickbtn:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("nickname",nickname.getText().toString());
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
