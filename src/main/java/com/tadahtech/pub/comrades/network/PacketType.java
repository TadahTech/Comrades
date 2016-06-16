package com.tadahtech.pub.comrades.network;

/**
 * Created by legop on 6/10/2016.
 */
public enum PacketType {

    JOIN(0),
    LEAVE(1),
    MSG(2),
    INV(3),
    INV_CLICK(4),
    ;

    int id;

    private PacketType(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static PacketType getById(int id){
        for (PacketType pt : values()){
            if (pt.getId() == id){
                return pt;
            }
        }
        return null;
    }
}
