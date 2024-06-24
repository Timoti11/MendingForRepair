package net.timoti11.mendingforrepair.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class PrepareAnvil implements Listener {

    @EventHandler
    private void onPrepareAnvil(final PrepareAnvilEvent event) {
        final AnvilInventory anvilInventory = event.getInventory();
        final ItemStack firstItem = anvilInventory.getItem(0);
        final ItemStack secondItem = anvilInventory.getItem(1);
        final ItemStack eventResultItem = event.getResult();

        if (isItemInvalid(firstItem) || isItemInvalid(secondItem)) return;


        if (isDamageable(firstItem) && isEnchantedBookWithMending(secondItem)) {
            repairItem(event, firstItem);
        }

        if (isItemInvalid(eventResultItem)) return;

        if (isMendingResult(eventResultItem)) {
            removeMending(eventResultItem, event);
        }
    }

    private void repairItem(PrepareAnvilEvent event, ItemStack firstItem) {
        if (!(firstItem.getItemMeta() instanceof Damageable)) return;

        ItemStack resultItem = event.getResult();
        Damageable resultItemMeta = (Damageable) resultItem.getItemMeta();

        if (resultItemMeta == null) return;

        resultItemMeta.setDamage(0);
        resultItemMeta.removeEnchant(Enchantment.MENDING);
        resultItem.setItemMeta(resultItemMeta);

        event.setResult(resultItem);
    }

    private void removeMending(ItemStack eventResultItem, PrepareAnvilEvent event) {
        eventResultItem.removeEnchantment(Enchantment.MENDING);

        event.setResult(eventResultItem);
    }

    private boolean isItemInvalid(ItemStack item) {
        return item == null || item.getType() == Material.AIR || !item.hasItemMeta();
    }

    private boolean isDamageable(ItemStack item) {
        return item.getItemMeta() instanceof Damageable;
    }

    private boolean isMendingResult(ItemStack eventResultItem) {
        return (eventResultItem.getEnchantments().containsKey(Enchantment.MENDING));
    }

    private boolean isEnchantedBookWithMending(ItemStack item) {
        if (item.getType() != Material.ENCHANTED_BOOK) {
            return false;
        }

        if (item.getItemMeta() instanceof EnchantmentStorageMeta bookMeta) {
            return bookMeta.hasStoredEnchant(Enchantment.MENDING);
        }

        return false;
    }
}