package codercamp.com.e_commerce.Fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.HomeCategoryAdapter;
import codercamp.com.e_commerce.adapters.PopularAdapter;
import codercamp.com.e_commerce.adapters.RecommendedAdapter;
import codercamp.com.e_commerce.adapters.ViewAllAdapter;
import codercamp.com.e_commerce.models.HomeCategoryModel;
import codercamp.com.e_commerce.models.Popular_Model;
import codercamp.com.e_commerce.models.RecommendedModel;
import codercamp.com.e_commerce.models.ViewAllModel;

public class HomeFragment extends Fragment {


    RecyclerView PopularRecyclerView, homeCategoryRecyclerView, RecommendedRecyclerView;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    //Popular Items
    private PopularAdapter popularAdapter;
    private List<Popular_Model> popular_models;

    //Home Category
    private List<HomeCategoryModel> homeCategoryModels;
    private HomeCategoryAdapter homeCategoryAdapter;

    //Recommended Items
    private List<RecommendedModel> recommendedModels;
    private RecommendedAdapter recommendedAdapter;

    //Search View
    private EditText searchEditText;
    private RecyclerView searchRecyclerView;
    private List<ViewAllModel> viewAllModels;
    private ViewAllAdapter viewAllAdapter;
    private FirebaseFirestore db;

    //View All Data Text View
    public TextView ViewAllForPopular, ViewAllExplore, ViewAllRecommended;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        progressBar = view.findViewById(R.id.progress_circular);
        scrollView = view.findViewById(R.id.scrollView);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);


        searchEditText = view.findViewById(R.id.edit_search);
        searchRecyclerView = view.findViewById(R.id.search_Recyclerview);


        //Popular Item
        PopularRecyclerView = view.findViewById(R.id.PopularRecyclerView);
        PopularRecyclerView.setHasFixedSize(true);
        PopularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popular_models = new ArrayList<>();
        popularAdapter = new PopularAdapter(popular_models, getActivity());
        PopularRecyclerView.setAdapter(popularAdapter);
        getPopularData();


        //Home Category
        homeCategoryRecyclerView = view.findViewById(R.id.CategoryRecyclerView);
        homeCategoryRecyclerView.setHasFixedSize(true);
        homeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeCategoryModels = new ArrayList<>();
        homeCategoryAdapter = new HomeCategoryAdapter(homeCategoryModels, getActivity());
        homeCategoryRecyclerView.setAdapter(homeCategoryAdapter);
        getCategoryData();

        //Recommended Items
        RecommendedRecyclerView = view.findViewById(R.id.RecommendedRecyclerView);
        RecommendedRecyclerView.setHasFixedSize(true);
        RecommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModels = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(recommendedModels, getActivity());
        RecommendedRecyclerView.setAdapter(recommendedAdapter);
        getRecommendedData();

        //Search Item
        searchView();


        return view;
    }


    private void getPopularData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Popular_Model model = document.toObject(Popular_Model.class);
                                popular_models.add(0, model);
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                popularAdapter.notifyDataSetChanged();
                            }


                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getCategoryData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            //homeCategoryModels.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategoryModel model = document.toObject(HomeCategoryModel.class);
                                homeCategoryModels.add(model);
                                //progressDialog.dismiss();
                                homeCategoryAdapter.notifyDataSetChanged();
                            }


                        } else {

                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getRecommendedData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            //recommendedModels.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel model = document.toObject(RecommendedModel.class);
                                recommendedModels.add(model);
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                recommendedAdapter.notifyDataSetChanged();
                            }

                        } else {

                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Search  Method
    private void searchView() {

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecyclerView.setHasFixedSize(true);
        viewAllModels = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(viewAllModels, getContext());
        searchRecyclerView.setAdapter(viewAllAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    viewAllModels.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    SearchProduct(s.toString());
                }

            }
        });
    }

    // Search  Sub-Method
    private void SearchProduct(String name) {
        if (!name.isEmpty()) {
            db.collection("AllProducts").whereEqualTo("name", name).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful() && task.getResult() != null) {
                                viewAllModels.clear();
                                viewAllAdapter.notifyDataSetChanged();

                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {

                                    ViewAllModel model = snapshot.toObject(ViewAllModel.class);
                                    viewAllModels.add(model);
                                    viewAllAdapter.notifyDataSetChanged();

                                }
                            }

                        }
                    });
        }

    }


}
