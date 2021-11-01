package codercamp.com.e_commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;

public class PaymentActivity extends AppCompatActivity {
    private RadioGroup radioGroup;//
    private RadioButton CashOn, bKash;
    private Button FinalOrder;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private List<MyCartModel> myCartModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymnet);
        radioGroup = findViewById(R.id.radioGroup);

        CashOn = findViewById(R.id.cashOn);
        bKash = findViewById(R.id.bKash);
        FinalOrder = findViewById(R.id.FinalOrder_Now);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseFirestore.getInstance();

        myCartModels = (List<MyCartModel>) getIntent().getSerializableExtra("itemList");

        FinalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If Selected Cash On Delivery
                if (CashOn.isChecked()) {
                    //Toast.makeText(PaymentActivity.this, "Cash On Delivery is Selected", Toast.LENGTH_SHORT).show();

                    if (myCartModels != null && myCartModels.size() > 0) {

                        for (MyCartModel cartModel : myCartModels) {
                            final HashMap<String, Object> map = new HashMap<>();

                            map.put("ProductName", cartModel.getProductName());
                            map.put("ProductPrice", cartModel.getProductPrice());
                            map.put("TotalQuantity", cartModel.getTotalQuantity());
                            map.put("TotalPrice", cartModel.getTotalPrice());
                            map.put("CurrentDate", cartModel.getCurrentDate());
                            map.put("CurrentTime", cartModel.getCurrentTime());


                            database.collection("CurrentUser").document(user.getUid())
                                    .collection("MyOrder").add(map)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(PaymentActivity.this, "Yor Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(PaymentActivity.this, OrderActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(PaymentActivity.this, "Yor Order Placed Failed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        }
                    }

                } else if (bKash.isChecked()) {
                    Toast.makeText(PaymentActivity.this, "bKash is Selected", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PaymentActivity.this, "Please Select a Payment Method", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}