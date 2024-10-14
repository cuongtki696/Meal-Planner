package meal.cuongtki696.org.repo.db;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meal {
    public int id;
    public String name;
    public String country;
    public String desc;
    public String image;
    public String instructions;
    @SerializedName("video_link")
    public String videoLink;

    public List<String> ingredients;

    @SerializedName("fitness_advice")
    public String fitnessAdvice;
    @SerializedName("process_steps")
    public List<String> processSteps;

    public String category;
    public String allergy;

    public Double protein;
    public Double fat;
    public Double carbs;
    public List<String> nutrients;
}
