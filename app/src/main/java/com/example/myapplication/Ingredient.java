package com.example.myapplication;

public class Ingredient {
    private final String name;
    private final int quantity;
    private final String unit;
    private final String usedByDate;

    // Constructor
    public Ingredient(String name, int quantity, String unit, String usedByDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.usedByDate = usedByDate;
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

    public String getUsedByDate() {
        return usedByDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
