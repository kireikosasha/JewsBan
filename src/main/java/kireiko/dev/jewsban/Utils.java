package kireiko.dev.jewsban;

import org.bukkit.configuration.file.FileConfiguration;

public class Utils {
    public static FileConfiguration getCfg() {
        return JewsBan.getInstance().getConfig();
    }
    public static void log(Object o) {
        JewsBan.getInstance().getLogger().info(o.toString());
    }
}
