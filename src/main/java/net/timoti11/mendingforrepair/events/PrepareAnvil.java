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
        ItemStack resultItem = event.getResult();

        if (isItemInvalid(firstItem) || isItemInvalid(secondItem) || isItemInvalid(resultItem)) return;

        if (isDamageable(firstItem) && isEnchantedBookWithMending(secondItem)) {
            resultItem = repairItem(resultItem);
        }

        if (isResultMending(resultItem)) {
            resultItem = removeMending(resultItem);
        }

        event.setResult(resultItem);
    }

    private ItemStack repairItem(ItemStack resultItem) {
        Damageable resultItemMeta = (Damageable) resultItem.getItemMeta();

        resultItemMeta.setDamage(0);
        resultItem.setItemMeta(resultItemMeta);

        return resultItem;
    }

    private ItemStack removeMending(ItemStack resultItem) {
        resultItem.removeEnchantment(Enchantment.MENDING);

        return resultItem;
    }

    private boolean isItemInvalid(ItemStack item) {
        return item == null || item.getType() == Material.AIR || !item.hasItemMeta();
    }

    private boolean isDamageable(ItemStack item) {
        return item.getItemMeta() instanceof Damageable;
    }

    private boolean isResultMending(ItemStack resultItem) {
        return (resultItem.getEnchantments().containsKey(Enchantment.MENDING));
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