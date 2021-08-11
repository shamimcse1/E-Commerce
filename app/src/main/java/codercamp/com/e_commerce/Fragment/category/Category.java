package codercamp.com.e_commerce.Fragment.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.NavCategoryAdapter;
import codercamp.com.e_commerce.models.NavCategoryModel;

public class Category extends Fragment {

    private RecyclerView nav_categoryRecycler;
    private List<NavCategoryModel> navCategoryModels;
    private NavCategoryAdapter navCategoryAdapter;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);


        progressBar = view.findViewById(R.id.progress_circular);
        linearLayout = view.findViewById(R.id.linearLayout);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        nav_categoryRecycler = view.findViewById(R.id.NavCategoryRecyclerview);
        nav_categoryRecycler.setHasFixedSize(true);
        nav_categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        navCategoryModels = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(navCategoryModels,getContext());
        nav_categoryRecycler.setAdapter(navCategoryAdapter);
        getNavCategory();

        return view;
    }

    private void getNavCategory() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() !=null) {
                            //homeCategoryModels.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel model = document.toObject(NavCategoryModel.class);
                                navCategoryModels.add(model);
                                progressBar.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                navCategoryAdapter.notifyDataSetChanged();
                            }


                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}