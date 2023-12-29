package kr.cosine.alphachest.scheduler;

import kr.cosine.alphachest.manager.AlphaChestManager;
import org.bukkit.scheduler.BukkitRunnable;

public class AlphaChestSaveScheduler extends BukkitRunnable {

    private final AlphaChestManager alphaChestManager;

    public AlphaChestSaveScheduler(AlphaChestManager alphaChestManager) {
        this.alphaChestManager = alphaChestManager;
    }

    @Override
    public void run() {
        alphaChestManager.saveAll();
    }
}
