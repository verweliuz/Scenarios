package me.verwelius.scenarios;

import me.verwelius.scenarios.listeners.AutoMelting;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Scenarios extends JavaPlugin {

    private final Map<String, Listener> scenarios = Map.of(
            "auto-melting", new AutoMelting()
    );

    @Override
    public void onEnable() {
        // Plugin startup logic
        Map<String, Boolean> active = new HashMap<>();

        PluginCommand command = getCommand("scenario");

        command.setExecutor((sender, cmd, label, args) -> {
            if(args.length != 2) return true;

            Listener scenario = scenarios.get(args[0]);
            if(scenario == null) return true;

            if(args[1].equalsIgnoreCase("on")) {
                if(active.getOrDefault(args[0], false)) return true;
                Bukkit.getPluginManager().registerEvents(scenario, this);
                active.put(args[0], true);
                sender.sendMessage("Enabled " + args[0]);
            }
            else if(args[1].equalsIgnoreCase("off")) {
                if(!active.getOrDefault(args[0], false)) return true;
                HandlerList.unregisterAll(scenario);
                active.put(args[0], false);
                sender.sendMessage("Disabled " + args[0]);
            }

            return true;
        });

        command.setTabCompleter((sender, cmd, label, args) -> {

            List<String> list = new ArrayList<>();

            if(args.length == 1) {
                list.addAll(scenarios.keySet());
            }
            else if(args.length == 2) {
                list.add("on");
                list.add("off");
            }

            return list.stream().filter(s -> s.startsWith(args[args.length - 1])).toList();
        });
    }

}
