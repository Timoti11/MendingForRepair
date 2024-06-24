package net.timoti11.mendingforrepair.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EntityPickupItem implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            ItemStack item = event.getItem().getItemStack();

            if (item.getEnchantments().containsKey(Enchantment.MENDING)) {
                item.removeEnchantment(Enchantment.MENDING);
            }
        }
    }
}