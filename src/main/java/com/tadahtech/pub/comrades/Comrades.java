package com.tadahtech.pub.comrades;

import com.google.common.collect.Maps;
import com.tadahtech.pub.comrades.module.ComradesModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * @author Timothy Andis (TadahTech) on 4/15/2016.
 */
public class Comrades extends JavaPlugin {

    private static Comrades instance;

    private Map<Class<? extends ComradesModule>, ComradesModule> modules;

    @Override
    public void onEnable(){
        instance = this;
        this.modules = Maps.newHashMap();
    }

    public static Comrades getInstance() {
        return instance;
    }

    public void registerModule(ComradesModule module) {
        this.modules.putIfAbsent(module.getClass(), module);
    }

    public ComradesModule getModule(Class<? extends ComradesModule> clazz){
        return this.modules.get(clazz);
    }



}
