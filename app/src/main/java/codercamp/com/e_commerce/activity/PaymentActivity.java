package codercamp.com.e_commerce.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import codercamp.com.e_commerce.BkashPaymentIntegration.BkashActivity;
import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.MyCartModel;
import codercamp.com.e_commerce.models.Payment_time;
import codercamp.com.e_commerce.models.ProfileModel;

public class PaymentActivity extends AppCompatActivity {
    private RadioGroup radioGroup;//
    private RadioButton CashOn, bKash;
    private Button FinalOrder;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private DatabaseReference reference;
    private List<MyCartModel> myCartModels;
    String name;
    String number;


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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel model = snapshot.getValue(ProfileModel.class);
                if (model != null) {
                    try {
                        name = model.getName();
                        number = model.getNumber();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        String currentDate, currentTime;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-1);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        currentTime = timeFormat.format(calendar.getTime());



        Payment_time payment_time = new Payment_time();
        payment_time.setCurrentTime(currentTime);
        payment_time.setCurrentDate(currentDate);

        myCartModels = (List<MyCartModel>) getIntent().getSerializableExtra("itemList");

        FinalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If Selected Cash On Delivery
                if (CashOn.isChecked()) {


                    if (myCartModels != null && myCartModels.size() > 0) {

                        for (MyCartModel cartModel : myCartModels) {
                            final HashMap<String, Object> map = new HashMap<>();


                            map.put("UserName", name);
                            map.put("Number", number);
                            map.put("ProductName", cartModel.getProductName());
                            map.put("ProductPrice", cartModel.getProductPrice());
                            map.put("TotalQuantity", cartModel.getTotalQuantity());
                            map.put("TotalPrice", cartModel.getTotalPrice());
                            map.put("CurrentDate", payment_time.getCurrentDate());//cartModel.getCurrentDate()
                            map.put("CurrentTime", payment_time.getCurrentTime());//cartModel.getCurrentTime()

                            //Store Data in Database
                            database.collection("CurrentUser").document(user.getUid())
                                    .collection("MyOrder").add(map)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(PaymentActivity.this, "Yor Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(PaymentActivity.this, OrderActivity.class);
                                                startActivity(intent);
                                                removeItemToCart(cartModel);
                                                finish();
                                            } else {
                                                Toast.makeText(PaymentActivity.this, "Your Order Placed Failed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        }
                        Toast.makeText(PaymentActivity.this, "Yor Order Placed Successfully", Toast.LENGTH_SHORT).show();

                    }

                } else if (bKash.isChecked()) {

                    if (myCartModels != null && myCartModels.size() > 0) {

                        for (MyCartModel cartModel : myCartModels) {
                            Intent intent = new Intent(PaymentActivity.this, BkashActivity.class);
                            //intent.putExtra("price",cartModel.getTotalPrice());
                            startActivity(intent);

                        }
                    }

                } else {
                    Toast.makeText(PaymentActivity.this, "Please Select a Payment Method", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void removeItemToCart(MyCartModel cartModel) {

        database.collection("CurrentUser").document(user.getUid())
                .collection("AddToCart")
                .document(cartModel.getDocumentId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(PaymentActivity.this, "Item Delete Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PaymentActivity.this, "Item Delete Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}