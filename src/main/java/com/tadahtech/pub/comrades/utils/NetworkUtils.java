package com.tadahtech.pub.comrades.utils;

import com.tadahtech.pub.comrades.network.Packet;
import com.tadahtech.pub.comrades.network.PacketProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by legop on 6/12/2016.
 */
public class NetworkUtils {

    private static ServerThread st;
    private static ClientListenThread clt;

    public NetworkUtils(String ip, int port, boolean server){
        if (server){
            try {
                st = new ServerThread(port);
                st.start();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                clt = new ClientListenThread(ip, port);
                clt.start();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void send(byte[] b) throws Exception{
        if (st!=null){
            st.send(b);
            return;
        } else {
            clt.send(b);
        }
    }


    public class ServerThread extends Thread{

        private ServerSocket ss;
        private ArrayList<Socket> sockets;

        public ServerThread(int port) throws Exception{
            ss = new ServerSocket(port);
        }

        @Override
        public void run(){
            try {
                while (true) {

                    Socket s = ss.accept();
                    sockets.add(s);

                }
            } catch (Exception e){

            }
        }

        public void send(byte[] bytes) throws Exception{
            for (Socket s : sockets){
                OutputStream os = s.getOutputStream();
                os.write(bytes);
                os.flush();
            }
        }
    }

    public class ClientListenThread extends Thread{

        private Socket s;

        public ClientListenThread(String ip, int port) throws Exception{
            s = new Socket(ip, port);
        }

        public void send(byte[] b) throws Exception{
            OutputStream os = s.getOutputStream();
            os.write(b);
            os.flush();
        }

        @Override
        public void run(){
            BufferedReader br;
            while (true){
                try {
                    br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String s = br.readLine();
                    Packet p = PacketProcessor.getPacket(s);
                    p.handle();
                } catch (Exception e){
                    e.printStackTrace();;
                }
            }
        }

    }
}
