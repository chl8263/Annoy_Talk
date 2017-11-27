package com.example.user.annoy_talk.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.user.annoy_talk.util.Contact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by choi on 2017-11-25.
 */

public class Connect extends Thread {
    private Context context;
    private int portnumber = 20002;
    private byte buffer[] = new byte[256];
    private InetAddress inetAddress = null;
    private String nickname;
    private String myip;
    private String content;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;


    public Connect(Context context,String name) {
        this.context = context;
        this.nickname = name;
        check_self_ip();
    }

    @Override
    public void run() {
        super.run();
        try {
            content = myip+","+nickname;
            inetAddress = InetAddress.getByName(Contact.serverip);
            buffer = content.getBytes();
            socket = new DatagramSocket();
            packet = new DatagramPacket(buffer, buffer.length, inetAddress, portnumber);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void check_self_ip(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wificon = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        if (wificon == false) {
            return;  // 연결이 됬는지 확인
        }
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        DhcpInfo dhcpInfo = wifimanager.getDhcpInfo();
        int wIp = dhcpInfo.ipAddress;
        myip = String.format("%d.%d.%d.%d", (wIp & 0xff), (wIp >> 8 & 0xff), (wIp >> 16 & 0xff), (wIp >> 24 & 0xff));
        Toast.makeText(context,myip,Toast.LENGTH_SHORT).show();
    }
}
