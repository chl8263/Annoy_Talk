package com.example.user.annoy_talk.util;

import com.example.user.annoy_talk.network.Connect_tcp;

/**
 * Created by choi on 2017-11-25.
 */

public class Contact {
    public static String serverip = "192.168.0.22";
    // public static  String serverip="223.194.157.28";

    public static String EXIT = "exit";
    public static String makeChatroom = "chatroom";
    public static String recvChatroom = "recvChatroom";
    public static String recvChatContent = "recvChatContent";
    public static String lastContent = "lastContent";


    public static String sound_get_ip = "sound_get_ip";
    public static String voiceOK = "voiceOK";
    public static String voiceEXIT = "voiceEXIT";

    public static String videoOK = "videoOK";
    public static String videoEXIT = "videoEXIT";

    public static String myname;
    public static String myage;
    public static String mysex;
    public static Connect_tcp connect_tcp;


    public static final String[] PERMISSIONS = {
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.ACCESS_WIFI_STATE",
    };

}
