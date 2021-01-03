package com.example.mapproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnNoteListener{

    Toolbar toolbar;
    //SharedPreferences pref;
    MyAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Item>ItemsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Agro Trade");

        ImageView btn_profile = findViewById(R.id.profile1);



//        Button btn_logout = (Button) findViewById(R.id.logout);
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userLogout();
//            }
//        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfile();
            }
        });

        ImageButton button = (ImageButton)findViewById(R.id.sortB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDialog();
            }
        });




        String onion, potato, tomato, sugarcane, wheat, brinjal, cotton,  carrot, chilli, lemon, capsicum, spinach;
        onion="India is the second largest onion growing country in the world. Indian onions are famous for their " +
                "pungency and are available round the year. Onions have two crop cycles, first harvesting starts in November to " +
                "January and the second harvesting from January to May.";
        potato = "Potato is the most important food crop of the world. Potato is a temperate crop grown under " +
                "subtropical conditions in India. The potato is a crop which has always been the ‘poor man’s friend’. For " +
                "vegetable purposes it has become one of the most popular crops in this country.  Potato is grown as a summer " +
                "crop in the hills and as a winter crop in the tropical and subtropical regions.";
        tomato = "Tomato is a warm season crop. Tomato can be grown on a wide range of soils from sandy to heavy" +
                "clay. It is a self pollinated crop. The major tomato producing states are Maharashtra, Bihar, Karnataka, Uttar " +
                "Pradesh, Orissa, Andhra Pradesh, Madhya Pradesh and Assam.";
        sugarcane = "Sugarcane  is the main sources of sugar in India and holds a prominent position as a cash " +
                "crop. India is the world’s largest consumer and the second largest producer of sugar, topped only by Brazil.";
        wheat = "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in " +
                "the country. Wheat crop has wide adaptability. It can be grown not only in the tropical and sub-tropical zones.";
        brinjal = "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in " +
                "the country. Wheat crop has wide adaptability. It can be grown not only in the tropical and sub-tropical zones.";
        cotton = "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in " +
                "the country. Wheat crop has wide adaptability. It can be grown not only in the tropical and sub-tropical zones.";
        carrot = "Carrot is a cool season crop and when grown at 15°C to 20°C will develop a good colour. The carrot " +
                "crop needs deep loose loamy soil. It requires a pH ranging from 6.0 to 7.0 for higher production. Carrot can be " +
                "grown in July - February.";
        chilli = "Carrot is a cool season crop and when grown at 15°C to 20°C will develop a good colour. The carrot " +
                "crop needs deep loose loamy soil. It requires a pH ranging from 6.0 to 7.0 for higher production. Carrot can be " +
                "grown in July - February.";
        lemon = "Lemons can be grown in all types of soils. Light soils having good drainage are suitable for its " +
                "cultivation. The best season for planting is July-August.";
        capsicum = "Capsicum, also known as sweet pepper, bell pepper or Shimla Mirch is one of the popular " +
                "vegetables grown throughout India. Capsicum is a cool season crop, but it can be grown round the year using " +
                "protected structures.";
        spinach = "It is perennial vegetable and cultivated throughout the world. It can be grown on any type of soil " +
                "having good drainage capacity. But it give good result when grown on sandy loam and alluvial soil.";


        SearchView searchView = (SearchView)findViewById(R.id.sV);
        recyclerView = findViewById(R.id.rv1);
        Item item1 = new Item(R.drawable.cotton, "Cotton", "Guntur", 4500,cotton,"Mohammed ali","9347502575",R.drawable.cotton_250);
        Item item2 = new Item(R.drawable.brinjal, "Brinjal", "Surat ", 2800,brinjal,"Harish singh","9381433601",R.drawable.brinjal_250);
        Item item3 = new Item(R.drawable.capsicum, "Capsicum", "Raigarh", 5300,capsicum,"Chakrapani","9393003351",R.drawable.capsicum_250);
        Item item4 = new Item(R.drawable.carrot, "Carrot", "Kathua ", 9500,carrot,"Amarnath","6302903513",R.drawable.carrot_250);
        Item item5 = new Item(R.drawable.chilles, "Chilles", "Chamba", 2700,chilli,"Venkat","9381538330",R.drawable.chilli_250);
        Item item6 = new Item(R.drawable.lemon, "Lemon", "Chathanoor ", 1500,lemon,"Badrinath","9381554429",R.drawable.lemon_250);
        Item item7 = new Item(R.drawable.wheat, "wheat", "Attingal ", 10000,wheat,"Dasanna","8115134567",R.drawable.wheat_250);
        Item item8 = new Item(R.drawable.potato, "Potato", "Dinanagar", 15500,potato,"Charith","8374675399",R.drawable.potato_250);
        Item item9 = new Item(R.drawable.onion, "Onion", "L B Nagar ", 8000,onion,"Venkatesh","9347422555",R.drawable.onion_250);
        Item item10 = new Item(R.drawable.tomato, "Tomato", "Raibareilly ", 7500,tomato,"Teja","9398078709",R.drawable.tomato_250);

        Item item11 = new Item(R.drawable.spinach, "Spinach", "Kalmeshwar", 725,spinach,"Nagendra","9581418984", R.drawable.spinach_250);
        Item item12 = new Item(R.drawable.sugarcane, "Sugarcane", "Ghaziabad ", 3450,sugarcane,"Ravi","9959916336",R.drawable.sugarcane_250);
        Item item13 = new Item(R.drawable.capsicum, "Capsicum", "Jalore", 3300,capsicum,"Krishna","8008638533",R.drawable.capsicum_250);
        Item item14 = new Item(R.drawable.carrot, "Carrot", "Kangra ", 7800,carrot,"Chandra","8125121612",R.drawable.carrot_250);
        Item item15 = new Item(R.drawable.chilles, "Chilles", "Nagpur", 9300,chilli,"Bharath","8309570871",R.drawable.chilli_250);
        Item item16 = new Item(R.drawable.lemon, "Lemon", "Mumbai ", 3600,lemon,"Ramchander","9440305963",R.drawable.lemon_250);
        Item item17 = new Item(R.drawable.wheat, "wheat", "Chala ", 13000,wheat,"Narappa","9524941575",R.drawable.wheat_250);
        Item item18 = new Item(R.drawable.potato, "Potato", "Pune", 2300,potato,"Girish","7894520107",R.drawable.potato_250);
        Item item19 = new Item(R.drawable.onion, "Onion", "Bargarh ", 8200,onion,"Raman chand","8309546123",R.drawable.onion_250);
        Item item20 = new Item(R.drawable.tomato, "Tomato", "karvi ", 5600,tomato,"Salaar","9038152164",R.drawable.tomato_250);
        // Item item21 = new Item(R.drawable.cotton, "Cotton", "Location: Adilabad ", 4850,des,n,p,R.drawable.cotton_250);
        // Item item22 = new Item(R.drawable.brinjal, "Brinjal", "Location: kullu ", 2000,,,,R.drawable.brinjal_250);



        ItemsList = new ArrayList<>();


        ItemsList.add(item1);
        ItemsList.add(item2);
        ItemsList.add(item3);
        ItemsList.add(item4);
        ItemsList.add(item5);
        ItemsList.add(item6);
        ItemsList.add(item7);
        ItemsList.add(item8);
        ItemsList.add(item9);
        ItemsList.add(item10);

        ItemsList.add(item11);
        ItemsList.add(item12);
        ItemsList.add(item13);
        ItemsList.add(item14);
        ItemsList.add(item15);
        ItemsList.add(item16);
        ItemsList.add(item17);
        ItemsList.add(item18);
        ItemsList.add(item19);
        ItemsList.add(item20);
        showData();



       /* String mSettings = pref.getString("Sort","ascending");
        if(mSettings.equals("ascending")){
            Collections.sort(ItemsList,Item.ASCENDING);


        }
        else if(mSettings.equals("descending")){
            Collections.sort(ItemsList,Item.DESCENDING);

        }*/






        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }

//    public void userLogout()
//    {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
//        finish();
//    }

    public void userProfile()
    {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);

//        startActivity(new Intent(getApplicationContext(),UserProfile.class));
    }


    public void showData()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(ItemsList,this);
        recyclerView.setAdapter(adapter);
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id==R.id.sort)
//        {
//            showSortDialog();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



    void showSortDialog()
    {
        String [] options = {"Price: Low-High", "Price: High-Low"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SortBy");
        //builder.setIcon(R.drawable.s);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    /*SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Sort", "ascending");
                    editor.apply();*/
                    Collections.sort(ItemsList,Item.ASCENDING);
                    showData();



                }
                if(which==1){
                   /* SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Sort", "descending");
                    editor.apply();*/
                    Collections.sort(ItemsList,Item.DESCENDING);
                    showData();




                }

            }
        });

        builder.create().show();

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ItemDetails.class);
        Item i1 = ItemsList.get(position) ;
        intent.putExtra("Send",  i1);
        startActivity(intent);
    }
}
