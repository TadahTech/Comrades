package com.tadahtech.pub.comrades.module.commands;

import org.bukkit.command.CommandSender;

/**
 * Created by legop on 5/23/2016.
 */
public interface IModuleCommand {

    String getName();

    String getPermission();

    String getUsage();

    String getDescription();

    void execute(CommandSender cmd, String[] args);


}
