package kr.cosine.alphachest.manager;

import kr.cosine.alphachest.data.AlphaChestHolder;
import kr.cosine.alphachest.registry.AlphaChestHolderRegistry;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class AlphaChestManager {

    private final File alphaChestFolder;

    private final AlphaChestHolderRegistry alphaChestHolderRegistry;

    public AlphaChestManager(Plugin plugin, AlphaChestHolderRegistry alphaChestHolderRegistry) {
        alphaChestFolder = new File(plugin.getDataFolder(), "alpha-chest");
        if (!alphaChestFolder.exists()) {
            alphaChestFolder.mkdirs();
        }
        this.alphaChestHolderRegistry = alphaChestHolderRegistry;
    }

    public void loadAll() {
        File[] files = alphaChestFolder.listFiles();
        if (files == null) return;
        for (File file : files) {
            String fileName = file.getName();
            String uniqueIdText = fileName.substring(0, fileName.length() - 4);
            UUID uniqueId = UUID.fromString(uniqueIdText);
            AlphaChestHolder alphaChestHolder = new AlphaChestHolder(uniqueId);
            alphaChestHolder.load(alphaChestFolder);
            alphaChestHolderRegistry.setAlphaChestHolder(uniqueId, alphaChestHolder);
        }
    }

    public void saveAll() {
        alphaChestHolderRegistry.getAlphaChestHolderMap().forEach(((uniqueId, alphaChestHolder) ->
            alphaChestHolder.save(alphaChestFolder))
        );
    }
}
