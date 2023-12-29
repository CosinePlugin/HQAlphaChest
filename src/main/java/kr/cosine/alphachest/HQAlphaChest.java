package kr.cosine.alphachest;

import kr.cosine.alphachest.command.AlphaChestAdminCommand;
import kr.cosine.alphachest.command.AlphaChestUserCommand;
import kr.cosine.alphachest.config.SettingConfig;
import kr.cosine.alphachest.listener.AlphaChestInventoryListener;
import kr.cosine.alphachest.manager.AlphaChestManager;
import kr.cosine.alphachest.registry.AlphaChestButtonRegistry;
import kr.cosine.alphachest.registry.AlphaChestHolderRegistry;
import kr.cosine.alphachest.scheduler.AlphaChestSaveScheduler;
import kr.cosine.alphachest.service.AlphaChestService;
import kr.cosine.alphachest.view.AlphaChestViewModel;
import org.bukkit.plugin.java.JavaPlugin;

public class HQAlphaChest extends JavaPlugin {

    private static HQAlphaChest plugin;

    private static AlphaChestViewModel alphaChestViewModel;

    private AlphaChestManager alphaChestManager;

    private AlphaChestSaveScheduler alphaChestSaveScheduler;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        AlphaChestButtonRegistry alphaChestButtonRegistry = new AlphaChestButtonRegistry();
        AlphaChestHolderRegistry alphaChestHolderRegistry = new AlphaChestHolderRegistry();

        SettingConfig settingConfig = new SettingConfig(this, alphaChestButtonRegistry);
        settingConfig.load();

        alphaChestManager = new AlphaChestManager(this, alphaChestHolderRegistry);
        alphaChestManager.loadAll();

        AlphaChestService alphaChestService = new AlphaChestService(settingConfig, alphaChestManager, alphaChestHolderRegistry);
        alphaChestViewModel = new AlphaChestViewModel(this, alphaChestButtonRegistry);

        getServer().getPluginManager().registerEvents(new AlphaChestInventoryListener(), this);
        getCommand("창고").setExecutor(new AlphaChestUserCommand(alphaChestService));
        getCommand("창고관리").setExecutor(new AlphaChestAdminCommand(alphaChestService));

        long autoSavePeriod = settingConfig.getAutoSavePeriod();
        alphaChestSaveScheduler = new AlphaChestSaveScheduler(alphaChestManager);
        alphaChestSaveScheduler.runTaskTimerAsynchronously(this, autoSavePeriod, autoSavePeriod);
    }

    @Override
    public void onDisable() {
        alphaChestManager.saveAll();
        alphaChestSaveScheduler.cancel();
    }

    public static HQAlphaChest getInstance() {
        return plugin;
    }

    public static AlphaChestViewModel getAlphaChestViewModel() {
        return alphaChestViewModel;
    }
}
