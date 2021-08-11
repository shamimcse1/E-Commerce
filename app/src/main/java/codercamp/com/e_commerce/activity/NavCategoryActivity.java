package codercamp.com.e_commerce.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.NavCatDetailedAdapter;
import codercamp.com.e_commerce.models.NavCatDetailedModel;

public class NavCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NavCatDetailedModel> detailedModels;
    private NavCatDetailedAdapter navCatDetailedAdapter;
    private Toolbar toolbar;
    private FirebaseFirestore dataStore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        toolbar = findViewById(R.id.detailedToolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animatoo.animateSlideRight(NavCategoryActivity.this);
                finish();
            }
        });

        //Databases
        dataStore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progress_circular1);
        recyclerView = findViewById(R.id.ProductDetailed);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        detailedModels = new ArrayList<>();
        navCatDetailedAdapter = new NavCatDetailedAdapter(detailedModels, this);
        recyclerView.setAdapter(navCatDetailedAdapter);
        getNavCategoryData();

    }


    private void getNavCategoryData() {

        String type = getIntent().getStringExtra("type");
        //Gating Indoor Product
        if (type != null && type.equalsIgnoreCase("indoor")) {

            dataStore.collection("NavCategoryDetailed").whereEqualTo("type", "indoor")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            NavCatDetailedModel model = snapshot.toObject(NavCatDetailedModel.class);
                            if (model != null) {
                                detailedModels.add(model);
                                navCatDetailedAdapter.notifyDataSetChanged();

                                String Type = model.getType();
                                String firstLetter = Type.substring(0, 1);
                                String remainingLetters = Type.substring(1, Type.length());
                                // change the first letter to uppercase
                                firstLetter = firstLetter.toUpperCase();
                                Type = firstLetter + remainingLetters;

                                getSupportActionBar().setTitle("Category " + Type);

                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
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

            dataStore.collection("NavCategoryDetailed").whereEqualTo("type", "outdoor")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            NavCatDetailedModel model = snapshot.toObject(NavCatDetailedModel.class);
                            if (model != null) {
                                detailedModels.add(model);
                                navCatDetailedAdapter.notifyDataSetChanged();
                                getSupportActionBar().setTitle("Category " + model.getType());
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
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

            dataStore.collection("NavCategoryDetailed").whereEqualTo("type", "pots")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                        try {
                            NavCatDetailedModel model = snapshot.toObject(NavCatDetailedModel.class);
                            if (model != null) {
                                detailedModels.add(model);
                                navCatDetailedAdapter.notifyDataSetChanged();
                                getSupportActionBar().setTitle("Category " + model.getType());
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
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