package codercamp.com.e_commerce.Fragment.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.ProfileViewActivity;
import codercamp.com.e_commerce.models.ProfileModel;

public class Profile extends Fragment {

    private TextView userName, userEmail, number, address;
    private ImageView userProfileImage;
    private MaterialButton Update;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileViewActivity.class));

            }
        });

        return view;
    }


    private void initView(View view) {

        userName = view.findViewById(R.id.ProUserName);
        userEmail = view.findViewById(R.id.ProUserEmail);
        number = view.findViewById(R.id.ProUserPhone);
        address = view.findViewById(R.id.ProUserAddress);
        userProfileImage = view.findViewById(R.id.ProfileImage);

        Update = view.findViewById(R.id.ProUpdateBtn);
        GetProfileInfo();

    }

    private void GetProfileInfo() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProfileModel model = dataSnapshot.getValue(ProfileModel.class);
                if (model != null) {
                    userName.setText(model.getName());
                    userEmail.setText(model.getEmail());
                    number.setText(model.getNumber());
                    address.setText(model.getAddress());
                    Glide.with(getActivity()).load(model.getImageUrl()).placeholder(R.drawable.profile).into(userProfileImage);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }


}