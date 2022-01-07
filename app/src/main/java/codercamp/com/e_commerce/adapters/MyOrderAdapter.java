package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;
import codercamp.com.e_commerce.models.Payment_time;
import codercamp.com.e_commerce.models.ProfileModel;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolderOrder> {
    private List<MyCartModel> myOrder;
    private Context mContext;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private List<MyCartModel> myCartModels;
    String name;
    String number;
    String date, time;
    public MyOrderAdapter(List<MyCartModel> myOrder, Context mContext) {
        this.myOrder = myOrder;
        this.mContext = mContext;
        Payment_time payment_time = new Payment_time();

        time = payment_time.getCurrentTime();
        date = payment_time.getCurrentDate();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel model = snapshot.getValue(ProfileModel.class);
                if (model!= null){
                    try {
                        name = model.getName();
                        number = model.getNumber();
                    }
                    catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_order_data, parent, false);

        return new MyViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderOrder holder, int position) {


        MyCartModel model = myOrder.get(position);


        holder.UserName.setText("User Name : "+name);
        holder.Number.setText("Number : "+number);

        holder.ProductID.setText("Product ID : "+model.getDocumentId());
        holder.ProductName.setText("Product Name : "+model.getProductName());
        holder.ProductPrice.setText("Product Price : "+String.valueOf(model.getProductPrice()) + " Tk");
        holder.ProductQuantity.setText("Total Quantity : "+model.getTotalQuantity());
        holder.TotalPrice.setText("Total Price : "+String.valueOf(model.getTotalPrice()) + " Tk");
        holder.DateTime.setText("Date Time :"+time +" "+ date);

    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    public class MyViewHolderOrder extends RecyclerView.ViewHolder {
        private TextView ProductID,ProductName, ProductPrice, TotalPrice, ProductQuantity, DateTime, UserName, Number;

        public MyViewHolderOrder(@NonNull View view) {
            super(view);//Product_ID
            UserName = view.findViewById(R.id.UserName);
            Number = view.findViewById(R.id.Number);

            ProductID = view.findViewById(R.id.Product_ID);
            ProductName = view.findViewById(R.id.Product_Name);
            ProductPrice = view.findViewById(R.id.Product_Price);
            TotalPrice = view.findViewById(R.id.Total_Price);
            ProductQuantity = view.findViewById(R.id.Total_Quality);
            DateTime = view.findViewById(R.id.Data_Time);
        }
    }
}
