package com.example.user.annoy_talk.ui.Select_peple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.ui.First_Fragment.F_Recycler_item;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-16.
 */

public class SelectActivity extends AppCompatActivity{
    private RecyclerView select;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


    }
    private void init(){
        select = (RecyclerView)findViewById(R.id.select);
        Intent intent = getIntent();
        ArrayList<F_Recycler_item> a = (ArrayList<F_Recycler_item>)intent.getSerializableExtra("item");

        
    }
}
