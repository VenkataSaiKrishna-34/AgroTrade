package com.example.mapproject;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class ContactUs extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contact_us);
//    }
//}


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Button button = findViewById(R.id.Call);
        Button button1 = findViewById(R.id.whatsApp);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                dail();
            }
        } );

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();

            }
        });
    }
    void dail()
    {
        Intent intent  = new Intent(Intent.ACTION_CALL);
        String contact  = "9441812650";
        Uri uri = Uri.parse("tel:91"+contact);
        intent.setData(uri);
        String []perms = {Manifest.permission.CALL_PHONE};
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,perms,100);
        }
        startActivity(intent);
    }

    void sendMsg()
    {
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


    }
}