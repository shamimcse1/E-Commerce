package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import codercamp.com.e_commerce.activity.ViewAllActivity;
import codercamp.com.e_commerce.models.HomeCategoryModel;

public class HomeCategoryAdapter extends  RecyclerView.Adapter<HomeCategoryAdapter.MyHomeCategory>{

    private List<HomeCategoryModel> modelList;
    private Context context;

    public HomeCategoryAdapter(List<HomeCategoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHomeCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);

        return new MyHomeCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomeCategory holder, int position) {
        HomeCategoryModel categoryModel = modelList.get(position);

        holder.catName.setText(categoryModel.getpName());
        Glide.with(context).load(categoryModel.getImageUrl()).placeholder(R.drawable.indoor).into(holder.catImage);
        Log.d("image", categoryModel.getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",categoryModel.getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyHomeCategory extends RecyclerView.ViewHolder{
        TextView catName;
        ImageView catImage;

        public MyHomeCategory(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.catName);
            catImage = itemView.findViewById(R.id.catImageUrl);

        }
    }
}
