package com.example.mapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Address extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        final TextView name = findViewById(R.id.name);
        final TextView street = findViewById(R.id.street);
        final TextView area = findViewById(R.id.area);
        final TextView city = findViewById(R.id.city);
        final TextView state = findViewById(R.id.state);
        final TextView pincode = findViewById(R.id.pincode);
        final TextView phone = findViewById(R.id.phone);

        DocumentReference docRef = fStore.collection("address").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("name"));
                street.setText(documentSnapshot.getString("street"));
                area.setText(documentSnapshot.getString("area"));
                city.setText(documentSnapshot.getString("city"));
                state.setText(documentSnapshot.getString("state"));
                pincode.setText(documentSnapshot.getString("pincode"));
                phone.setText(documentSnapshot.getString("phone"));
            }
        });


    }
}
