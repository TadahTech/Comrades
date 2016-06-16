package com.tadahtech.pub.comrades.network.packets;

import com.tadahtech.pub.comrades.network.Packet;
import com.tadahtech.pub.comrades.network.PacketType;

import java.util.UUID;

/**
 * Created by legop on 6/12/2016.
 * Sent to all servers on player leave.
 */
public class LeavePacket extends Packet {

    private UUID uuid;
    private boolean doNotify;

    public LeavePacket(UUID playerId, boolean doNotify) throws Exception {
        super(PacketType.LEAVE);
        this.uuid = playerId;
        this.doNotify = doNotify;
    }

    public byte[] prepareSend(){

        String data;
        if (doNotify){
            data = pt.getId() + ";" + uuid.toString() + ";" + 1;
        } else {
            data = pt.getId() + ";" + uuid.toString() + ";" + 0;
        }

        byte[] toSend = data.getBytes();
        return toSend;
    }

    @Override
    public void handle() {
        //TODO: HANDLE THE PACKET
    }

}
