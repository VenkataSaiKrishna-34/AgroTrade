package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    EditText edit_email,edit_password;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    Button btn_login,btn_new_account,btn_mobile;
    TextView forgotPass;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        edit_email = findViewById(R.id.email);
        edit_password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);
        fAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.login);
        forgotPass = findViewById(R.id.forgotten_paasword);

        btn_new_account = (Button) findViewById(R.id.new_account);
        btn_mobile = (Button) findViewById(R.id.continue_mobile);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreate_New_Account();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edit_email.setError("Email is required");
                    return;
                }

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+";
                if(!email.matches(emailPattern))
                {
                    edit_email.setError("Enter Valid Email");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    edit_password.setError("Password is Required");
                    return;
                }

                if(password.length() < 6)
                {
                    edit_password.setError("Password must be >= 6 chars");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login_Activity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhone_Login();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());


                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to receive reset link");
                passwordResetDialog.setView(resetMail);



                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail = resetMail.getText().toString();

                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+";
                        if(!mail.matches(emailPattern))
                        {
                            Toast.makeText(Login_Activity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login_Activity.this, "Reset Link sent to your mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login_Activity.this, "Error! reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }

    public void startCreate_New_Account()
    {
        Intent intent = new Intent(this, Create_New_Account.class);
        startActivity(intent);
    }

    public  void startPhone_Login()
    {
        Intent intent = new Intent(this, Phone_login.class);
        startActivity(intent);
    }
}
