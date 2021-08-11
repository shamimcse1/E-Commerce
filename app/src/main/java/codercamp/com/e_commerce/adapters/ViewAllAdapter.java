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
import codercamp.com.e_commerce.activity.DetailedActivity;
import codercamp.com.e_commerce.models.ViewAllModel;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.AllViewHolder>{

    private List<ViewAllModel> modelList;
    private Context context;

    public ViewAllAdapter(List<ViewAllModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_all_data,parent, false);
        return new AllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {

        ViewAllModel model= modelList.get(position);

        holder.pName.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        holder.price.setText(String.valueOf(model.getPrice()+" Tk"));
        Glide.with(context).load(model.getImageUrl()).placeholder(R.drawable.indoor).into(holder.roundedImageView);

        Log.d("image", model.getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail",modelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static  class AllViewHolder extends RecyclerView.ViewHolder {

        ImageView roundedImageView;
        TextView pName,description,rating, price;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.ViewProductImageId);
            pName = itemView.findViewById(R.id.ViewProductNameId);
            description = itemView.findViewById(R.id.ViewProductDescriptionId);
            rating = itemView.findViewById(R.id.ViewProductRatingTextId);
            price = itemView.findViewById(R.id.ViewProductPriceId);
        }
    }
}
