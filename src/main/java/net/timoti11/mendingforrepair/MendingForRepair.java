package net.timoti11.mendingforrepair;

import net.timoti11.mendingforrepair.events.InventoryClick;
import net.timoti11.mendingforrepair.events.PrepareAnvil;
import net.timoti11.mendingforrepair.events.EntityPickupItem;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MendingForRepair extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("MendingForRepair is enabled");

        getServer().getPluginManager().registerEvents(new PrepareAnvil(), this);
        getServer().getPluginManager().registerEvents(new EntityPickupItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling MendingForRepair...");
    }
}