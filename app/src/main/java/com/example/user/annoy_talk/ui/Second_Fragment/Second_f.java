package com.example.user.annoy_talk.ui.Second_Fragment;

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

public class Second_f extends Fragment {
    public Second_f() {

    }

    public static Second_f newInstance() {
        Bundle args = new Bundle();
        Second_f fragment = new Second_f();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);

        return view;
    }
}