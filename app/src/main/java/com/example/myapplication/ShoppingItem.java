package com.example.myapplication;

public class ShoppingItem {
    private final String name;
    private final int quantity;
    private final String unit;


    // Constructor
    public ShoppingItem(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

}
