package kr.cosine.alphachest.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlphaChestHolder {

    private static final String ITEM_SECTION_KEY = "item";

    private final UUID owner;

    public AlphaChestHolder(UUID owner) {
        this.owner = owner;
    }

    private final Map<Integer, AlphaChest> alphaChestMap = new HashMap<>();

    public boolean isOpened = false;

    public void load(File folder) {
        File file = new File(folder, owner.toString() + ".yml");
        if (!file.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection itemSection = config.getConfigurationSection(ITEM_SECTION_KEY);
        if (itemSection == null) return;
        for (String indexText : itemSection.getKeys(false)) {
            int index = Integer.parseInt(indexText);
            Map<Integer, ItemStack> itemStackMap = new HashMap<>();
            ConfigurationSection indexSection = itemSection.getConfigurationSection(indexText);
            for (String slotText : indexSection.getKeys(false)) {
                int slot = Integer.parseInt(slotText);
                ItemStack itemStack = indexSection.getItemStack(slotText);
                itemStackMap.put(slot, itemStack);
            }
            AlphaChest alphaChest = new AlphaChest(index, itemStackMap);
            alphaChestMap.put(index, alphaChest);
        }
    }

    public void save(File folder) {
        List<Map.Entry<Integer, AlphaChest>> alphaChestEntry = this.alphaChestMap.entrySet()
            .stream()
            .filter(entry -> entry.getValue().isChagned)
            .collect(Collectors.toList());
        if (!alphaChestEntry.isEmpty()) {
            File file = new File(folder, owner.toString() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            alphaChestEntry.forEach(entry -> {
                AlphaChest alphaChest = entry.getValue();
                String sectionKey = ITEM_SECTION_KEY + "." + entry.getKey();
                config.set(sectionKey, null);
                Map<Integer, ItemStack> itemStackMap = alphaChest.getItemStackMap();
                itemStackMap.forEach((slot, itemStack) -> config.set(sectionKey + "." + slot, itemStack));
                alphaChest.isChagned = false;
            });
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AlphaChest getAlphaChest(int index) {
        return alphaChestMap.computeIfAbsent(index, (_index) -> new AlphaChest(index));
    }
}
