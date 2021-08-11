package codercamp.com.e_commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codercamp.com.e_commerce.R;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (user != null) {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    Animatoo.animateZoom(SplashActivity.this);

                } else {
                    startActivity(new Intent(SplashActivity.this, SingUpActivity.class));
                    Animatoo.animateZoom(SplashActivity.this);
                }

                finish();
            }
        }, 2000);
    }
}