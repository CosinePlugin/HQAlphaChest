package kr.cosine.alphachest.view;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractContainer implements InventoryHolder {

    private Inventory baseInventory;

    private final int size;
    private final String title;
    private final boolean isCancelled;

    public AbstractContainer(int row, String title, boolean isCancelled) {
        int size = row * 9;
        this.title = title;
        this.size = size;
        this.isCancelled = isCancelled;
    }

    abstract protected void onCreate(Inventory inventory);

    public void onOpen(Player player) {}

    public void onClick(InventoryClickEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

    public int getSize() {
        return size;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void open(Player player) {
        player.openInventory(getInventory());
        onOpen(player);
    }

    @Override
    public Inventory getInventory() {
        if (baseInventory == null) {
            baseInventory = Bukkit.createInventory(this, size, title);
            onCreate(baseInventory);
        }
        return baseInventory;
    }
}
