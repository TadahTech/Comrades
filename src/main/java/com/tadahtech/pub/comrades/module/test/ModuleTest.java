package com.tadahtech.pub.comrades.module.test;

import com.tadahtech.pub.comrades.module.ComradesModule;
import com.tadahtech.pub.comrades.module.commands.ModuleCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by legop on 5/13/2016.
 */
public class ModuleTest extends ComradesModule{
    public ModuleTest(JavaPlugin jp) {
        super(jp);
        start();
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

    @Override
    public void start() {
        super.plugin.getServer().getPluginManager().registerEvents(this, super.plugin);
        setName("Test");
        super.cmd = new ModuleCommand(super.plugin, super.name);
        super.cmd.registerCommand(new ModuleTestCommand());
        new ModuleCommand.ModuleHelpCommand(super.cmd.getCommands());
    }

    @Override
    public void cleanup() {
        //RESET ANY LISTS OR MAPS HERE
    }

    @Override
    public void end() {
        cleanup();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage(getName() + " is running!");
    }
}
