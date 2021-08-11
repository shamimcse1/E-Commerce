package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.NavCategoryActivity;
import codercamp.com.e_commerce.models.NavCategoryModel;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.NavCategory>{

    private List<NavCategoryModel> modelList;
    private Context context;

    public NavCategoryAdapter(List<NavCategoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public NavCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nav_category_item,parent,false);

        return new NavCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategory holder, int position) {

        NavCategoryModel categoryModel = modelList.get(position);

        //holder.pName.setText(categoryModel.getName());
        //holder.description.setText(categoryModel.getDescription());
        holder.discount.setText(categoryModel.getDiscount());
       // holder.type.setText(categoryModel.getType());

        String Type = categoryModel.getType();
        String firstLetter = Type.substring(0, 1);
        String remainingLetters = Type.substring(1, Type.length());
        // change the first letter to uppercase
        firstLetter = firstLetter.toUpperCase();
        Type = firstLetter + remainingLetters;
        holder.type.setText(Type);

        Glide.with(context).load(categoryModel.getImageUrl()).placeholder(R.drawable.indoor).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type",categoryModel.getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class NavCategory extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView pName,description,discount,type;

        public NavCategory(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.nav_catImage);
           // pName = itemView.findViewById(R.id.nav_catName);
           // description = itemView.findViewById(R.id.nav_catDescription);
            discount = itemView.findViewById(R.id.nav_catDiscount);
            type = itemView.findViewById(R.id.nav_catType);


        }
    }
}
