package me.thelpro.ttzombies.npc;

import me.thelpro.ttzombies.TTZombies;
import me.thelpro.ttzombies.filemanager.Storage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NPCManager {

    static TTZombies plugin = TTZombies.plugin;
    static FileConfiguration data = Storage.get();

    public static void spawnNPC(Location loc, Inventory inv, Player player) {

        World world = player.getWorld();
        String uuid = player.getUniqueId().toString();

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
        Material block = npc.getLocation().add(0, 1, 0).getBlock().getType();
        npc.getLocation().add(0, 1, 0).getBlock().setType(Material.BARRIER);
        npc.getEquipment().setHelmet(helmet);
        npc.getEquipment().setChestplate(chestplate);
        npc.getEquipment().setLeggings(leggings);
        npc.getEquipment().setBoots(boots);
        npc.getEquipment().setItemInMainHand(hand);

        data.set("MOB_" + uuid, npc);
        data.set("INV_" + uuid, inv);
        data.set("BLOCK_" + uuid, block);
    }
    public static void killZombie(Player player) {

        String uuid = player.getUniqueId().toString();

        Zombie npc = (Zombie) data.get("MOB_" + uuid);
        Inventory inv = (Inventory) data.get("INV_" + uuid);
        Material block = (Material) data.get("BLOCK_" + uuid);

        assert inv != null;
        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                assert npc != null;
                npc.getWorld().dropItem(npc.getLocation(), item);
            }
        }
        assert npc != null;
        assert block != null;
        npc.getLocation().add(0, 1, 0).getBlock().setType(block);
        npc.remove();

    }
}