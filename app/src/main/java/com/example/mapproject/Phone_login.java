package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Phone_login extends AppCompatActivity {

    EditText edit_phone,edit_otp;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ProgressBar progressBar;
    TextView state;
    Button send_otp;
    CountryCodePicker codePicker;
    String verificationID;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        Button button = (Button) findViewById(R.id.continue_email);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEmail();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        edit_phone = findViewById(R.id.phone);
        edit_otp = findViewById(R.id.otp);
        progressBar = findViewById(R.id.progressBar);
        send_otp = (Button) findViewById(R.id.send_otp);
        state = findViewById(R.id.state);
        codePicker = findViewById(R.id.ccp);


        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verificationInProgress)
                {
                    if(!edit_phone.getText().toString().isEmpty() && edit_phone.getText().toString().length() == 10)
                    {
                        String phone_num = "+"+codePicker.getSelectedCountryCode()+edit_phone.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP...");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phone_num);
                    }
                    else
                    {
                        edit_phone.setError("Invalid phone number");
                    }
                }
                else
                {
                    String userOTP = edit_otp.getText().toString();

                    if(!userOTP.isEmpty() && userOTP.length() == 6)
                    {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,userOTP);
                        verifyAuth(credential);
                    }
                    else
                    {
                        edit_otp.setError("Invalid OTP");
                    }
                }
            }
        });
    }

    protected void onStart()
    {
        super.onStart();

        if(fAuth.getCurrentUser() != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            state.setText("Checking...");
            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        }
    }

    public void verifyAuth(PhoneAuthCredential credential)
    {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    checkUserProfile();
                }
                else
                {
                    Toast.makeText(Phone_login.this, "Authentication is failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkUserProfile()
    {
        DocumentReference docRef = fstore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(),PhoneRegister.class));
                    finish();
                }
            }
        });
    }

    public void requestOTP(String edit_phone)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(edit_phone, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                edit_otp.setVisibility(View.VISIBLE);
                verificationID = s;
                token = forceResendingToken;
                send_otp.setText("Verify");
                verificationInProgress = true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Phone_login.this, "Cannot Create Account" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startEmail()
    {
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
    }


}
