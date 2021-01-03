package com.example.mapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Confirm_purchase extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        Button btn = findViewById(R.id.proceed);

        final EditText editName = findViewById(R.id.edit_name);
        final EditText editStreet = findViewById(R.id.edit_street);
        final EditText editArea = findViewById(R.id.edit_area);
        final EditText editCity = findViewById(R.id.edit_city);
        final EditText editState = findViewById(R.id.edit_state);
        final EditText editPincode = findViewById(R.id.edit_pincode);
        final EditText editPhone = findViewById(R.id.edit_phone);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editPincode.getText().toString()))
                {
                    editPincode.setError("Pincode is required");
                    return;
                }

                if(TextUtils.isEmpty(editName.getText().toString()))
                {
                    editName.setError("Name is required");
                    return;
                }

                String userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference;
                Map<String,Object> user_address = new HashMap<>();


                user_address.put("name",editName.getText().toString());
                user_address.put("street",editStreet.getText().toString());
                user_address.put("area",editArea.getText().toString());
                user_address.put("city",editCity.getText().toString());
                user_address.put("state",editState.getText().toString());
                user_address.put("pincode",editPincode.getText().toString());
                user_address.put("phone",editPhone.getText().toString());

//                documentReference.
                fStore.collection("address").document(userID).set(user_address);

                Intent intent3 = new Intent(Confirm_purchase.this, ThankYou.class);
                startActivity(intent3);
            }
        });


    }
}
