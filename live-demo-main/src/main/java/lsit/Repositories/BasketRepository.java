package lsit.Repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import lsit.Models.Basket;

@Repository
public class BasketRepository {
    private Basket basket = new Basket();

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
}
