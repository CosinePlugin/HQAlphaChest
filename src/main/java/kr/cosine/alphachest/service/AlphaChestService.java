package kr.cosine.alphachest.service;

import kr.cosine.alphachest.config.SettingConfig;
import kr.cosine.alphachest.data.AlphaChestHolder;
import kr.cosine.alphachest.manager.AlphaChestManager;
import kr.cosine.alphachest.registry.AlphaChestHolderRegistry;
import kr.cosine.alphachest.util.SchdulerUtils;
import kr.cosine.alphachest.view.AbstractContainer;
import kr.cosine.alphachest.view.AlphaChestMainView;
import kr.cosine.alphachest.view.AlphaChestView;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class AlphaChestService {

    private final SettingConfig settingConfig;
    private final AlphaChestManager alphaChestManager;
    private final AlphaChestHolderRegistry alphaChestHolderRegistry;

    public AlphaChestService(
        SettingConfig settingConfig,
        AlphaChestManager alphaChestManager,
        AlphaChestHolderRegistry alphaChestHolderRegistry
    ) {
        this.settingConfig = settingConfig;
        this.alphaChestManager = alphaChestManager;
        this.alphaChestHolderRegistry = alphaChestHolderRegistry;
    }

    public boolean openAlphaChestFromUser(Player player) {
        AlphaChestHolder alphaChestHolder = alphaChestHolderRegistry.getAlphaChestHolder(player.getUniqueId());
        if (alphaChestHolder.isOpened) return false;
        openAlphaChestMainView(alphaChestHolder, player, player.getName());
        return true;
    }

    public boolean openAlphaChestFromAdmin(Player player, OfflinePlayer targetOfflinePlayer) {
        AlphaChestHolder alphaChestHolder = alphaChestHolderRegistry.findAlphaChestHolder(targetOfflinePlayer.getUniqueId());
        if (alphaChestHolder == null) return false;
        Player target = targetOfflinePlayer.getPlayer();
        if (target != null) {
            closeAlphaChestView(target);
        }
        openAlphaChestMainView(alphaChestHolder, player, targetOfflinePlayer.getName());
        return true;
    }

    private void closeAlphaChestView(Player player) {
        InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
        if (holder instanceof AbstractContainer) {
            if (holder instanceof AlphaChestView) {
                ((AlphaChestView) holder).isPrevented = true;
            }
            player.closeInventory();
        }
    }

    private void openAlphaChestMainView(AlphaChestHolder alphaChestHolder, Player player, String targetName) {
        AlphaChestMainView alphaChestMainView = new AlphaChestMainView(alphaChestHolder, targetName, player);
        alphaChestMainView.open(player);
    }

    public void save() {
        SchdulerUtils.runAsync(alphaChestManager::saveAll);
    }

    public void reload() {
        settingConfig.reload();
    }
}
