package com.tadahtech.pub.comrades.network;

import com.tadahtech.pub.comrades.utils.NetworkUtils;

/**
 * Created by legop on 6/10/2016.
 */
public abstract class Packet implements ComradesPacket {

    public PacketType pt;

    public Packet(PacketType pt){
        this.pt = pt;
    }

    public void send(byte[] bytes) throws Exception{
        NetworkUtils.send(bytes);
    }

}


