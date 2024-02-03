package kireiko.dev.jewsban;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

public class JewsBanListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            if (!Utils.getCfg().getBoolean("enable")) return;
            if (event.getPlayer().hasPermission("jewsban.bypass") && Utils.getCfg().getBoolean("bypass")) return;
            event.setCancelled(true);
            String message = Utils.getCfg().getString("message");
            if(Objects.requireNonNull(message).equalsIgnoreCase("none")) return;
            event.getPlayer().sendMessage(message);
        }
    }

}
