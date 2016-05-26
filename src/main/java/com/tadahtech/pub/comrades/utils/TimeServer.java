package com.tadahtech.pub.comrades.utils;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TimeServer {
    @WebMethod(action = "getTime")//No need to be precise to the nanosecond. Milliseconds will do just fine.
    public long getTime(){ return System.currentTimeMillis(); }
}