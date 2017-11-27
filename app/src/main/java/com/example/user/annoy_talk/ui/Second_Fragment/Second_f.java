package com.example.user.annoy_talk.ui.Second_Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.annoy_talk.R;

import java.util.ArrayList;

/**
 * Created by User on 2017-11-19.
 */

public class Second_f extends Fragment {
    private RecyclerView second_recyclerView;
    private S_Recycle_Adapter s_recycle_adapter;
    private ArrayList<S_Recycler_item> items;
    public Second_f() {

    }

    public static Second_f newInstance() {
        Bundle args = new Bundle();
        Second_f fragment = new Second_f();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<S_Recycler_item>();
        s_recycle_adapter = new S_Recycle_Adapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);
        second_recyclerView = (RecyclerView)view.findViewById(R.id.second_RecyclerView);
        second_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        second_recyclerView.setAdapter(s_recycle_adapter);
        return view;
    }
}
