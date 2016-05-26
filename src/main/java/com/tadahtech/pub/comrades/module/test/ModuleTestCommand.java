package com.tadahtech.pub.comrades.module.test;

import com.tadahtech.pub.comrades.module.commands.IModuleCommand;
import org.bukkit.command.CommandSender;

/**
 * Created by legop on 5/23/2016.
 */
public class ModuleTestCommand implements IModuleCommand {
    @Override
    public String getName() {
        return "display";
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getUsage() {
        return "/cmd display";
    }

    @Override
    public String getDescription() {
        return "Displays to the user that a module is running.";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("A module is running!");
    }
}
