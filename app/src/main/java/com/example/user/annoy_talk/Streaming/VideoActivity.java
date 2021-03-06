package com.example.user.annoy_talk.Streaming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.annoy_talk.R;
import com.example.user.annoy_talk.util.Contact;
import com.sktelecom.playrtc.PlayRTC;
import com.sktelecom.playrtc.PlayRTCFactory;
import com.sktelecom.playrtc.config.PlayRTCAudioConfig;
import com.sktelecom.playrtc.config.PlayRTCConfig;
import com.sktelecom.playrtc.config.PlayRTCVideoConfig;
import com.sktelecom.playrtc.exception.RequiredConfigMissingException;
import com.sktelecom.playrtc.exception.RequiredParameterMissingException;
import com.sktelecom.playrtc.exception.UnsupportedPlatformVersionException;
import com.sktelecom.playrtc.observer.PlayRTCObserver;
import com.sktelecom.playrtc.stream.PlayRTCMedia;
import com.sktelecom.playrtc.util.ui.PlayRTCVideoView;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by choi on 2017-12-09.
 */

public class VideoActivity extends AppCompatActivity {
    private final String T_DEVELOPERS_PROJECT_KEY = "60ba608a-e228-4530-8711-fa38004719c1";

    private PlayRTC playrtc;
    private PlayRTCObserver playrtcObserver;

    private boolean isCloseActivity = false;
    private boolean isChannelConnected = false;
    private PlayRTCVideoView localView;
    private PlayRTCVideoView remoteView;
    private PlayRTCMedia localMedia;
    private PlayRTCMedia remoteMedia;
    private String channelId;

    private View darkBackground;
    private MediaPlayer player;

    /*private String ip;
    private String main_recv_port;
    private String sound_send_port;*/

    private ImageView v_call_status;
    private TextView video_otherName;
    private TextView video_status;
    private String Recv__Channel;

    private String check;

    private String getname;

    private int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.videoOK);
        intentFilter.addAction(Contact.videoEXIT);
        registerReceiver(receiver, intentFilter);

        player = MediaPlayer.create(getApplicationContext(), R.raw.video);
        player.setLooping(true);
        player.start();


        createPlayRTCObserverInstance();
        createPlayRTCInstance();
        init();

    }

    private void init() {
        darkBackground = findViewById(R.id.darkBackground);
        Intent intent = getIntent();
        String[] split = intent.getStringExtra("other").split("/");
        video_status = (TextView) findViewById(R.id.video_status);
        video_otherName = (TextView) findViewById(R.id.video_otherName);
        v_call_status = (ImageView) findViewById(R.id.v_call_status);
        v_call_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    finish();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Contact.connect_tcp.videoEXIT(getname);
                        }
                    }).start();
                } else if (flag == 1) {
                    video_otherName.setVisibility(View.INVISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Contact.connect_tcp.videoOK(getname);
                        }
                    }).start();
                    try {
                        playrtc.connectChannel(Recv__Channel, new JSONObject());
                    } catch (RequiredConfigMissingException e) {
                        e.printStackTrace();
                    }
                    video_status.setVisibility(View.INVISIBLE);

                    v_call_status.setImageResource(R.drawable.voice_red);
                    flag = 0;
                    darkBackground.setVisibility(View.INVISIBLE);


                    if (player != null) {
                        player.stop();
                        player.release();
                    }
                }
            }
        });

        if (split[0].equals("SEND")) {
            video_otherName.setText(split[1]);
            getname = split[1];
            darkBackground.setVisibility(View.VISIBLE);
            check = split[0];
            video_status.setText("영상 연결중....");
            try {
                playrtc.createChannel(new JSONObject());    //채널 만드는곳
            } catch (RequiredConfigMissingException e) {
                e.printStackTrace();
            }

        } else if (split[0].equals("RECV")) {
            video_otherName.setText(split[1]);
            getname = split[1];
            video_status.setText("영상 통화 왔습니다....");
            darkBackground.setVisibility(View.VISIBLE);
            flag = 1;
            v_call_status.setImageResource(R.drawable.voice_blue);
            Log.e("aaaaaaaaaa", split[2]);
            check = split[0];
            Recv__Channel = split[2];


        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Contact.videoOK)) {
                darkBackground.setVisibility(View.INVISIBLE);
                video_otherName.setVisibility(View.INVISIBLE);
                video_status.setVisibility(View.INVISIBLE);
                if (player != null) {
                    player.stop();
                    player.release();
                }
            }else if (intent.getAction().equals(Contact.videoEXIT)) {
                finish();
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && this.localView == null) {
            createVideoView();
        }
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
    protected void onDestroy() {
        super.onDestroy();

        // instance release
        if (playrtc != null) {
            playrtc.close();
            playrtc = null;
        }

        // new v2.2.6
        if (localView != null) {
            localView.release();
        }
        // new v2.2.6
        if (remoteView != null) {
            remoteView.release();
        }

        playrtcObserver = null;
        //android.os.Process.killProcess(android.os.Process.myPid());   //앱 자체를 아예 종료 시키는것
    }

    private void createPlayRTCObserverInstance() {
        playrtcObserver = new PlayRTCObserver() {
            @Override   //채널 만들기 눌렀을때 만든채널 나오는곳
            public void onConnectChannel(final PlayRTC obj, final String channelId, final String channelCreateReason, final String channelType) {
                isChannelConnected = true;
                if (check.equals("SEND")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Contact.connect_tcp.videoCONN(Contact.myname + "/" + getname + "/" + channelId);
                        }
                    }).start();
                }
                Log.e("****make channel******", channelId);

            }

            @Override
            public void onAddLocalStream(final PlayRTC obj, final PlayRTCMedia playRTCMedia) {
                localMedia = playRTCMedia;

                playRTCMedia.setVideoRenderer(localView.getVideoRenderer());
            }

            @Override
            public void onAddRemoteStream(final PlayRTC obj, final String peerId, final String peerUserId, final PlayRTCMedia playRTCMedia) {

                remoteMedia = playRTCMedia;

                playRTCMedia.setVideoRenderer(remoteView.getVideoRenderer());

            }

            @Override
            public void onDisconnectChannel(final PlayRTC obj, final String disconnectReason) {
                isChannelConnected = false;

                // v2.2.5
                localView.bgClearColor();
                remoteView.bgClearColor();

                createPlayRTCInstance();
            }


        };
    }

    private void createPlayRTCInstance() {
        try {
            //function for sdk v2.2.0
            PlayRTCConfig config = createPlayRTCConfig();
            playrtc = PlayRTCFactory.createPlayRTC(config, playrtcObserver);


        } catch (UnsupportedPlatformVersionException e) {
            e.printStackTrace();
        } catch (RequiredParameterMissingException e) {
            e.printStackTrace();
        }
    }

    private PlayRTCConfig createPlayRTCConfig() {
        PlayRTCConfig config = PlayRTCFactory.createConfig();

        config.setAndroidContext(getApplicationContext());

        config.setProjectId(T_DEVELOPERS_PROJECT_KEY);

        config.video.setEnable(true); // send video stream

        config.video.setCameraType(PlayRTCVideoConfig.CameraType.Front);


        config.video.setPreferCodec(PlayRTCVideoConfig.VideoCodec.VP8);


        config.video.setMaxFrameSize(640, 480);
        config.video.setMinFrameSize(640, 480);


        config.audio.setEnable(true);    //send audio stream
        config.audio.setAudioManagerEnable(true);

        config.audio.setPreferCodec(PlayRTCAudioConfig.AudioCodec.OPUS);


        config.data.setEnable(true);     //use datachannel stream

        config.log.console.setLevel(PlayRTCConfig.DEBUG);

        File logPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Android/data/" + getPackageName() + "/files/log/");
        if (logPath.exists() == false) {
            logPath.mkdirs();
        }
        config.log.file.setLogPath(logPath.getAbsolutePath());
        config.log.file.setLevel(PlayRTCConfig.DEBUG);

        return config;
    }

    private void createVideoView() {
        RelativeLayout myVideoViewGroup = (RelativeLayout) findViewById(R.id.video_view_group);

        if (localView != null) {
            return;
        }

        Point myViewDimensions = new Point();
        myViewDimensions.x = myVideoViewGroup.getWidth();
        myViewDimensions.y = myVideoViewGroup.getHeight();

        if (remoteView == null) {
            createRemoteVideoView(myViewDimensions, myVideoViewGroup);
        }

        if (localView == null) {
            createLocalVideoView(myViewDimensions, myVideoViewGroup);
        }
    }

    private void createLocalVideoView(final Point parentViewDimensions, RelativeLayout parentVideoViewGroup) {
        if (localView == null) {
            Point myVideoSize = new Point();
            myVideoSize.x = (int) (parentViewDimensions.x * 0.3);
            myVideoSize.y = (int) (parentViewDimensions.y * 0.3);

            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(myVideoSize.x, myVideoSize.y);
            param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            param.setMargins(30, 30, 30, 30);

            localView = new PlayRTCVideoView(parentVideoViewGroup.getContext());
            localView.setZOrderMediaOverlay(true);
            localView.setBgClearColor(225, 225, 225, 255);
            localView.setLayoutParams(param);
            localView.initRenderer();
            parentVideoViewGroup.addView(localView);


        }
    }

    private void createRemoteVideoView(final Point parentViewDimensions, RelativeLayout parentVideoViewGroup) {
        if (remoteView == null) {
            // Create the video size variable.
            Point myVideoSize = new Point();
            myVideoSize.x = parentViewDimensions.x;
            myVideoSize.y = parentViewDimensions.y;

            // Create the view parameters.
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            // Create the remoteView.
            // new v2.2.6
            remoteView = new PlayRTCVideoView(parentVideoViewGroup.getContext());
            // Background color
            // v2.2.5
            remoteView.setBgClearColor(200, 200, 200, 255);
            // Set the layout parameters.
            remoteView.setLayoutParams(param);

            // new v2.2.6
            remoteView.initRenderer();

            // Add the view to the videoViewGroup.
            parentVideoViewGroup.addView(remoteView);
        }
    }

}
