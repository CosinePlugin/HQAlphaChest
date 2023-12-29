package kr.cosine.alphachest.view;

import kr.cosine.alphachest.HQAlphaChest;
import kr.cosine.alphachest.data.AlphaChest;
import kr.cosine.alphachest.util.SchdulerUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlphaChestView extends AbstractContainer {

    private static final AlphaChestViewModel alphaChestViewModel = HQAlphaChest.getAlphaChestViewModel();

    private final AlphaChestMainView alphaChestMainView;

    private final AlphaChest alphaChest;

    public boolean isPrevented = false;

    public AlphaChestView(AlphaChestMainView alphaChestMainView, AlphaChest alphaChest, String targetName) {
        super(6, targetName + "님의 " + alphaChest.getIndex() + "번 창고", false);
        this.alphaChestMainView = alphaChestMainView;
        this.alphaChest = alphaChest;
    }

    @Override
    protected void onCreate(Inventory inventory) {
        alphaChest.getItemStackMap().forEach(inventory::setItem);
    }

    @Override
    public void onOpen(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 1f);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 0.5f, 1f);

        ItemStack[] contents = event.getInventory().getContents();
        alphaChestViewModel.setItemStackMap(alphaChest, contents);
        if (!isPrevented) {
            SchdulerUtils.runTaskLater(() -> alphaChestMainView.open(player));
        }
    }
}
