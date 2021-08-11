package codercamp.com.e_commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import codercamp.com.e_commerce.R;

public class LogInActivity extends AppCompatActivity {
    private EditText email, password;
    private CircularProgressButton SingInButton;
    private TextView SingUpText;
    private String UserEmail, UserPassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DatabaseReference reference;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (user != null) {
            String userId = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        }


    }

    private void initView() {

        email = findViewById(R.id.UserEmailLogIn);
        password = findViewById(R.id.UserPasswordLogIn);
        SingInButton = findViewById(R.id.SingInBtn);
        SingUpText = findViewById(R.id.SingUPText);
        progressBar = findViewById(R.id.LogInPrograss);


        SingUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SingUpActivity.class));
                Animatoo.animateZoom(LogInActivity.this);
                finish();
            }
        });

        SingInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(LogInActivity.this, "Sing In Successfully", Toast.LENGTH_SHORT).show();

                UserEmail = email.getText().toString();
                UserPassword = password.getText().toString();

                if (UserEmail.isEmpty()) {
                    email.setError("Please Enter Email");
                    email.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
                    email.setError("Please Enter Valid Email");
                    email.requestFocus();
                    return;
                } else if (UserPassword.isEmpty()) {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                    return;
                } else if (UserPassword.length() < 8) {
                    password.setError("Password Length must be greater then 8 Character");
                    password.requestFocus();
                    return;
                } else {
                    SingIn();
                }

            }
        });
    }

    private void SingIn() {

        SingInButton.startAnimation();

        auth.signInWithEmailAndPassword(UserEmail, UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SingInButton.stopAnimation();
                    Toast.makeText(LogInActivity.this, "Sing In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    Animatoo.animateZoom(LogInActivity.this);
                    finish();
                } else {
                    SingInButton.stopAnimation();
                    Toast.makeText(LogInActivity.this, "Sing In Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                SingInButton.stopAnimation();
                Toast.makeText(LogInActivity.this, "Sing In Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SingInButton.dispose();
    }
}