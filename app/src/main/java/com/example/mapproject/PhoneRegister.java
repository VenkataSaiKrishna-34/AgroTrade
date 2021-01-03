package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PhoneRegister extends AppCompatActivity {

    EditText email,name,city;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        register = findViewById(R.id.btn1);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fstore.collection("users").document(userID);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !city.getText().toString().isEmpty())
                {
                    String Name = name.getText().toString();
                    String Email = email.getText().toString();
                    String City = city.getText().toString();

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+";
                    if(!Email.matches(emailPattern))
                    {
                        email.setError("Enter Valid Email");
                        return;
                    }

                    Map<String, Object> user = new HashMap<>();
                    user.put("name",Name);
                    user.put("email",Email);
                    user.put("city",City);

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               startActivity(new Intent(getApplicationContext(),MainActivity.class));
                               finish();
                           }
                           else
                           {
                               Toast.makeText(PhoneRegister.this, "Details are not inserted", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                else
                {
                    Toast.makeText(PhoneRegister.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
