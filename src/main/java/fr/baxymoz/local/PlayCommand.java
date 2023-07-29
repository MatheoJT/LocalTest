package fr.baxymoz.local;


import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player target = sender instanceof Player ? (Player) sender : null;
        if(args.length == 0) {
            sender.sendMessage("§cUsage: /play <song> [player]");
            return false;
        }

        if(args.length >= 2) {
            target = LocalTest.getInstance().getServer().getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage("§cThe player " + args[1] + " is not online.");
                return false;
            }
        }


        File file = new File(LocalTest.getInstance().getDataFolder(), args[0] + ".nbs");

        if(!file.exists()) {
            sender.sendMessage("§cThe song " + args[0] + " doesn't exist.");
            return false;
        }


        if(LocalTest.getInstance().songPlayerHashMap.containsKey(target)) {
            LocalTest.getInstance().songPlayerHashMap.get(target).setPlaying(false);
            LocalTest.getInstance().songPlayerHashMap.get(target).destroy();
            LocalTest.getInstance().songPlayerHashMap.remove(target);
        }

        Song song = NBSDecoder.parse(file);
        RadioSongPlayer radioSongPlayer = new RadioSongPlayer(song);
        assert target != null;
        radioSongPlayer.addPlayer(target);
        radioSongPlayer.setPlaying(true);

        LocalTest.getInstance().songPlayerHashMap.put(target, radioSongPlayer);

        return false;
    }
}
