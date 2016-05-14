package com.tadahtech.pub.comrades.module.test;

import com.tadahtech.pub.comrades.module.ComradesModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by legop on 5/13/2016.
 */
public class ModuleTest extends ComradesModule{
    public ModuleTest(JavaPlugin jp) {
        super(jp);
        super.plugin.getServer().getPluginManager().registerEvents(this, super.plugin);
        setName("Awesome Test Module");
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPurpose() {
        return "Send a message to a player on join, a test Module.";
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage(getName() + " is running!");
    }
}
