package lsit.Models;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id; // Unique ID for the order
    private UUID clientId; // ID of the client who placed the order
    private List<UUID> clothesIds; // List of clothes UUIDs in the order
    private double totalPrice; // Total price of the order

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public List<UUID> getClothesIds() {
        return clothesIds;
    }

    public void setClothesIds(List<UUID> clothesIds) {
        this.clothesIds = clothesIds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
