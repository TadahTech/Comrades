package com.tadahtech.pub.comrades.network;

import com.tadahtech.pub.comrades.network.packets.*;

import java.util.UUID;

/**
 * Created by legop on 6/10/2016.
 */
public class PacketProcessor {

    public static Packet getPacket(String s)throws Exception{
        String[] parts = s.split(";");
        int packetId = Integer.parseInt(parts[0]);
        PacketType pt = PacketType.getById(packetId);

        switch (pt){
            case JOIN:{
                if (Integer.parseInt(parts[2]) == 1){
                    return new JoinPacket(UUID.fromString(parts[1]), true);
                } else {
                    return new JoinPacket(UUID.fromString(parts[1]), false);
                }
            }
            case LEAVE:{
                if (Integer.parseInt(parts[2]) == 1){
                    return new LeavePacket(UUID.fromString(parts[1]), true);
                } else {
                    return new LeavePacket(UUID.fromString(parts[1]), false);
                }
            }
            case INV_CLICK:{
                return new InvClickPacket(UUID.fromString(parts[1]), Integer.parseInt(parts[2]));
            }
            case INV:{
                if (Integer.parseInt(parts[3]) == 1){
                    return new InvPacket(UUID.fromString(parts[1]), parts[2], true);
                } else {
                    return new InvPacket(UUID.fromString(parts[1]), parts[2], false);
                }
            }
            case MSG:{
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < parts.length; i++){
                    sb.append(parts[i]);
                }
                if (Integer.parseInt(parts[2]) == 1){
                    return new MsgPacket(UUID.fromString(parts[1]), true, sb.toString());
                } else {
                    return new MsgPacket(UUID.fromString(parts[1]), false, sb.toString());

                }
            }

            default:{
                return null;
            }
        }
    }

}
