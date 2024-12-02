package lsit.Repositories;


import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.Clothes;
import lsit.Models.Order;

@Repository
public class WarehouseRepository {

    private static final String CLOTHES_FILE_PATH = "clothes.json";
    private static final String ORDERS_FILE_PATH = "orders.json";

    private static HashMap<UUID, Clothes> clothesMap = new HashMap<>();
    private static HashMap<UUID, Order> ordersMap = new HashMap<>();

    @PostConstruct
    public void initData() {
        loadClothes();
        loadOrders();
    }

    // Clothes operations
    public void addClothes(Clothes clothes) {
        clothes.setId(UUID.randomUUID());
        clothesMap.put(clothes.getId(), clothes);
        saveClothes();
    }

    public Clothes getClothes(UUID id) {
        return clothesMap.get(id);
    }

    public void updateClothes(Clothes updatedClothes) {
        Clothes existingClothes = clothesMap.get(updatedClothes.getId());
        if (existingClothes != null) {
            existingClothes.setName(updatedClothes.getName());
            existingClothes.setKind(updatedClothes.getKind());
            existingClothes.setSize(updatedClothes.getSize());
            existingClothes.setPrice(updatedClothes.getPrice());
        }
        saveClothes();
    }

    public void deleteClothes(UUID id) {
        clothesMap.remove(id);
        saveClothes();
    }

    public List<Clothes> listClothes() {
        return new ArrayList<>(clothesMap.values());
    }

    // Order operations
    public void addOrder(Order order) {
        order.setId(UUID.randomUUID());
        ordersMap.put(order.getId(), order);
        saveOrders();
    }

    public Order getOrder(UUID id) {
        return ordersMap.get(id);
    }

    public List<Order> listOrders() {
        return new ArrayList<>(ordersMap.values());
    }

    // Persistence operations
    private void loadClothes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Clothes> clothesList = objectMapper.readValue(
                new File(CLOTHES_FILE_PATH), new TypeReference<List<Clothes>>() {});
            clothesList.forEach(clothes -> clothesMap.put(clothes.getId(), clothes));
        } catch (IOException e) {
            System.err.println("Failed to load clothes: " + e.getMessage());
        }
    }

    private void saveClothes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(CLOTHES_FILE_PATH), clothesMap.values());
        } catch (IOException e) {
            System.err.println("Failed to save clothes: " + e.getMessage());
        }
    }

    private void loadOrders() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Order> orderList = objectMapper.readValue(
                new File(ORDERS_FILE_PATH), new TypeReference<List<Order>>() {});
            orderList.forEach(order -> ordersMap.put(order.getId(), order));
        } catch (IOException e) {
            System.err.println("Failed to load orders: " + e.getMessage());
        }
    }

    private void saveOrders() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(ORDERS_FILE_PATH), ordersMap.values());
        } catch (IOException e) {
            System.err.println("Failed to save orders: " + e.getMessage());
        }
    }

    public void packageOrder(UUID orderId) {
        Order order = ordersMap.get(orderId);
        if (order != null && order.getStatus() == Order.OrderStatus.PLACED) {
            order.setStatus(Order.OrderStatus.PACKAGED);
            saveOrders();
        } else {
            throw new IllegalStateException("Order cannot be packaged. Current status: " + order.getStatus());
        }
    }

    public void shipOrder(UUID orderId, String shipmentDate) {
        Order order = ordersMap.get(orderId);
        if (order != null && order.getStatus() == Order.OrderStatus.PACKAGED) {
            order.setStatus(Order.OrderStatus.SHIPPED);
            order.setShipmentDate(shipmentDate);
            saveOrders();
        } else {
            throw new IllegalStateException("Order cannot be shipped. Current status: " + order.getStatus());
        }
    }

    public void confirmOrder(UUID orderId) {
        Order order = ordersMap.get(orderId);
        if (order != null && order.getStatus() == Order.OrderStatus.SHIPPED) {
            order.setStatus(Order.OrderStatus.CONFIRMED);
            saveOrders();
        } else {
            throw new IllegalStateException("Order cannot be confirmed. Current status: " + order.getStatus());
        }
    }
}

