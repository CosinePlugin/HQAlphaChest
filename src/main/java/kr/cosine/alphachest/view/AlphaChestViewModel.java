package kr.cosine.alphachest.view;

import kr.cosine.alphachest.data.AlphaChest;
import kr.cosine.alphachest.data.AlphaChestButton;
import kr.cosine.alphachest.enums.Button;
import kr.cosine.alphachest.enums.Permission;
import kr.cosine.alphachest.registry.AlphaChestButtonRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaChestViewModel {

    protected final List<Integer> slots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);

    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    private final AlphaChestButtonRegistry alphaChestButtonRegistry;

    public AlphaChestViewModel(Plugin plugin, AlphaChestButtonRegistry alphaChestButtonRegistry) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        this.alphaChestButtonRegistry = alphaChestButtonRegistry;
    }

    public AlphaChestButton getAlphaChestButton(Button button) {
        return alphaChestButtonRegistry.getAlphaChestButton(button);
    }

    public AlphaChestButton getAlphaChestButtonByPermission(Player player, int index) {
        if (hasPermission(player, index)) {
            return getAlphaChestButton(Button.UNLOCKED);
        } else {
            return getAlphaChestButton(Button.LOCKED);
        }
    }

    public boolean hasPermission(Player player, int index) {
        String permission = Permission.CHEST.getPermission(index);
        return player.hasPermission(permission);
    }

    public void setItemStackMap(AlphaChest alphaChest, ItemStack[] contents) {
        Map<Integer, ItemStack> itemStackMap = toMap(contents);
        if (!alphaChest.isEqual(itemStackMap)) {
            alphaChest.setItemStackMap(itemStackMap);
        }
    }

    private Map<Integer, ItemStack> toMap(ItemStack[] itemStacks) {
        Map<Integer, ItemStack> itemStackMap = new HashMap<>();
        for (int loop = 0; loop < itemStacks.length; loop++) {
            ItemStack itemStack = itemStacks[loop];
            if (itemStack == null) continue;
            itemStackMap.put(loop, itemStack);
        }
        return itemStackMap;
    }
}
