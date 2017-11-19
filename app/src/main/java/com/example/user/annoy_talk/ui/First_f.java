package com.example.user.annoy_talk.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.annoy_talk.R;

/**
 * Created by User on 2017-11-19.
 */

public class First_f extends Fragment{
    public First_f(){

    }
    public static First_f newInstance(){
        Bundle args = new Bundle();
        First_f fragment = new First_f();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);

        return view;
    }

}
