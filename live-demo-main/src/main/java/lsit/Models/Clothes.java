package lsit.Models;

import java.util.UUID;

public class Clothes {

    private UUID id;
    private String name;
    private ClothesKind kind;
    private int size;
    private int price;

    public Clothes() {}

    public Clothes(UUID id, String name, ClothesKind kind, int size, int price) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.size = size;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClothesKind getKind() {
        return kind;
    }

    public void setKind(ClothesKind kind) {
        this.kind = kind;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Enum for Clothes Kind
    public enum ClothesKind {
        TSHIRT, SWEATER, SHIRT, JACKET, PANTS, BEANIE, GLOVES
    }

    // ToString method for better debugging
    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kind=" + kind +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}
