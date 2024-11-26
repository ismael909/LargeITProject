package lsit.Repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import lsit.Models.Basket;
import lsit.Utils.FileUtil;

import java.io.IOException;
@Repository
public class BasketRepository {
    private Basket basket = new Basket();
    private static final String FILE_PATH = "basket.json";
    public Basket getBasket() {
        return basket;
    }

    public void addToBasket(UUID clothesId) {
        basket.addClothes(clothesId);
    }

    public void removeFromBasket(UUID clothesId) {
        basket.removeClothes(clothesId);
    }
    public void clearBasket() {
        basket.clothesIds.clear();
    }
    public void loadBasket() {
        try {
            Basket loadedBasket = FileUtil.readFromFile(FILE_PATH, Basket.class);
            if (loadedBasket != null) {
                basket = loadedBasket;
            }
        } catch (IOException e) {
            System.err.println("Failed to load basket: " + e.getMessage());
        }
    }

    public void saveBasket() {
        try {
            FileUtil.writeToFile(FILE_PATH, basket);
        } catch (IOException e) {
            System.err.println("Failed to save basket: " + e.getMessage());
        }
    }
}
