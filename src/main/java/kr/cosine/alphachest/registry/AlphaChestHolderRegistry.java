package kr.cosine.alphachest.registry;

import kr.cosine.alphachest.data.AlphaChestHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AlphaChestHolderRegistry {

    private final Map<UUID, AlphaChestHolder> alphaChestHolderMap = new HashMap<>();

    public AlphaChestHolder findAlphaChestHolder(UUID uniqueId) throws NullPointerException {
        return alphaChestHolderMap.get(uniqueId);
    }

    public AlphaChestHolder getAlphaChestHolder(UUID uniqueId) {
        return alphaChestHolderMap.computeIfAbsent(uniqueId, (_uniqueId) -> new AlphaChestHolder(uniqueId));
    }

    public void setAlphaChestHolder(UUID uniqueId, AlphaChestHolder alphaChestHolder) {
        alphaChestHolderMap.put(uniqueId, alphaChestHolder);
    }

    public Map<UUID, AlphaChestHolder> getAlphaChestHolderMap() {
        return alphaChestHolderMap;
    }
}
