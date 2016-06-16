package com.tadahtech.pub.comrades.network;

/**
 * Created by legop on 6/10/2016.
 */
public interface ComradesPacket {

    void send(byte[] bytes) throws Exception;

    byte[] prepareSend() throws Exception;

    void handle();

}
