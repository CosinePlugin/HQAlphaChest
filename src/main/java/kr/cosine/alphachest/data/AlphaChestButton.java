package kr.cosine.alphachest.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class AlphaChestButton {

    private static final String INDEX = "%index%";

    private final Material material;

    private final String displayName;

    private final List<String> lore;

    public AlphaChestButton(Material material, String displayName, List<String> lore) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack toItemStack(int index) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = replace(this.displayName, index);
        itemMeta.setDisplayName(displayName);
        List<String> lore = this.lore
            .stream()
            .map(line -> replace(line, index))
            .collect(Collectors.toList());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private String replace(String text, int index) {
        return text.replace(INDEX, String.valueOf(index));
    }
}
