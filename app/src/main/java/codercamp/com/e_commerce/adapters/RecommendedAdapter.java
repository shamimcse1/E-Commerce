package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.content.Intent;
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
import codercamp.com.e_commerce.models.RecommendedModel;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyRecommended>{

    private List<RecommendedModel> modelList;
    private Context context;

    public RecommendedAdapter(List<RecommendedModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRecommended onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recommended_item,parent ,false);
        return new MyRecommended(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecommended holder, int position) {

        RecommendedModel model = modelList.get(position);
        if (model != null){
        holder.pName.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        holder.price.setText(String.valueOf(model.getPrice()+" Tk"));
        Glide.with(context).load(model.getImageUrl()).placeholder(R.drawable.indoor).into(holder.imageView);
        }

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
        return modelList.size();
    }

    public static class MyRecommended extends RecyclerView.ViewHolder {
        TextView pName,description,rating,price;
        RoundedImageView imageView;
        public MyRecommended(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.RecProductNameId);
            description = itemView.findViewById(R.id.RecProductDescriptionId);
            rating = itemView.findViewById(R.id.RecProductRatingTextId);
            price = itemView.findViewById(R.id.RecProductPriceId);
            imageView = itemView.findViewById(R.id.RecProductImageId);

        }
    }
}
