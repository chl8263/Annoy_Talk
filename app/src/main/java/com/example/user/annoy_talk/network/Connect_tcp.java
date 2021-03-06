package com.example.user.annoy_talk.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.user.annoy_talk.Streaming.VideoActivity;
import com.example.user.annoy_talk.Streaming.VoiceActivity;
import com.example.user.annoy_talk.interfaces.Recv_Listener;
import com.example.user.annoy_talk.util.Contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by choi on 2017-11-25.
 */

public class Connect_tcp extends Thread {
    private String users;
    private Context context;
    private Recv_Listener recv_listener;
    private int portnumber = 3300;
    private Socket socket = null;
    private String nickname;
    private String myip;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Connect_tcp(Context context, Recv_Listener recv_listener, String nickname) {
        this.nickname = nickname;
        this.context = context;
        this.recv_listener = recv_listener;
        check_self_ip();
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(Contact.serverip, portnumber);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(nickname+","+myip +","+Contact.myage+ ","+Contact.mysex);
            while (true) {

                String check = dataInputStream.readUTF();

                if (check.equals("users")) {
                    String users = dataInputStream.readUTF();
                    recv_listener.recv_refresh(users);
                } else if (check.equals("chatroom")) {
                    String users = dataInputStream.readUTF();
                    Log.e("chatroom", users);
                    Intent intent = new Intent(Contact.recvChatroom);
                    intent.putExtra("chatroom", users);
                    context.sendBroadcast(intent);
                } else if (check.equals("chat")) {
                    String content = dataInputStream.readUTF();
                    Log.e("recv___chat", content);
                    Intent intent = new Intent(Contact.recvChatContent);
                    intent.putExtra("recvChatContent", content);
                    context.sendBroadcast(intent);
                } else if (check.equals("exit")) {
                    String content = dataInputStream.readUTF();
                    Log.e("recv___exit", content);
                    Intent intent = new Intent(Contact.EXIT);
                    intent.putExtra("exit_msg", content);
                    context.sendBroadcast(intent);
                } else if (check.equals("lastchat")) {
                    String content = dataInputStream.readUTF();
                    Log.e("last_chat", content);
                    Intent intent = new Intent(Contact.lastContent);
                    intent.putExtra("lastcontent", content);
                    context.sendBroadcast(intent);
                } else if (check.equals("ip")) {
                    String content = dataInputStream.readUTF();
                    Log.e("ip", content);

                    Intent intent = new Intent(Contact.sound_get_ip);
                    intent.putExtra("ip", content);
                    context.sendBroadcast(intent);
                } else if (check.equals("recvip")) {
                    String content = dataInputStream.readUTF();
                    Log.e("other", content);
                    Intent intent = new Intent(context.getApplicationContext(), VoiceActivity.class);
                    intent.putExtra("other", "RECV/" + content);
                    context.startActivity(intent);

                } else if (check.equals("voiceOK")) {
                    Intent intent = new Intent(Contact.voiceOK);
                    context.sendBroadcast(intent);
                } else if (check.equals("voiceEXIT")) {
                    Intent intent = new Intent(Contact.voiceEXIT);
                    context.sendBroadcast(intent);
                } else if (check.equals("videoCONN")) {
                    String content = dataInputStream.readUTF();
                    Intent intent = new Intent(context.getApplicationContext(), VideoActivity.class);
                    intent.putExtra("other", "RECV/" + content);
                    context.startActivity(intent);
                } else if (check.equals("videoOK")) {
                    Intent intent = new Intent(Contact.videoOK);
                    context.sendBroadcast(intent);
                }else if (check.equals("videoEXIT")) {
                    Intent intent = new Intent(Contact.videoEXIT);
                    context.sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChatRoom(String roominfo) {
        try {
            dataOutputStream.writeUTF("chatroom");
            dataOutputStream.writeUTF(roominfo);
            Intent intent = new Intent(Contact.makeChatroom);
            intent.putExtra("chatinfo", roominfo);
            context.sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChat(String chatinfo_roomName, String chatinfo_content) {
        try {
            dataOutputStream.writeUTF("chat");
            dataOutputStream.writeUTF(chatinfo_roomName + "," + Contact.myname + "," + chatinfo_content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendinitChat(String chatinfo_roomName) {
        try {
            dataOutputStream.writeUTF("rechat");
            dataOutputStream.writeUTF(chatinfo_roomName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendExit() {
        try {
            dataOutputStream.writeUTF("exit");
            dataOutputStream.writeUTF(Contact.myname);
            /*socket.close();
            dataOutputStream.close();
            dataOutputStream.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send_IP(String name) {
        try {
            dataOutputStream.writeUTF("ip");
            dataOutputStream.writeUTF(Contact.myname + "/" + name);
            /*socket.close();
            dataOutputStream.close();
            dataOutputStream.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voice_ok(String name) {
        try {
            dataOutputStream.writeUTF("voiceOK");
            dataOutputStream.writeUTF(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voice_EXIT(String name) {
        try {
            dataOutputStream.writeUTF("voiceEXIT");
            dataOutputStream.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void videoCONN(String name) {
        try {
            dataOutputStream.writeUTF("videoCONN");
            dataOutputStream.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void videoOK(String name) {
        try {
            dataOutputStream.writeUTF("videoOK");
            dataOutputStream.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void videoEXIT(String name) {
        try {
            dataOutputStream.writeUTF("videoEXIT");
            dataOutputStream.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void check_self_ip() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wificon = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        if (wificon == false) {
            return;  // 연결이 됬는지 확인
        }
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        DhcpInfo dhcpInfo = wifimanager.getDhcpInfo();
        int wIp = dhcpInfo.ipAddress;
        myip = String.format("%d.%d.%d.%d", (wIp & 0xff), (wIp >> 8 & 0xff), (wIp >> 16 & 0xff), (wIp >> 24 & 0xff));
        Toast.makeText(context, myip, Toast.LENGTH_SHORT).show();
    }
}
