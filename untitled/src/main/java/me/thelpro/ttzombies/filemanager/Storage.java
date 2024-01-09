package me.thelpro.ttzombies.filemanager;

import me.thelpro.ttzombies.TTZombies;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Storage {

    static TTZombies plugin = TTZombies.plugin;
    public static FileConfiguration storage;
    public static File file;
    

    public static  void reload() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), "data.yml");
        }
        storage = YamlConfiguration.loadConfiguration(file);
        InputStream stream = plugin.getResource("data.yml");
        if (stream != null) {
            YamlConfiguration dconfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
            storage.setDefaults(dconfig);
        }
    }

    public static FileConfiguration get() {
        if (storage == null) {
            reload();
        }
        return storage;
    }

    public static void save() {
        if (storage == null || file == null)
            return;
        try {
            storage.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, e);
        }
    }
    public static void saveDefault() {
        if (file == null)
            file = new File(plugin.getDataFolder(), "data.yml");
        if (!file.exists())
            plugin.saveResource("data.yml", false);
    }
}