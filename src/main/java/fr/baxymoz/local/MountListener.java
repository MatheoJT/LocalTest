package fr.baxymoz.local;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class MountListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Entity passenger = player.getPassenger();
        if (passenger != null) {
            double forward = player.getEyeLocation().getDirection().normalize().multiply(0.5).getY();
            passenger.setVelocity(passenger.getVelocity().add(new Vector(forward, 0, 0)));
        }
    }
}
