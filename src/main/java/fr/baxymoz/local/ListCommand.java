package fr.baxymoz.local;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListCommand implements CommandExecutor {

    private final HashMap<Player, Integer> pageHashMap = new HashMap<>();
    private final List<String> filesName = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //display list of files name in the plugin folder 10 per pages with buttons to change pages dans /list <page>

        final int MAX_PER_PAGE = 10;
        int page = 1;

        if(args.length >= 1) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cThe page " + args[0] + " is not a number.");
                return false;
            }
        }

        if(page < 1) {
            sender.sendMessage("§cThe page " + page + " is not valid.");
            return false;
        }

        if(pageHashMap.containsKey((Player) sender)) {
            pageHashMap.replace((Player) sender, page);
        } else {
            pageHashMap.put((Player) sender, page);
        }

        if(filesName.isEmpty()) {
            for (String fileName : Objects.requireNonNull(LocalTest.getInstance().getDataFolder().list())) {
                if(fileName.endsWith(".nbs")) {
                    filesName.add(fileName.substring(0, fileName.length() - 4));
                }
            }
        }


        int maxPage = (int) Math.ceil((double) filesName.size() / MAX_PER_PAGE);

        if(page > maxPage) {
            sender.sendMessage("§cThe page " + page + " is not valid.");
            return false;
        }

        sender.sendMessage("§6§lList of songs:");
        for (int i = (page - 1) * MAX_PER_PAGE; i < page * MAX_PER_PAGE; i++) {
            if(i >= filesName.size()) {
                break;
            }
            sender.sendMessage("§e" + filesName.get(i));
        }

        if(page > 1) {
            //using chatComponent to add a button to change page
            TextComponent textComponent = new TextComponent("§6§lPrevious page: §e/list " + (page - 1));
            textComponent.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/list " + (page - 1)));
            textComponent.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("§e/list " + (page - 1))}));
            ((Player) sender).spigot().sendMessage(textComponent);
        }

        if(page < maxPage) {
            TextComponent textComponent = new TextComponent("§6§lNext page: §e/list " + (page + 1));
            textComponent.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/list " + (page + 1)));
            textComponent.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("§e/list " + (page + 1))}));
            ((Player) sender).spigot().sendMessage(textComponent);
        }



        return false;
    }
}
