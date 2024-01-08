package me.thelpro.ttzombies.events;

import me.thelpro.ttzombies.TTZombies;
import me.thelpro.ttzombies.npc.NPCManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Zombify implements Listener {

    static TTZombies plugin = TTZombies.plugin;

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER)) {
            Player killed = (Player) e.getEntity();
            if (killed.hasPermission("ttz.use") || !killed.hasPermission("ttz.special")) {
                Player killer = e.getEntity().getKiller();

                ItemStack item = new ItemStack(Material.NAME_TAG);
                ItemMeta meta = item.getItemMeta();
                meta.displayName(Component.text(killed.getName()));
                item.setItemMeta(meta);

                NPCManager.spawnNPC(killed.getLocation(), killed.getInventory(), killed);
                killed.getInventory().clear();
            } else if (killed.hasPermission("ttz.special")) {
                killed.sendMessage(
                        Component.text()
                                .content("You have a special curse")
                                .color(NamedTextColor.DARK_RED)
                                .decoration(TextDecoration.BOLD, true)
                                .append(Component.text(" —— "))
                                .color(NamedTextColor.WHITE)
                                .decoration(TextDecoration.BOLD, false)
                                .append(Component.text("You are immune to death"))
                                .color(NamedTextColor.DARK_RED)
                );
            }
        }
    }
    @EventHandler
    public void zombieDeathEvent
}