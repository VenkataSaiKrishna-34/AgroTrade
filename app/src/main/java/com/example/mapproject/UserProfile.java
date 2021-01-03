package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

import static android.content.Intent.ACTION_PICK;

public class UserProfile extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    TextView profileName, profileEmail, profileCity, profilePhone;
    Toolbar toolbar;
    ImageView profileImage,changeProfile, editName, editCity;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    StorageReference storageRef;
    EditText editNameFeild,editCityFeild;
    Button save;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.three_dot_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.WhatsAppUs:
//                Intent intent = new Intent(UserProfile.this, ContactUs.class);
//                startActivity(intent);

                    boolean appInstalled;

                    PackageManager packageManager = getPackageManager();
                    try
                    {
                        PackageInfo packageInfo = packageManager.getPackageInfo("com.whatsapp", packageManager.GET_META_DATA);
                        appInstalled = true;
                    }
                    catch (PackageManager.NameNotFoundException e)
                    {
                        appInstalled =false;
                        e.printStackTrace();
                    }

                    if(appInstalled==true)
                    {
                        Uri uri = Uri.parse("smsto:"+"8008638533");
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        //intent.setType("text/plain");
                        intent.setPackage("com.whatsapp");
                        //intent.putExtra(Intent.EXTRA_TEXT,msg);
                        //intent = Intent.createChooser(intent,"send Message");
                        startActivity(intent);
                    }
//                Toast.makeText(this, "Contact us is selected", Toast.LENGTH_SHORT).show();
                return true;

//            case R.id.aboutUs:
//                Intent intent = new Intent(UserProfile.this, AboutUs.class);
//                startActivity(intent);
//                return true;

            case R.id.contactUs:
                Intent intent1  = new Intent(Intent.ACTION_CALL);
                String contact  = "9441812650";
                Uri uri = Uri.parse("tel:91"+contact);
                intent1.setData(uri);
                String []perms = {Manifest.permission.CALL_PHONE};
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,perms,100);
                }
                startActivity(intent1);
                return true;

            case R.id.address:
                Intent intent2 = new Intent(UserProfile.this, Address.class);
                startActivity(intent2);
                return true;

            case R.id.order:
                Intent intent3 = new Intent(UserProfile.this, Orders.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setTitle("Profile");

        profileName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmail);
        profileCity = findViewById(R.id.profileCity);
        profilePhone = findViewById(R.id.profilePhone);
        profileImage = findViewById(R.id.profileImage1);
        changeProfile = findViewById(R.id.changeProfile);
        editName = findViewById(R.id.editName);
        editCity = findViewById(R.id.editCity);
        editNameFeild = findViewById(R.id.editNameField);
        save = findViewById(R.id.save);
        editCityFeild = findViewById(R.id.editCityField);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();



        StorageReference profileRef = storageRef.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        Button btn_logout = (Button) findViewById(R.id.logout);

            changeProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open gallery

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //                startActivityForResult(gallery,PICK_IMAGE);


    //                Intent gallery = new Intent();
    //                gallery.setType("image/*");
    //                gallery.setAction(Intent.ACTION_GET_CONTENT);
    //
                    startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

                }
            });

            btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogout();
            }
            });

        DocumentReference docRef = fstore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                profileName.setText(documentSnapshot.getString("name"));
                profileEmail.setText(documentSnapshot.getString("email"));
                profileCity.setText(documentSnapshot.getString("city"));
                String s = fAuth.getCurrentUser().getPhoneNumber();

                if(s!=null) profilePhone.setText(s);
                else profilePhone.setText(documentSnapshot.getString("phone"));
            }
        });

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNameFeild.setVisibility(view.VISIBLE);
                save.setVisibility(view.VISIBLE);
                profileName.setVisibility(view.GONE);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = editNameFeild.getText().toString();
                        editNameFeild.setVisibility(view.GONE);
                        save.setVisibility(view.GONE);
                        profileName.setText(name);
                        profileName.setVisibility(view.VISIBLE);
                    }
                });
            }
        });

        editCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCityFeild.setVisibility(view.VISIBLE);
                save.setVisibility(view.VISIBLE);
                profileCity.setVisibility(view.GONE);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = editCityFeild.getText().toString();
                        editCityFeild.setVisibility(view.GONE);
                        save.setVisibility(view.GONE);
                        profileCity.setText(name);
                        profileCity.setVisibility(view.VISIBLE);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null)
        {
                imageUri = data.getData();
//                profileImage.setImageURI(imageUri);
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                    profileImage.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri)
    {
        //upload image to firebase

        final StorageReference fileRef = storageRef.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(UserProfile.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userLogout()
    {
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
        finish();
    }
}
