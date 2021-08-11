package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartHolder> {
    private List<MyCartModel> myCartModels;
    private Context context;
    private int TotalPrice = 0;
    private FirebaseFirestore database;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public MyCartAdapter(List<MyCartModel> myCartModels, Context context) {
        this.myCartModels = myCartModels;
        this.context = context;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_item, parent, false);


        return new MyCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartHolder holder, int position) {

        MyCartModel model = myCartModels.get(position);

        holder.productName.setText(model.getProductName());
        holder.productPrice.setText(String.valueOf(model.getProductPrice())+" Tk");
        holder.totalQuantity.setText(model.getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(model.getTotalPrice())+" Tk");
        holder.currentDate.setText(model.getCurrentDate());
        holder.currentTime.setText(model.getCurrentTime());

        //Pass Total Price to My Cart Fragment
       // TotalPrice = TotalPrice + myCartModels.get(position).getTotalPrice();

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("CurrentUser").document(user.getUid())
                        .collection("AddToCart")
                        .document(myCartModels.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    myCartModels.remove(myCartModels.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Delete Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Item Delete Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartModels.size();
    }

    public static class MyCartHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, totalQuantity, totalPrice, currentDate, currentTime;
        ImageView deleteItem;

        public MyCartHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.MyCartProductName);
            productPrice = itemView.findViewById(R.id.MyCartProductPrice);
            totalQuantity = itemView.findViewById(R.id.MyCartProductQuantity);
            totalPrice = itemView.findViewById(R.id.MyCartProductTotalPrice);
            currentDate = itemView.findViewById(R.id.MyCartProductDate);
            currentTime = itemView.findViewById(R.id.MyCartProductTime);
            deleteItem = itemView.findViewById(R.id.DeleteItem);


        }
    }
}
