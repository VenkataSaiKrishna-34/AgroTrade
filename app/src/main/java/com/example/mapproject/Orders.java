package com.example.mapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Orders extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        final ImageView image = findViewById(R.id.image);
        final TextView title = findViewById(R.id.title);
        final TextView location = findViewById(R.id.location);
        final TextView price = findViewById(R.id.price);

        DocumentReference docRef = fStore.collection("orders").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                title.setText(documentSnapshot.getString("crop_name"));
                location.setText("Location: "+documentSnapshot.getString("location"));
                price.setText("Rs."+documentSnapshot.getString("price")+"/Quintal");
                image.setImageResource(Integer.parseInt(documentSnapshot.getString("image")));

            }
        });


    }
}
