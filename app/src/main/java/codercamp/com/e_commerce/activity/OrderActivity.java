package codercamp.com.e_commerce.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;

public class OrderActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private List<MyCartModel> myCartModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        database= FirebaseFirestore.getInstance();

       // myCartModels = (List<MyCartModel>) getIntent().getSerializableExtra("itemList");

       /* if (myCartModels != null && myCartModels.size() > 0){

            for (MyCartModel cartModel : myCartModels){
                final HashMap<String, Object> map = new HashMap<>();

                map.put("ProductName",cartModel.getProductName());
                map.put("ProductPrice",cartModel.getProductPrice());
                map.put("TotalQuantity",cartModel.getTotalQuantity());
                map.put("TotalPrice",cartModel.getTotalPrice());
                map.put("CurrentDate",cartModel.getCurrentDate());
                map.put("CurrentTime",cartModel.getCurrentTime());


                database.collection("CurrentUser").document(user.getUid())
                        .collection("MyOrder").add(map)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(OrderActivity.this, "Yor Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(OrderActivity.this, "Yor Order Placed Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        }*/
    }
}