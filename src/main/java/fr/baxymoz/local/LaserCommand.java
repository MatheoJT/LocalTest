package fr.baxymoz.local;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Collections;

public class LaserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        ArmorStand armorStand = player.getWorld().spawn(player.getLocation().clone().add(0,200,0), ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setVisible(false);

        new Laser(player.getLocation(), Collections.singletonList(player), 5);

        return false;
    }
}
