package me.verwelius.scenarios;

import me.verwelius.scenarios.listeners.AutoMelting;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Scenarios extends JavaPlugin {

    private final Map<String, Listener> scenarios = Map.of(
            "auto-melting", new AutoMelting()
    );

    @Override
    public void onEnable() {
        // Plugin startup logic
        Map<String, Boolean> active = new HashMap<>();

        getCommand("scenario").setExecutor((sender, cmd, label, args) -> {
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
    }

}
