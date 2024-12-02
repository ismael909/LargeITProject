package lsit.Controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Clothes;

import lsit.Models.Order;

import lsit.Repositories.GCSOrderRepository;

import lsit.Repositories.GCSClothesRepository;

@RestController
@RequestMapping("/clothes")
public class ClothesController {

    private final GCSClothesRepository clothesRepository;
    private final GCSOrderRepository orderRepository;

    public ClothesController(GCSClothesRepository clothesRepository, GCSOrderRepository orderRepository) {
        this.clothesRepository = clothesRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClothes(@RequestBody Clothes clothes) {
        try {
            clothesRepository.add(clothes);
            return ResponseEntity.ok("Clothes added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding clothes: " + e.getMessage());
        }
    }

    // @PostMapping("/order/add/{clothesId}/{clientId}")
    // @PreAuthorize("hasAnyRole('CUSTOMER','SALES')")
    // public ResponseEntity<String> addToOrder(@PathVariable UUID clothesId, @PathVariable UUID clientId) {
    //     Clothes clothes = clothesRepository.get(clothesId);

    //     if (clothes == null) {
    //         return ResponseEntity.badRequest().body("Clothes item not found.");
    //     }

    //     Order order = orderRepository.get(clientId);

    //     if (order == null) {
    //         order = new Order();
    //         order.setClientId(clientId);
    //     }

    //     order.getClothesIds().add(clothesId);
    //     orderRepository.update(order);

    //     return ResponseEntity.ok("Item added to order.");
    // }

    // @DeleteMapping("/order/remove/{clothesId}/{clientId}")
    // @PreAuthorize("hasAnyRole('CUSTOMER', 'SALES')")
    // public ResponseEntity<String> removeFromOrder(@PathVariable UUID clothesId, @PathVariable UUID clientId) {
    //     Order order = orderRepository.get(clientId);

    //     if (order == null || !order.getClothesIds().contains(clothesId)) {
    //         return ResponseEntity.badRequest().body("Order or item not found.");
    //     }

    //     order.getClothesIds().remove(clothesId);
    //     orderRepository.update(order);

    //     return ResponseEntity.ok("Item removed from order.");
    // }

    // @GetMapping("/order/{clientId}")
    // @PreAuthorize("hasAnyRole('CUSTOMER', 'SALES')")
    // public ResponseEntity<Order> getOrder(@PathVariable UUID clientId) {
    //     Order order = orderRepository.get(clientId);

    //     if (order == null || order.getClothesIds().isEmpty()) {
    //         return ResponseEntity.ok(new Order());
    //     }

    //     return ResponseEntity.ok(order);
    // }
}
