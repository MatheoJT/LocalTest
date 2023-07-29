package fr.baxymoz.local;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player target = sender instanceof Player ? (Player) sender : null;

        if(args.length >= 1) {
            target = LocalTest.getInstance().getServer().getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage("§cThe player " + args[0] + " is not online.");
                return false;
            }
        }


        if(LocalTest.getInstance().songPlayerHashMap.containsKey(target)) {
            LocalTest.getInstance().songPlayerHashMap.get(target).setPlaying(false);
            LocalTest.getInstance().songPlayerHashMap.get(target).destroy();
            LocalTest.getInstance().songPlayerHashMap.remove(target);
        }

        return false;
    }
}
