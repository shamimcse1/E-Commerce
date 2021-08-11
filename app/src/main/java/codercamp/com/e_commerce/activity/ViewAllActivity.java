package codercamp.com.e_commerce.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.ViewAllAdapter;
import codercamp.com.e_commerce.models.ViewAllModel;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private List<ViewAllModel> modelList;
    private ViewAllAdapter viewAllAdapter;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private FirebaseFirestore database;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseFirestore.getInstance();

        String type = getIntent().getStringExtra("type");


        progressBar = findViewById(R.id.ViewProgress_circular);
        linearLayout = findViewById(R.id.ViewLinearLayout);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);


        viewAllRecyclerView = findViewById(R.id.ViewAllRecyclerview);
        viewAllRecyclerView.setHasFixedSize(true);
        viewAllRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(modelList, this);
        viewAllRecyclerView.setAdapter(viewAllAdapter);


        //Gating Indoor Product
        if (type != null && type.equalsIgnoreCase("indoor")) {

            database.collection("AllProducts").whereEqualTo("type", "indoor")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            ViewAllModel model = snapshot.toObject(ViewAllModel.class);
                            modelList.add(model);
                            Log.d("tag", "Indoor" + model.getName());
                            String Type = model.getType();
                            String firstLetter = Type.substring(0, 1);
                            String remainingLetters = Type.substring(1, Type.length());
                            // change the first letter to uppercase
                            firstLetter = firstLetter.toUpperCase();
                            Type = firstLetter + remainingLetters;
                            getSupportActionBar().setTitle("Category " + Type);

                            viewAllAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Error ", e.getMessage());
                        }

                    }


                }
            });


        }

        //Gating Outdoor Product
        if (type != null && type.equalsIgnoreCase("outdoor")) {

            database.collection("AllProducts").whereEqualTo("type", "outdoor")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            ViewAllModel model = snapshot.toObject(ViewAllModel.class);
                            modelList.add(model);
                            String Type = model.getType();
                            String firstLetter = Type.substring(0, 1);
                            String remainingLetters = Type.substring(1, Type.length());
                            // change the first letter to uppercase
                            firstLetter = firstLetter.toUpperCase();
                            Type = firstLetter + remainingLetters;
                            getSupportActionBar().setTitle("Category " + Type);

                            viewAllAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Error ", e.getMessage());
                        }

                    }
                }
            });


        }

        //Gating Pots Product
        if (type != null && type.equalsIgnoreCase("pots")) {

            database.collection("AllProducts").whereEqualTo("type", "pots")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            ViewAllModel model = snapshot.toObject(ViewAllModel.class);
                            modelList.add(model);

                            String Type = model.getType();
                            String firstLetter = Type.substring(0, 1);
                            String remainingLetters = Type.substring(1, Type.length());
                            // change the first letter to uppercase
                            firstLetter = firstLetter.toUpperCase();
                            Type = firstLetter + remainingLetters;
                            getSupportActionBar().setTitle("Category " + Type);

                            viewAllAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Error ", e.getMessage());
                        }

                    }
                }
            });


        }
    }
}