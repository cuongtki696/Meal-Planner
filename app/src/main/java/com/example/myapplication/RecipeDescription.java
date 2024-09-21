package com.example.myapplication;

import android.util.Log;

import java.util.List;

public class RecipeDescription {
    private int id;
    private String name;
    private String image;
    private String category;
    private String allergy;
    private String description;
    private List<String> ingredients;
    private double protein;  // New field for protein
    private double fat;      // New field for fat
    private double carbs;    // New field for carbs
    private String microNutrients; // New field for micronutrients
    private String instructions; // Field for instructions
    private String videoLink;
    private String fitnessAdvice;
    private String processSteps;

    public RecipeDescription(int id, String name, String category, String allergy, String description, double protein, double fat, double carbs, String microNutrients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.allergy = allergy;
        this.description = description;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.microNutrients = microNutrients;
    }

    public RecipeDescription(int id, String name, String image, String category, String allergy, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.category = category;
        this.allergy = allergy;
        this.description = description;
    }

    // Getter methods for nutrients
    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public String getMicroNutrients() {
        return microNutrients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAllergy() {
        return allergy;
    }

    public String getDescription() {
        return description;
    }

    public void loadIngredients(DatabaseHelper dbHelper) {
        this.ingredients = dbHelper.getRecipeIngredients(this.id);
        Log.d("loadIngredients", "Loaded ingredients for recipe ID " + this.id + ": " + this.ingredients);
    }

    public List<String> getRequiredIngredients() {
        return ingredients;
    }

    public String getIngredientsAsString() {
        if (ingredients == null || ingredients.isEmpty()) {
            return "No ingredients available.";
        }
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);
            if (i == ingredients.size() - 1) {
                ingredientsBuilder.append("- ").append(ingredient);
            } else {
                ingredientsBuilder.append("- ").append(ingredient).append("\n");
            }
        }

        return ingredientsBuilder.toString();
    }


    public String getInstructions() {
        return instructions != null ? instructions : "No instructions available.";
    }

    public int getId() {
        return id;
    }


    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getFitnessAdvice() {
        return fitnessAdvice;
    }

    public void setFitnessAdvice(String fitnessAdvice) {
        this.fitnessAdvice = fitnessAdvice;
    }

    public String getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(String processSteps) {
        this.processSteps = processSteps;
    }
}
