package lsit.Models;

import java.util.UUID;

public class Clothes {
    public UUID id;
    public String name;
    public ClothesKind kind;
    public int size;
    public int price;
    public enum ClothesKind{
        TSHIRT, SWEATER, SHIRT, JACKET, PANTS, BEANIE, GLOVES
    }
}
