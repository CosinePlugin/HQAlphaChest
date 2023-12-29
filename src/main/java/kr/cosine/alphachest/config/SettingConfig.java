package kr.cosine.alphachest.config;

import kr.cosine.alphachest.data.AlphaChestButton;
import kr.cosine.alphachest.enums.Button;
import kr.cosine.alphachest.registry.AlphaChestButtonRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SettingConfig {

    private final Logger logger;

    private final File file;
    private final YamlConfiguration config;

    private final AlphaChestButtonRegistry alphaChestButtonRegistry;

    public SettingConfig(Plugin plugin, AlphaChestButtonRegistry alphaChestButtonRegistry) {
        logger = plugin.getLogger();
        String path = "config.yml";
        File newFile = new File(plugin.getDataFolder(), path);
        if (!newFile.exists() && plugin.getResource(path) != null) {
            plugin.saveResource(path, false);
        }
        file = newFile;
        config = YamlConfiguration.loadConfiguration(newFile);
        this.alphaChestButtonRegistry = alphaChestButtonRegistry;
    }

    public void load() {
        String inventorySectionKey = "inventory";
        ConfigurationSection inventorySection = config.getConfigurationSection(inventorySectionKey);
        if (inventorySection == null) return;
        for (String buttonText : inventorySection.getKeys(false)) {
            Button button = Button.valueOf(buttonText.toUpperCase());
            ConfigurationSection buttonSection = inventorySection.getConfigurationSection(buttonText);
            String materialText = buttonSection.getString("material");
            Material material = Material.getMaterial(materialText);
            if (material == null) {
                logger.warning( "(" + inventorySectionKey + "." + buttonText + ".material) " + materialText + "은(는) 존재하지 않는 Material입니다.");
                continue;
            }
            String displayName = applyColor(buttonSection.getString("display-name", "&6&l[ %index%번 ] &f&l창고"));
            List<String> lore = buttonSection.getStringList("lore")
                .stream()
                .map(this::applyColor)
                .collect(Collectors.toList());
            AlphaChestButton alphaChestButton = new AlphaChestButton(material, displayName, lore);
            alphaChestButtonRegistry.setAlphaChestButton(button, alphaChestButton);
        }
    }

    private String applyColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public void reload() {
        try {
            config.load(file);
            alphaChestButtonRegistry.clear();
            load();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public long getAutoSavePeriod() {
        return config.getLong("auto-save-period");
    }
}
