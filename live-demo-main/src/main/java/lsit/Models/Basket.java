package lsit.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Basket {
    public List<UUID> clothesIds = new ArrayList<>();

    public void addClothes(UUID clothesId) {
        clothesIds.add(clothesId);
    }

    public void removeClothes(UUID clothesId) {
        clothesIds.remove(clothesId);
    }
    
}
