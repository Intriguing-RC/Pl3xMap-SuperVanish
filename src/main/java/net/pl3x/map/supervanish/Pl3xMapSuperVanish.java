package net.pl3x.map.supervanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pl3xMapSuperVanish extends JavaPlugin {
    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("SuperVanish") &&
                !getServer().getPluginManager().isPluginEnabled("PremiumVanish")) {
            getLogger().severe("SuperVanish or PremiumVanish not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!getServer().getPluginManager().isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onPlayerHide(de.myzelyam.api.vanish.PlayerHideEvent event) {
                net.pl3x.map.api.Pl3xMapProvider.get().playerManager()
                        .hidden(event.getPlayer().getUniqueId(), true);
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onPlayerShow(de.myzelyam.api.vanish.PlayerShowEvent event) {
                net.pl3x.map.api.Pl3xMapProvider.get().playerManager()
                        .hidden(event.getPlayer().getUniqueId(), false);
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onPlayerJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                if (de.myzelyam.api.vanish.VanishAPI.isInvisible(player)) {
                    net.pl3x.map.api.Pl3xMapProvider.get().playerManager()
                            .hide(player.getUniqueId());
                }
            }
        }, this);
    }
}
