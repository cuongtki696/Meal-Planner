package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<RecipeDescription> recipeList;
    private Context context;

    public RecipeAdapter(Context context, List<RecipeDescription> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        Log.d("RecipeAdapter", "Adapter initialized with " + recipeList.size() + " recipes");
    }

    public void updateRecipeList(List<RecipeDescription> newRecipeList) {
        this.recipeList = newRecipeList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeDescription recipe = recipeList.get(position);

        // Use the getter methods in RecipeDescription to get values
        holder.recipeName.setText("Name: " + recipe.getName());
        holder.recipeType.setText("Type: " + recipe.getCategory());
        holder.recipeAllergy.setText("Allergy: " + recipe.getAllergy());
        holder.recipeDescription.setText("Description: " + recipe.getDescription());

        Log.d("RecipeAdapter", "Binding recipe: " + recipe.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipeId", recipe.getId()); // Assuming recipe has a unique ID
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return recipeList != null ? recipeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeType, recipeAllergy, recipeDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeType = itemView.findViewById(R.id.recipe_type);
            recipeAllergy = itemView.findViewById(R.id.recipe_allergy);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
        }
    }
}