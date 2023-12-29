package kr.cosine.alphachest.listener;

import kr.cosine.alphachest.view.AbstractContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class AlphaChestInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        AbstractContainer abstractContainer = getAbstractContainer(event.getView());
        if (abstractContainer == null) return;
        event.setCancelled(abstractContainer.isCancelled());
        abstractContainer.onClick(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        AbstractContainer abstractContainer = getAbstractContainer(event.getView());
        if (abstractContainer == null) return;
        abstractContainer.onClose(event);
    }

    private AbstractContainer getAbstractContainer(InventoryView inventoryView) {
        InventoryHolder inventoryHolder = inventoryView.getTopInventory().getHolder();
        if (inventoryHolder == null) return null;
        if (inventoryHolder instanceof AbstractContainer) {
            return (AbstractContainer) inventoryHolder;
        } else {
            return null;
        }
    }
}
