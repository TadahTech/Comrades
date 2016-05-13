package com.tadahtech.pub.comrades;

import com.google.common.collect.Maps;
import com.tadahtech.pub.comrades.module.IComradesModule;
import com.tadahtech.pub.comrades.module.ModuleLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * @author Timothy Andis (TadahTech) on 4/15/2016.
 */
public class Comrades extends JavaPlugin {

    private static Comrades instance;

    private boolean test = false;

    private Map<Class<? extends IComradesModule>, IComradesModule> modules;

    @Override
    public void onEnable(){
        instance = this;
        this.modules = Maps.newHashMap();
        if (test){
            try {
                ModuleLoader.loadModule("com.tadahtech.pub.comrades.module.test.ModuleTest");
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            ModuleLoader.loadModules();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Comrades getInstance() {
        return instance;
    }

    public void registerModule(IComradesModule module) {
        this.modules.putIfAbsent(module.getClass(), module);
    }

    public IComradesModule getModule(Class<? extends IComradesModule> clazz){
        return this.modules.get(clazz);
    }



}
