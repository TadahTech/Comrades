package com.tadahtech.pub.comrades.module;

/**
 * @author Timothy Andis (TadahTech) on 5/12/2016.
 */
public interface IComradesModule {

    void setName(String name);

    String getName();

    String getPurpose();

    void start();

    void cleanup();

    void end();

}
