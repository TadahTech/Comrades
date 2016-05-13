package com.tadahtech.pub.comrades.module;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by legop on 5/13/2016.
 */
public abstract class ComradesModule implements IComradesModule, Listener {

    public String name;

    public ComradesModule(JavaPlugin jp){

    }
}
