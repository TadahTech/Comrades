package com.tadahtech.pub.comrades.network.packets;

import com.tadahtech.pub.comrades.network.Packet;
import com.tadahtech.pub.comrades.network.PacketType;

import java.util.UUID;

/**
 * Created by legop on 6/12/2016.
 */
public class InvClickPacket extends Packet {

    private UUID player;
    private int slot;

    public InvClickPacket(UUID playerId, int slot) {
        super(PacketType.INV_CLICK);
    }

    public byte[] prepareSend(){
        String s = pt.getId() + ";" + player.toString() + ";" + slot;
        return s.getBytes();
    }

    @Override
    public void handle() {
        //TODO: Handle the packet
    }
}
