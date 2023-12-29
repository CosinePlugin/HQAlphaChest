package kr.cosine.alphachest.util;

import kr.cosine.alphachest.HQAlphaChest;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SchdulerUtils {

    private static final Plugin plugin = HQAlphaChest.getInstance();
    private static final BukkitScheduler scheduler = plugin.getServer().getScheduler();

    public static void runTaskLater(Runnable runnable) {
        scheduler.runTaskLater(plugin, runnable, 1);
    }

    public static void runSync(Runnable runnable) {
        scheduler.runTask(plugin, runnable);
    }

    public static void runAsync(Runnable runnable) {
        scheduler.runTaskAsynchronously(plugin, runnable);
    }
}
