package com.example.user.annoy_talk.network;

import android.content.Context;
import android.util.Log;

import com.example.user.annoy_talk.interfaces.Recv_Listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by choi on 2017-11-25.
 */

public class Recv_users extends Thread {
    private String users;
    private Context context;
    private int portnumber = 20003;
    private Recv_Listener recv_listener;
    private byte buffer[] = new byte[256];
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;

    public Recv_users(Context context,Recv_Listener recv_listener) {
        this.context = context;
        this.recv_listener =recv_listener;
        recv_listener.recv_refresh("qqqqqqqqqqq");
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new DatagramSocket(portnumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true) {

            try {
                buffer=null;
                buffer = new byte[256];

                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                users = new String(packet.getData(),"UTF-8");
                users = users.substring(0,packet.getLength());
                Log.e("ALLUSER",users + " " + packet.getLength());
                recv_listener.recv_refresh(users);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
