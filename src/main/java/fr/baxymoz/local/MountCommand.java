package fr.baxymoz.local;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

public class MountCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Pig babyPig = player.getWorld().spawn(player.getLocation(), Pig.class);
        babyPig.setBaby();

        babyPig.setPassenger(player);


        return false;
    }
}
