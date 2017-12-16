package com.example.user.annoy_talk.Streaming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.network.Sound_recv;
import com.example.user.annoy_talk.network.Sound_send;
import com.example.user.annoy_talk.util.Contact;

/**
 * Created by choi on 2017-12-15.
 */

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView profile;
    private ImageView profileimg;
    private TextView status;
    private ImageView callstatus;
    private String getName;
    private String ip;
    private String sound_send_port;
    private String sound_recv_port;
    private int flag;

    private Sound_send sound_send;
    private Sound_recv sound_recv;
    private MediaPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);


        init();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.sound_get_ip);
        intentFilter.addAction(Contact.voiceOK);
        intentFilter.addAction(Contact.voiceEXIT);

        registerReceiver(receiver, intentFilter);

        player = MediaPlayer.create(getApplicationContext(), R.raw.voice);
        player.setLooping(true);
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            player.stop();
            player.release();
            player=null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }


    }

    private void init() {
        profile = (TextView) findViewById(R.id.profile);
        profileimg = (ImageView) findViewById(R.id.profileImg);
        status = (TextView) findViewById(R.id.status);
        callstatus = (ImageView) findViewById(R.id.callstatus);
        callstatus.setOnClickListener(this);
        Intent intent = getIntent();
        getName = intent.getStringExtra("other");
        final String[] split = getName.split("/");
        if (split[0].equals("SEND")) {
            profile.setText(split[1]);
            getName = split[1];
            status.setText("연결중....");
            callstatus.setImageResource(R.drawable.voice_red);
            flag = 0;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Contact.connect_tcp.send_IP(split[1]);
                }
            }).start();

        } else if (split[0].equals("RECV")) {

            profile.setText(split[1]);
            getName = split[1];
            status.setText("전화왔습니다....");
            callstatus.setImageResource(R.drawable.voice_blue);
            flag = 1;
            Log.e("ippppppppppppp", "ggggggggggggggg");


            callstatus.setImageResource(R.drawable.voice_blue);
            ip = split[2];


        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callstatus:
                if (flag == 0) {
                    //사운드 소켓을 모두 종료하고 엑티비티를 닫는다.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Contact.connect_tcp.voice_EXIT(getName);
                        }
                    }).start();
                    if(sound_send!=null) {
                        sound_send.interrupt();
                        sound_send =null;
                    }if(sound_recv!=null){
                        sound_recv.interrupt();
                        sound_recv =null;
                    }
                   /* MainSendUdp mainSendUdp = new MainSendUdp("r/" + Contact.MyName + "/", ip, Integer.parseInt(main_recv_port));
                    mainSendUdp.start();*/
                    finish();
                } else if (flag == 1) {
                    /*MainSendUdp mainSendUdp = new MainSendUdp("s/" + Contact.MyName + "/", ip, Integer.parseInt(main_recv_port));
                    mainSendUdp.start();*/
                    status.setText("연결됨!");
                    flag = 0;
                    callstatus.setImageResource(R.drawable.voice_red);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Contact.connect_tcp.voice_ok(getName);
                        }
                    }).start();

                    sound_send = new Sound_send(ip,9002);
                    sound_send.start();
                    sound_recv = new Sound_recv(9001);
                    sound_recv.start();

                    if (player != null) {
                        player.stop();
                        player.release();
                    }
                }
                break;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Contact.sound_get_ip)){
                ip = intent.getStringExtra("ip");
                Log.e("ipCheck",ip);
                /*sound_send= new Sound_send(ip,9001);
                sound_send.start();
                sound_recv = new Sound_recv(9002);
                sound_recv.start();*/
            }else if(intent.getAction().equals(Contact.voiceOK)){
                player.stop();
                status.setText("연결됨!");
                sound_send= new Sound_send(ip,9001);
                sound_send.start();
                sound_recv = new Sound_recv(9002);
                sound_recv.start();
            }else if(intent.getAction().equals(Contact.voiceEXIT)){
                finish();
                if(sound_send!=null) {
                    sound_send.interrupt();
                    sound_send =null;
                }if(sound_recv!=null){
                    sound_recv.interrupt();
                    sound_recv =null;
                }

            }
        }
    };

}
