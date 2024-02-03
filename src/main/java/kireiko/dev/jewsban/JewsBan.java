package kireiko.dev.jewsban;

import lombok.Getter;
import net.mineland.core.api.AsyncScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JewsBan extends JavaPlugin {

    @Getter
    public static JewsBan instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        AsyncScheduler.run(() -> {
            Bukkit.getPluginManager().registerEvents(new JewsBanListener(), this);
        });

        Utils.log("JewsBan by pawsashatoy and yatwinkle");

    }

}
