package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_New_Account extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText edit_name,edit_email,edit_password,edit_phone,edit_city;
    Button btn_signup;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__account);

        edit_name = (EditText) findViewById(R.id.name);
        edit_email = (EditText) findViewById(R.id.email);
        edit_password = (EditText) findViewById(R.id.password);
        edit_phone = (EditText) findViewById(R.id.phone);
        edit_city = (EditText) findViewById(R.id.city);

        btn_signup = (Button) findViewById(R.id.signup);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email  = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                final String name = edit_name.getText().toString().trim();
                final String phone = edit_phone.getText().toString().trim();
                final String city = edit_city.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edit_email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    edit_password.setError("Password is required");
                    return;
                }

                if(password.length() < 6)
                {
                    edit_password.setError("Password must be >= 6 chars");
                    return;
                }

                if(phone.length() < 10)
                {
                    edit_phone.setError("Invalid phone number");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Create_New_Account.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("city",city);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user profile is created for "+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Create_New_Account.this, "Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}
