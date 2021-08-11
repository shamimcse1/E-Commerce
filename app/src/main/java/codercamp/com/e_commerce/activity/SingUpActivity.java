package codercamp.com.e_commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import codercamp.com.e_commerce.R;

public class SingUpActivity extends AppCompatActivity {


    private EditText username, email, number, password;
    private CircularProgressButton singUpButton;
    private TextView SingInText;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserName, UserEmail, UserNumber, UserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        initView();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        //String userId = user.getUid();

    }

    private void initView() {

        username = findViewById(R.id.UserName);
        number = findViewById(R.id.UserPhone);
        email = findViewById(R.id.UserEmail);
        password = findViewById(R.id.UserPassword);

        singUpButton = (CircularProgressButton) findViewById(R.id.SingUpBtn);

        SingInText = findViewById(R.id.SingInText);



        SingInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SingUpActivity.this, LogInActivity.class));
                Animatoo.animateZoom(SingUpActivity.this);
                finish();
            }
        });

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserName = username.getText().toString();
                UserEmail = email.getText().toString();
                UserNumber = number.getText().toString();
                UserPassword = password.getText().toString();

                if (UserName.isEmpty()) {
                    username.setError("Please Enter Name");
                    username.requestFocus();
                    return;
                } else if (UserEmail.isEmpty()) {
                    email.setError("Please Enter Email");
                    email.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
                    email.setError("Please Enter Valid Email");
                    email.requestFocus();
                    return;
                } else if (UserNumber.isEmpty()) {
                    number.setError("Please Enter Number");
                    number.requestFocus();
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
                    CreateUser();
                }

            }
        });
    }

    private void CreateUser() {

        singUpButton.startAnimation();

        auth.createUserWithEmailAndPassword(UserEmail, UserPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UpdateUI();
                        } else {
                            singUpButton.stopAnimation();
                            Toast.makeText(SingUpActivity.this, "Sing Up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                singUpButton.stopAnimation();
                Toast.makeText(SingUpActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void UpdateUI() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate_andTime = sdf.format(new Date());

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", UserName);
        map.put("email", UserEmail);
        map.put("number", UserNumber);
        map.put("password", UserPassword);
        map.put("date", currentDate_andTime);

        reference.child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    singUpButton.stopAnimation();
                    Toast.makeText(SingUpActivity.this, "Sing Up Successfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(SingUpActivity.this, "Database Update Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SingUpActivity.this, LogInActivity.class));
                    Animatoo.animateZoom(SingUpActivity.this);
                    finish();
                } else {
                    singUpButton.stopAnimation();
                    Toast.makeText(SingUpActivity.this, "Database Update Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                singUpButton.stopAnimation();
                Toast.makeText(SingUpActivity.this, "Database Update Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        singUpButton.dispose();
    }
}