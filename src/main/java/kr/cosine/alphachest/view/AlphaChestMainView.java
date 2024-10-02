package kr.cosine.alphachest.view;

import kr.cosine.alphachest.HQAlphaChest;
import kr.cosine.alphachest.data.AlphaChest;
import kr.cosine.alphachest.data.AlphaChestButton;
import kr.cosine.alphachest.data.AlphaChestHolder;
import kr.cosine.alphachest.enums.Button;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlphaChestMainView extends AbstractContainer {

    private static final AlphaChestViewModel alphaChestViewModel = HQAlphaChest.getAlphaChestViewModel();

    private final AlphaChestHolder alphaChestHolder;
    private final String targetName;
    private final Player viewer;
    private final boolean isOp;

    public AlphaChestMainView(AlphaChestHolder alphaChestHolder, String targetName, Player viewer) {
        super(1, targetName + "님의 창고", true);
        this.alphaChestHolder = alphaChestHolder;
        this.targetName = targetName;
        this.viewer = viewer;
        this.isOp = viewer.isOp();
        alphaChestHolder.isOpened = true;
    }

    private boolean isPrevented = false;

    @Override
    protected void onCreate(Inventory inventory) {
        for (int slot : alphaChestViewModel.slots) {
            int index = slot + 1;
            AlphaChestButton alphaChestButton;
            if (isOp) {
                alphaChestButton = alphaChestViewModel.getAlphaChestButton(Button.UNLOCKED);
            } else {
                alphaChestButton = alphaChestViewModel.getAlphaChestButtonByPermission(viewer, index);
            }
            ItemStack itemStack = alphaChestButton.toItemStack(index);
            inventory.setItem(slot, itemStack);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClick() != ClickType.LEFT) return;

        int slot = event.getRawSlot();
        if (slot >= getSize()) return;

        Player player = (Player) event.getWhoClicked();
        int index = slot + 1;

        if (isOp || alphaChestViewModel.hasPermission(player, index)) {
            isPrevented = true;
            AlphaChest alphaChest = alphaChestHolder.getAlphaChest(index);
            AlphaChestView alphaChestView = new AlphaChestView(this, alphaChest, targetName);
            alphaChestView.open(player);
            return;
        }
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.5f, 1f);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        alphaChestHolder.isOpened = false;
        if (isPrevented) {
            isPrevented = false;
        }
    }
}
