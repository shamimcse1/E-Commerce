package codercamp.com.e_commerce.Fragment.MyOrder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.adapters.MyOrderAdapter;
import codercamp.com.e_commerce.models.MyCartModel;


public class MyOrderFragment extends Fragment {

    private ConstraintLayout constraintLayout;
    private LinearLayout linearLayout;
    private RecyclerView MyOrderRecyclerView;
    private List<MyCartModel> MyOrderModels;
    private MyOrderAdapter OrderAdapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private ProgressDialog progressDialog;


    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        constraintLayout = view.findViewById(R.id.ConstantLayout);
        linearLayout = view.findViewById(R.id.showOrderData);

        constraintLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        MyOrderRecyclerView = view.findViewById(R.id.MyOrderRecyclerview);
        MyOrderRecyclerView.setHasFixedSize(true);
        MyOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyOrderModels = new ArrayList<>();
        OrderAdapter = new MyOrderAdapter(MyOrderModels, getContext());
        MyOrderRecyclerView.setAdapter(OrderAdapter);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseFirestore.getInstance();
        getOrderData();

        return view;
    }

    private void getOrderData() {
        try {
            database.collection("CurrentUser").document(user.getUid()).collection("MyOrder")//AddToCart
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //Dialog will show
                            progressDialog.show();
                            if (task.isSuccessful() && task.getResult() != null) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String documentID = document.getId();

                                    MyCartModel model = document.toObject(MyCartModel.class);

                                    model.setDocumentId(documentID);
                                    MyOrderModels.add(model);
                                    constraintLayout.setVisibility(View.GONE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();

                                }
                                OrderAdapter.notifyDataSetChanged();

                            } else {
                                progressDialog.dismiss();
                                constraintLayout.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Your Order list is Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}