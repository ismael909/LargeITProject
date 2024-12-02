package lsit.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lsit.Models.Clothes;
import lsit.Models.Order;
import lsit.Repositories.WarehouseRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    // Clothes Endpoints
    @PostMapping("/clothes")
    public String addClothes(@RequestBody Clothes clothes) {
        warehouseRepository.addClothes(clothes);
        return "Clothes added successfully!";
    }

    @GetMapping("/clothes/{id}")
    public Clothes getClothes(@PathVariable UUID id) {
        return warehouseRepository.getClothes(id);
    }

    @GetMapping("/clothes")
    public List<Clothes> listClothes() {
        return warehouseRepository.listClothes();
    }

    @PutMapping("/clothes")
    public String updateClothes(@RequestBody Clothes clothes) {
        warehouseRepository.updateClothes(clothes);
        return "Clothes updated successfully!";
    }

    @DeleteMapping("/clothes/{id}")
    public String deleteClothes(@PathVariable UUID id) {
        warehouseRepository.deleteClothes(id);
        return "Clothes deleted successfully!";
    }

    // Order Endpoints
    @PostMapping("/orders")
    public String addOrder(@RequestBody Order order) {
        warehouseRepository.addOrder(order);
        return "Order placed successfully!";
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable UUID id) {
        return warehouseRepository.getOrder(id);
    }

    @GetMapping("/orders")
    public List<Order> listOrders() {
        return warehouseRepository.listOrders();
    }

    public String packageOrder(@PathVariable UUID orderId) {
        warehouseRepository.packageOrder(orderId);
        return "Order packaged successfully!";
    }

    @PostMapping("/orders/{orderId}/ship")
    public String shipOrder(@PathVariable UUID orderId, @RequestParam String shipmentDate) {
        warehouseRepository.shipOrder(orderId, shipmentDate);
        return "Order shipped successfully!";
    }

    @PostMapping("/orders/{orderId}/confirm")
    public String confirmOrder(@PathVariable UUID orderId) {
        warehouseRepository.confirmOrder(orderId);
        return "Order confirmed successfully!";
    }
}

