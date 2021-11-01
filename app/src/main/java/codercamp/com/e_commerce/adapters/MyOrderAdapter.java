package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolderOrder> {
    private List<MyCartModel> myOrder;
    private Context mContext;

    public MyOrderAdapter(List<MyCartModel> myOrder, Context mContext) {
        this.myOrder = myOrder;
        this.mContext = mContext;
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

        holder.ProductID.setText("Product ID : "+model.getDocumentId());
        holder.ProductName.setText("Product Name : "+model.getProductName());
        holder.ProductPrice.setText("Product Price : "+String.valueOf(model.getProductPrice()) + " Tk");
        holder.ProductQuantity.setText("Total Quantity : "+model.getTotalQuantity());
        holder.TotalPrice.setText("Total Price : "+String.valueOf(model.getTotalPrice()) + " Tk");
        holder.DateTime.setText("Date Time :"+model.getCurrentTime() + model.getCurrentDate());

    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    public class MyViewHolderOrder extends RecyclerView.ViewHolder {
        private TextView ProductID,ProductName, ProductPrice, TotalPrice, ProductQuantity, DateTime;

        public MyViewHolderOrder(@NonNull View view) {
            super(view);//Product_ID
            ProductID = view.findViewById(R.id.Product_ID);
            ProductName = view.findViewById(R.id.Product_Name);
            ProductPrice = view.findViewById(R.id.Product_Price);
            TotalPrice = view.findViewById(R.id.Total_Price);
            ProductQuantity = view.findViewById(R.id.Total_Quality);
            DateTime = view.findViewById(R.id.Data_Time);
        }
    }
}
