package me.thelpro.ttzombies;

import me.thelpro.ttzombies.events.Zombify;
import org.bukkit.plugin.java.JavaPlugin;

public final class TTZombies extends JavaPlugin {

    public static TTZombies plugin;

    @Override
    public void onEnable() {

        plugin = this;
        saveDefaultConfig();

        plugin.getServer().getPluginManager().registerEvents(new Zombify(), plugin);

    }

}