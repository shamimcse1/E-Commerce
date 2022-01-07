package codercamp.com.e_commerce.Fragment.Logut;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.activity.LogInActivity;


public class LogoutFragment extends Fragment {

    public Button logoutButton;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);


        logoutButton = view.findViewById(R.id.log_out);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LogInActivity.class));
                Animatoo.animateZoom(getActivity());
                getActivity().finish();

            }
        });
        return view;
    }
}