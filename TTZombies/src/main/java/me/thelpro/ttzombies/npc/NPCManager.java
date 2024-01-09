package me.thelpro.ttzombies.npc;

import me.thelpro.ttzombies.TTZombies;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NPCManager {

    static TTZombies plugin = TTZombies.plugin;
    static FileConfiguration data = plugin.getConfig();

    public static void spawnNPC(Player player) {

        World world = player.getWorld();
        String uuid = player.getUniqueId().toString();
        Inventory inv = player.getInventory();
        Location loc = player.getLocation();

        ItemStack helmet = player.getEquipment().getHelmet();
        ItemStack chestplate = player.getEquipment().getChestplate();
        ItemStack leggings = player.getEquipment().getLeggings();
        ItemStack boots = player.getEquipment().getBoots();
        ItemStack hand = player.getEquipment().getItemInMainHand();

        Zombie npc = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
        TextComponent textComponent = Component.text(player.getName());
        npc.customName(textComponent);
        npc.setCustomNameVisible(true);
        npc.setAI(false);
        npc.setRemoveWhenFarAway(false);

        Material block = npc.getLocation().add(0, 50, 0).getBlock().getType();
        npc.getLocation().add(0, 50, 0).getBlock().setType(Material.BEDROCK);
        npc.getEquipment().setHelmet(helmet);
        npc.getEquipment().setChestplate(chestplate);
        npc.getEquipment().setLeggings(leggings);
        npc.getEquipment().setBoots(boots);
        npc.getEquipment().setItemInMainHand(hand);

        ArrayList<Object> playerData = new ArrayList<Object>();
        playerData.add(npc);
        playerData.add(inv);
        playerData.add(block);

        data.set(uuid, playerData);
        plugin.saveConfig();
    }
    public static void killZombie(Player player) {

        String uuid = player.getUniqueId().toString();

        ArrayList<Object> playerData = (ArrayList<Object>) data.get(uuid);
        assert playerData != null;
        Zombie npc = (Zombie) playerData.get(0);
        Inventory inv = (Inventory) playerData.get(1);
        Material block = (Material) playerData.get(2);

        assert inv != null;
        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                assert npc != null;
                npc.getWorld().dropItem(npc.getLocation(), item);
            }
        }
        assert npc != null;
        assert block != null;
        npc.getLocation().add(0, 50, 0).getBlock().setType(block);
        npc.remove();

        Bukkit.getServer().broadcast(Component.text(player.getName() + "s Zombie has been killed.", NamedTextColor.RED, TextDecoration.BOLD));
    }
}