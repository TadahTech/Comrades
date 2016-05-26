package com.tadahtech.pub.comrades.module.commands;

import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by legop on 5/23/2016.
 */
public class ModuleCommand implements CommandExecutor {

    private static final Map<String, IModuleCommand> commands = Maps.newHashMap();

    public ModuleCommand(JavaPlugin jp, String name){
        jp.getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){
        if (commands.containsKey(cmd.getName())){
            IModuleCommand mc = commands.get(cmd.getName());
            mc.execute(sender, args);
        }
        return true;
    }

    public static void registerCommand(IModuleCommand cmd){
        commands.put(cmd.getName(), cmd);
    }

    public Map<String, IModuleCommand> getCommands(){
        return commands;
    }

    public static final class ModuleHelpCommand implements IModuleCommand{

        private Map<String, IModuleCommand> commands;

        public ModuleHelpCommand(Map<String, IModuleCommand> commands){
            this.commands = commands;
            commands.put(getName(), this);
        }


        @Override
        public String getName() {
            return "help";
        }

        @Override
        public String getPermission() {
            return "";
        }

        @Override
        public String getUsage() {
            return "/<MODULE> help";
        }

        @Override
        public String getDescription() {
            return "A help command for each module.";
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            String trailing = "---------~---------~---------~---------~---------";
            String[] help = new String[(commands.size()*2) + 2];
            help[0] = trailing;
            int i = 1;
            for (String s : commands.keySet()){
                IModuleCommand cmd = commands.get(s);
                String h = "       " + s + "    -    " + cmd.getDescription();
                String h2 = "           " + cmd.getUsage();
                help[i] = h;
                help[i+1] =h2;
                i+=2;
            }
            help[commands.size()+2] = trailing;
            sender.sendMessage(help);
        }
    }

}
