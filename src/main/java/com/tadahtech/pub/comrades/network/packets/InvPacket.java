package com.tadahtech.pub.comrades.network.packets;

import com.tadahtech.pub.comrades.network.Packet;
import com.tadahtech.pub.comrades.network.PacketType;

import java.util.UUID;

/**
 * Created by legop on 6/12/2016.
 */
public class InvPacket extends Packet {

    private UUID player;
    private String invName;
    private boolean notify;

    public InvPacket(UUID player, String invName, boolean doNotify) {
        super(PacketType.INV);
        this.player = player;
        this.invName = invName;
        this.notify = doNotify;
    }

    @Override
    public byte[] prepareSend() throws Exception {
        String s;
        if (notify) {
            s = pt.getId() + ";" + player.toString() + ";" + invName + ";" + 1;
        } else {
            s = pt.getId() + ";" + player.toString() + ";" + invName + ";" + 0;
        }
        return s.getBytes();
    }

    @Override
    public void handle() {
        //TODO: HANDLE THE PACKET
    }
}
