package com.example.user.annoy_talk.util;

import com.example.user.annoy_talk.network.Connect_tcp;

/**
 * Created by choi on 2017-11-25.
 */

public class Contact {
    public static String serverip = "192.168.0.17";
    // public static  String serverip="223.194.157.28";

    public static String EXIT = "exit";
    public static String makeChatroom = "chatroom";
    public static String recvChatroom = "recvChatroom";
    public static String recvChatContent = "recvChatContent";
    public static String lastContent = "lastContent";

    public static String myname;
    public static Connect_tcp connect_tcp;
}
