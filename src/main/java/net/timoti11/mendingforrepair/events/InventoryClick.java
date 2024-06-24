package net.timoti11.mendingforrepair.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item != null && item.getType() != Material.AIR) {
            if (item.getEnchantments().containsKey(Enchantment.MENDING)) {
                item.removeEnchantment(Enchantment.MENDING);
            }
        }
    }
}