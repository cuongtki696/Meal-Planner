package meal.cuongtki696.org.ui.home;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder;

import meal.cuongtki696.org.R;
import meal.cuongtki696.org.repo.db.Meal;

public class MealAdapter extends BaseQuickAdapter<Meal, QuickViewHolder> {

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_meal, viewGroup);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder holder, int i, @Nullable Meal meal) {
        Context context = holder.itemView.getContext();

        holder.setText(R.id.tv_name, meal.name);
        holder.setText(R.id.tv_desc, meal.desc);
        holder.setText(R.id.tv_type, meal.category);
        holder.setText(R.id.tv_allergy, meal.allergy);

        Glide.with(context).asBitmap().load(Uri.parse(meal.image)).into((ImageView) holder.getView(R.id.iv_image));
    }
}
