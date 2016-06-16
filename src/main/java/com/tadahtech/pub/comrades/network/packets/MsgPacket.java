package com.tadahtech.pub.comrades.network.packets;

import com.tadahtech.pub.comrades.network.Packet;
import com.tadahtech.pub.comrades.network.PacketType;

import java.util.UUID;

/**
 * Created by legop on 6/13/2016.
 */
public class MsgPacket extends Packet {

    UUID from;
    boolean toParty;
    String msg;

    public MsgPacket(UUID from, boolean toParty, String msg){
        super(PacketType.MSG);
        this.from = from;
        this.toParty = toParty;
        this.msg = msg;
    }

    @Override
    public byte[] prepareSend() throws Exception {
        String s;
        if (toParty){
           s = pt.getId() + ";" + from.toString() + ";" + 1 + ";" + msg.replaceAll(";", "%-");
        } else {
            s = pt.getId() + ";" + from.toString() + ";" + 0 + ";" + msg.replaceAll(";", "%-");
        }
        return s.getBytes();
    }

    @Override
    public void handle() {
        //TODO: HANDLE THE PACKET
    }
}
