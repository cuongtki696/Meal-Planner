package com.example.myapplication;

public class NutrientAverage {
    private double protein;
    private double fat;
    private double carbs;

    public NutrientAverage(double protein, double fat, double carbs) {
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }
}
