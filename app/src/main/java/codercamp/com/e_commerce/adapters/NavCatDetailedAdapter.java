package codercamp.com.e_commerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.NavCategoryActivity;
import codercamp.com.e_commerce.models.NavCatDetailedModel;

public class NavCatDetailedAdapter extends RecyclerView.Adapter<NavCatDetailedAdapter.NavViewHolder> {
    private List<NavCatDetailedModel> modelList;
    private Context context;
    private int totalQuantity = 1;
    private int TotalPrice = 0;
    private TextView quantity;
    private FirebaseFirestore database;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public NavCatDetailedAdapter(List<NavCatDetailedModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.nav_cat_detailed_item, parent, false);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavViewHolder holder, int position) {
        NavCatDetailedModel model = modelList.get(position);

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice() + " Tk");
        Glide.with(context).load(model.getImageUrl()).placeholder(R.drawable.plant).into(holder.imageView);
        Log.d("image", model.getImageUrl());

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    holder.quantity.setText(String.valueOf(totalQuantity));
                    TotalPrice = model.getPrice() * totalQuantity;
                }

            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity > 1) {
                    totalQuantity--;
                    holder.quantity.setText(String.valueOf(totalQuantity));
                    TotalPrice = model.getPrice() * totalQuantity;
                }

            }
        });

        holder.AddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Add This Product");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                TotalPrice = model.getPrice() * totalQuantity;
                                String currentDate, currentTime;

                                Calendar calendar = Calendar.getInstance();

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd,yyyy");
                                currentDate = dateFormat.format(calendar.getTime());

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                currentTime = timeFormat.format(calendar.getTime());

                                final HashMap<String, Object> map = new HashMap<>();

                                map.put("ProductName", model.getName());
                                map.put("ProductPrice", model.getPrice());
                                map.put("TotalQuantity", String.valueOf(TotalPrice));
                                map.put("TotalPrice", TotalPrice);
                                map.put("CurrentDate", currentDate);
                                map.put("CurrentTime", currentTime);


                                database.collection("CurrentUser").document(user.getUid())
                                        .collection("AddToCart").add(map)//
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Added To A Cart", Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, NavCategoryActivity.class));
                                                    //context.finish();
                                                } else {
                                                    Toast.makeText(context, "Failed to Add Cart", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        Log.d("price", String.valueOf(TotalPrice));


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class NavViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;
        ImageView imageView, addItem, removeItem;
        MaterialButton AddToCartButton;

        public NavViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nav_catDetailedName);
            price = itemView.findViewById(R.id.nav_catDetailedPrice);
            AddToCartButton = itemView.findViewById(R.id.AddToCart);
            imageView = itemView.findViewById(R.id.nav_catDetailedImage);
            addItem = itemView.findViewById(R.id.ItemAdd);
            removeItem = itemView.findViewById(R.id.ItemRemove);
            quantity = itemView.findViewById(R.id.ItemQuantity);
        }
    }
}
