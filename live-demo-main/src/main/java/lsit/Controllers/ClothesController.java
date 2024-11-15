package lsit.Controllers;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Clothes;
import lsit.Repositories.ClothesRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lsit.Models.Basket;
import lsit.Repositories.BasketRepository;

import java.util.UUID;

@RestController
public class ClothesController {

    private  ClothesRepository ClothesRepository;
    private  BasketRepository basketRepository;

    public ClothesController(ClothesRepository clothesRepository, BasketRepository basketRepository) {
        this.ClothesRepository = clothesRepository;
        this.basketRepository = basketRepository;
    }

    @PostMapping("/basket/add/{clothesId}")
    public ResponseEntity<String> addToBasket(@PathVariable UUID clothesId) {
        Clothes clothes = ClothesRepository.get(clothesId);
        if (clothes == null) {
            return ResponseEntity.badRequest().body("Clothes item not found.");
        }
        basketRepository.addToBasket(clothesId);
        return ResponseEntity.ok("Item added to basket.");
    }

    @DeleteMapping("/basket/remove/{clothesId}")
    public ResponseEntity<String> removeFromBasket(@PathVariable UUID clothesId) {
        basketRepository.removeFromBasket(clothesId);
        return ResponseEntity.ok("Item removed from basket.");
    }

    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket() {
        Basket basket = basketRepository.getBasket();
        if (basket == null || basket.clothesIds.isEmpty()) {
            return ResponseEntity.ok(new Basket());
        }
        return ResponseEntity.ok(basket);
    }

    @PostMapping("/order")
    public ResponseEntity<String> order() {
        Basket basket = basketRepository.getBasket();
        if (basket == null || basket.clothesIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Basket is empty. Please add items to the basket before ordering.");
        }

        // Check availability of each item
        StringBuilder unavailableItems = new StringBuilder();
        for (UUID clothesId : basket.clothesIds) {
            if (ClothesRepository.get(clothesId) == null) {
                unavailableItems.append("Item with ID ").append(clothesId).append(" is out of stock.\n");
            }
        }

        if (unavailableItems.length() > 0) {
            return ResponseEntity.badRequest().body(unavailableItems.toString());
        }

        // If all items are available, process the order
        return ResponseEntity.ok("Order placed successfully!");
    }


    @GetMapping("/clothes")
    public List<Clothes> list(){
        return ClothesRepository.list();
    }

    @GetMapping("/clothes/{id}")
    public Clothes get(@PathVariable("id") UUID id){
        return ClothesRepository.get(id);
    }

    @PostMapping("/clothes")
    public Clothes add(@RequestBody Clothes p){
        ClothesRepository.add(p);
        return p;
    }

    @PutMapping("/clothes/{id}")
    public Clothes update(@PathVariable("id") UUID id, @RequestBody Clothes p){
        p.id = id;
        ClothesRepository.update(p);
        return p;
    }

    @DeleteMapping("/clothes/{id}")
    public void delete(@PathVariable("id") UUID id){
        ClothesRepository.remove(id);
    }
 }
