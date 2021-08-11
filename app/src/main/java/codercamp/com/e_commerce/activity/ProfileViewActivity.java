package codercamp.com.e_commerce.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import codercamp.com.e_commerce.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewActivity extends AppCompatActivity {

    private EditText userName, userEmail, number, address;
    private CircleImageView userProfileImage;
    private MaterialButton Update;
    private String Name, Email, Number, Address;
    private int RequestCode = 111;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private Uri uri;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        toolbar = findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animatoo.animateSlideRight(ProfileViewActivity.this);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading.... ");

        initView();
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RequestCode);
            }
        });
    }

    private void initView() {

        userName = findViewById(R.id.ProUserName);
        userEmail = findViewById(R.id.ProUserEmail);
        number = findViewById(R.id.ProUserPhone);
        address = findViewById(R.id.ProUserAddress);
        userProfileImage = findViewById(R.id.ProfileImage);

        Update = findViewById(R.id.ProUpdateBtn);


    }

    private void UpdateProfile() {

        Name = userName.getText().toString();
        Email = userEmail.getText().toString();
        Number = number.getText().toString();
        Address = address.getText().toString();


        if (Name.isEmpty()) {
            userName.setError("Please Enter Name");
            userName.requestFocus();
        } else if (Email.isEmpty()) {
            userEmail.setError("Please Enter Email");
            userEmail.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            userEmail.setError("Please Enter Valid Email");
            userEmail.requestFocus();

        } else if (Number.isEmpty()) {
            number.setError("Please Enter Number");
            number.requestFocus();

        } else if (Address.isEmpty()) {
            address.setError("Please Enter Address");
            address.requestFocus();

        }

        UpdateData();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            userProfileImage.setImageURI(uri);

        }
        else {
            Toast.makeText(this, "Pleas Select An Image", Toast.LENGTH_SHORT).show();

        }
    }

    private void UploadImage() {
        if (uri != null){
        progressDialog.show();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(user.getUid());

        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String, Object> update = new HashMap<>();

                            update.put("name", Name);
                            update.put("email", Email);
                            update.put("number", Number);
                            update.put("Address", Address);
                            update.put("imageUrl", uri.toString());
                            reference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileViewActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ProfileViewActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                            reference.child("name").child(Name.toString().toString());
//                            reference.child("email").child(Email.toString().toString());
//                            reference.child("number").child(Number.toString().toString());
//                            reference.child("address").child(Address.toString().toString());
                            userProfileImage.setImageResource(R.drawable.profile);
                            progressDialog.dismiss();
                            finish();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ProfileViewActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", e.getMessage());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress);
            }
        });

        }
        else {
            Toast.makeText(this, "Pleas Select An Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateData() {
        UploadImage();
    }
}