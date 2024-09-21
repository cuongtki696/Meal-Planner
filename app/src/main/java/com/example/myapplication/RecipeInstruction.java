package com.example.myapplication;

public class RecipeInstruction {
    private int recipeId;

    private String nutrientValues;
    private String instructions;

    public RecipeInstruction(int recipeId, String nutrientValues, String instructions) {
        this.recipeId = recipeId;

        this.nutrientValues = nutrientValues;
        this.instructions = instructions;
    }


    public String getNutrientValues() {
        return nutrientValues;
    }

    public String getInstructions() {
        return instructions;
    }
}
