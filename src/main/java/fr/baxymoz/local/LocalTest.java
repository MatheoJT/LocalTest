package fr.baxymoz.local;


import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.logging.Level;

public class LocalTest extends JavaPlugin {

    private static LocalTest instance;

    public final HashMap<Player, SongPlayer> songPlayerHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("LocalTest is enabled!");

        saveDefaultConfig();

        getCommand("play").setExecutor(new PlayCommand());
        getCommand("stop").setExecutor(new StopCommand());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("mount").setExecutor(new MountCommand());
        getCommand("laser").setExecutor(new LaserCommand());


        getServer().getPluginManager().registerEvents(new MountListener(), this);
        messageSender();

        instance = this;

        start();
    }
    public int day = 0;
    public int timer = 0;

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                timer++;
                if(timer == 280) {
                    day++;
                    Bukkit.getConsoleSender().sendMessage("§c WE REACHED DAY " + day + " !");
                    timer = 0;
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

    public void messageSender() {
        Bukkit.getScheduler().runTaskTimer(this, () -> getLogger().log(Level.SEVERE, "Day " + day + " - " + getClockTime()), 0, 0);
    }


    public String getClockTime() {
        double timeRatio = (double) timer / 280; // ratio entre le temps en jeu et le temps réel
        int inGameSeconds = (int) (timeRatio * 86400); // 86400 = 24h en secondes
        int hours = (inGameSeconds / 3600) % 24;
        int minutes = (inGameSeconds % 3600) / 60;
        String hourStr = (hours < 10) ? "0" + hours : String.valueOf(hours);
        String minuteStr = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        return hourStr + ":" + minuteStr;
    }

    @Override
    public void onDisable() {
        getLogger().info("LocalTest is disabled!");

    }

    public static LocalTest getInstance() {
        return instance;
    }

}
