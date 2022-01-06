package codercamp.com.e_commerce.Fragment.MyCart;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.PaymentActivity;
import codercamp.com.e_commerce.adapters.MyCartAdapter;
import codercamp.com.e_commerce.models.MyCartModel;


public class MyCartFragment extends Fragment {

    private RecyclerView myCartRec;
    private List<MyCartModel> myCartModels;
    private MyCartAdapter adapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private RelativeLayout constraintLayout, constraintLayout2;
    private TextView totalPrice;
    private MaterialButton buyBtn;


    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseFirestore.getInstance();

        buyBtn = view.findViewById(R.id.BuyBtn);

        constraintLayout = view.findViewById(R.id.constraint);
        constraintLayout2 = view.findViewById(R.id.constraint2);

        totalPrice = view.findViewById(R.id.totalPrice);

        constraintLayout.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.GONE);

        myCartRec = view.findViewById(R.id.MyCartRecyclerview);
        myCartRec.setHasFixedSize(true);
        myCartRec.setLayoutManager(new LinearLayoutManager(getContext()));
        myCartModels = new ArrayList<>();
        adapter = new MyCartAdapter(myCartModels, getContext());
        myCartRec.setAdapter(adapter);
        getCartData();

        // Go To Order Activity
        OrderBtn();

        return view;
    }


    private void getCartData() {
        database.collection("CurrentUser").document(user.getUid()).collection("AddToCart")//AddToCart
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String documentID = document.getId();

                                MyCartModel model = document.toObject(MyCartModel.class);

                                model.setDocumentId(documentID);
                                myCartModels.add(model);
                                constraintLayout.setVisibility(View.GONE);
                                constraintLayout2.setVisibility(View.VISIBLE);

                            }
                            adapter.notifyDataSetChanged();
                            calculateTotalPrice(myCartModels);

                        } else {
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void calculateTotalPrice(List<MyCartModel> myCartModels) {

        int totalsAmount = 0;
        for (MyCartModel model : myCartModels) {
            totalsAmount += model.getTotalPrice();
        }
        totalPrice.setText("Total Price : " + String.valueOf(totalsAmount) + " Tk");
    }

    private void OrderBtn() {
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                alertDialogBuilder.setMessage("Are you sure, You want to Buy These Product");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                                intent.putExtra("itemList", (Serializable) myCartModels);
                                startActivity(intent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }


}