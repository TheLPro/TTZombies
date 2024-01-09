package me.thelpro.ttzombies;

import me.thelpro.ttzombies.events.Zombify;
import me.thelpro.ttzombies.filemanager.Storage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class TTZombies extends JavaPlugin {

    public static TTZombies plugin;
    public static FileConfiguration data;

    @Override
    public void onEnable() {

        plugin = this;
        Storage.saveDefault();
        Storage.reload();
        data = Storage.get();

        plugin.getServer().getPluginManager().registerEvents(new Zombify(), plugin);

    }

}