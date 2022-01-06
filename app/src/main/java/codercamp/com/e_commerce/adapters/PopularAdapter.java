package codercamp.com.e_commerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.ViewAllActivity;
import codercamp.com.e_commerce.models.Popular_Model;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyHolder>{
    private List<Popular_Model> popular_models;
    private Context context;

    public PopularAdapter(List<Popular_Model> popular_models, Context context) {
        this.popular_models = popular_models;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_product_item, parent,false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Popular_Model model = popular_models.get(position);


        holder.pName.setText(model.getpName());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice() + " Tk");
        holder.rating.setText(model.getRating());
        holder.discount.setText(model.getDiscount());

        Glide.with(context).load(model.getImageUrl()).placeholder(R.drawable.plant).into(holder.roundedImageView);
        Log.d("image", model.getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",model.getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return popular_models.size();
    }

    public static class  MyHolder extends RecyclerView.ViewHolder {
        TextView pName,description,rating,discount,price;
        RoundedImageView roundedImageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.ProductNameId);
            description = itemView.findViewById(R.id.ProductDescriptionId);
            rating = itemView.findViewById(R.id.ProductRatingTextId);
            discount = itemView.findViewById(R.id.ProductDiscountId);
            price = itemView.findViewById(R.id.ProductPriceId);
            roundedImageView = itemView.findViewById(R.id.ProductImageId);
        }
    }
}
