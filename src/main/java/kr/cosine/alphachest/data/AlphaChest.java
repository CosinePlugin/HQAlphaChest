package kr.cosine.alphachest.data;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AlphaChest {

    private final int index;

    private Map<Integer, ItemStack> itemStackMap = new HashMap<>();

    public AlphaChest(int index) {
        this.index = index;
    }

    public AlphaChest(int index, Map<Integer, ItemStack> itemStackMap) {
        this.index = index;
        this.itemStackMap = itemStackMap;
    }

    public boolean isChagned = false;

    public boolean isEqual(Map<Integer, ItemStack> itemStackMap) {
        return getItemStackMap().equals(itemStackMap);
    }

    public Map<Integer, ItemStack> getItemStackMap() {
        return itemStackMap;
    }

    public void setItemStackMap(Map<Integer, ItemStack> itemStackMap) {
        this.itemStackMap = itemStackMap;
        isChagned = true;
    }

    public int getIndex() {
        return index;
    }
}
