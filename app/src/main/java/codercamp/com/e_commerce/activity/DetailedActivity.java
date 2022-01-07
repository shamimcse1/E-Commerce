package codercamp.com.e_commerce.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.ReviewAdapter;
import codercamp.com.e_commerce.models.ReviewModel;
import codercamp.com.e_commerce.models.ViewAllModel;

public class DetailedActivity extends AppCompatActivity {

    private ImageView pImageView, itemAdd, itemRemove;
    private TextView pName, description, price, quantity, addToReview;
    private MaterialButton addToCart;
    private Toolbar toolbar;
    private RecyclerView reviewRecyclerView;
    private ViewAllModel viewAllModel = null;
    private List<ReviewModel> reviewModels;
    private ReviewAdapter reviewAdapter;
    private int totalQuantity = 1;
    private int TotalPrice = 0;
    private FirebaseFirestore database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private AlertDialog dialog;
    private String ReviewText,Rating,ReviewerName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailedToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel) {

            viewAllModel = (ViewAllModel) object;
        }

        pImageView = findViewById(R.id.pImageId);
        itemAdd = findViewById(R.id.ItemAdd);
        itemRemove = findViewById(R.id.ItemRemove);

        pName = findViewById(R.id.pNameId);
        description = findViewById(R.id.pDescriptionId);
        price = findViewById(R.id.pPriceId);
        quantity = findViewById(R.id.ItemQuantity);
        addToReview = findViewById(R.id.AdReviewId);
        addToCart = findViewById(R.id.addToCartBtn);
        reviewRecyclerView = findViewById(R.id.ReviewRecyclerview);

        progressDialog = new ProgressDialog(DetailedActivity.this);

        if (viewAllModel != null) {

            pName.setText(viewAllModel.getName());
            description.setText(viewAllModel.getDescription());
            price.setText(String.valueOf(viewAllModel.getPrice() + " Tk"));
            Glide.with(this).load(viewAllModel.getImageUrl()).placeholder(R.drawable.indoor).into(pImageView);

            TotalPrice = viewAllModel.getPrice() * totalQuantity;

        }


        itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    TotalPrice = viewAllModel.getPrice() * totalQuantity;
                }

            }
        });
        itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    TotalPrice = viewAllModel.getPrice() * totalQuantity;
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentDate, currentTime;

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR,-1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd,yyyy");
                currentDate = dateFormat.format(calendar.getTime());

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                currentTime = timeFormat.format(calendar.getTime());

                final HashMap<String, Object> map = new HashMap<>();

                map.put("ProductName", viewAllModel.getName());
                map.put("ProductPrice", viewAllModel.getPrice());
                map.put("TotalQuantity", quantity.getText().toString());
                map.put("TotalPrice", TotalPrice);
                map.put("CurrentDate", currentDate);
                map.put("CurrentTime", currentTime);


                database.collection("CurrentUser").document(user.getUid())
                        .collection("AddToCart").add(map)//
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(DetailedActivity.this, "Failed to Add Cart", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });

        //Review Area
        AddReview();
        ViewReview();

    }


    private void AddReview() {

        addToReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait....");
                progressDialog.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailedActivity.this);

                View view = getLayoutInflater().inflate(R.layout.review_layout, null);

                final RatingBar ratingBar = view.findViewById(R.id.RatingBar);
                final EditText AddReviewText = view.findViewById(R.id.ReviewEditText);
                final EditText reviewUser = view.findViewById(R.id.ReviewUserName);
                final MaterialButton Submit = view.findViewById(R.id.Submit_button);
                final MaterialButton Cancel = view.findViewById(R.id.Cancel_button);
                builder.setView(view);

                //Cancelled Button
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // finish();
                        dialog.dismiss();
                        progressDialog.dismiss();
                    }
                });

                //Submit Button
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ReviewText = AddReviewText.getText().toString();
                        ReviewerName = reviewUser.getText().toString();
                        if (ReviewerName.isEmpty()) {
                            reviewUser.setError("Please Enter Name");
                            reviewUser.requestFocus();
                        }
                        else if (ReviewText.isEmpty()) {
                            AddReviewText.setError("Please Add Review");
                            AddReviewText.requestFocus();
                        } else {
                            Rating = String.valueOf(ratingBar.getRating());
                            ReviewAddInDatabase();
                        }

                    }
                });

                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

            }
        });

    }

    private void ReviewAddInDatabase() {
      final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserReview");

        final String key = reference.push().getKey();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("review", ReviewText);
        hashMap.put("rating", Rating);
        hashMap.put("userName",ReviewerName);
        hashMap.put("currentDate",currentDate);

        assert key != null;
        reference.child(key).child(user.getUid()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DetailedActivity.this, "Review Successfully Added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    progressDialog.dismiss();

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(DetailedActivity.this, "Added to Failed Review", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void  ViewReview(){
        final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("UserReview");
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setHasFixedSize(true);
        reviewModels = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewModels,this);
        reviewRecyclerView.setAdapter(reviewAdapter);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ReviewModel model = dataSnapshot.getValue(ReviewModel.class);
                    if (model != null){
                        reviewModels.add(model);
                        reviewRecyclerView.setVisibility(View.VISIBLE);
                        reviewAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailedActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}