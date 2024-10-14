package meal.cuongtki696.org.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import meal.cuongtki696.org.repo.db.Meal;

public class AssetsHelper {
    public static List<Meal> meals = new ArrayList<>();

    public static void setup(Context context) {
        String json = load(context, "meals.json");
        meals = new Gson().fromJson(json, new TypeToken<List<Meal>>() {
        }.getType());
    }

    private static String load(Context context, String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
