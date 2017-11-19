package com.example.user.annoy_talk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.user.annoy_talk.ui.First_f;
import com.example.user.annoy_talk.ui.Second_f;

public class MainActivity extends AppCompatActivity {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusbar();
        initViewPager();
    }

    private void permissionCheck() {
        int permissionCheck_writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck_writeStorage == PackageManager.PERMISSION_DENIED) { //권한 없음
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() {


        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하시려면 한번더 눌러주세요", Toast.LENGTH_SHORT).show();
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

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.chat));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.user);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.chat);
                        break;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.user_unselect);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.chat_unselect);
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return First_f.newInstance();
                case 1:
                    return Second_f.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}



















