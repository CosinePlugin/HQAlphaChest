package kr.cosine.alphachest.registry;

import kr.cosine.alphachest.data.AlphaChestButton;
import kr.cosine.alphachest.enums.Button;

import java.util.HashMap;
import java.util.Map;

public class AlphaChestButtonRegistry {

    private final Map<Button, AlphaChestButton> alphaChestButtonMap = new HashMap<>();

    public AlphaChestButton getAlphaChestButton(Button button) {
        return alphaChestButtonMap.get(button);
    }

    public void setAlphaChestButton(Button button, AlphaChestButton alphaChestButton) {
        alphaChestButtonMap.put(button, alphaChestButton);
    }

    public void clear() {
        alphaChestButtonMap.clear();
    }
}
