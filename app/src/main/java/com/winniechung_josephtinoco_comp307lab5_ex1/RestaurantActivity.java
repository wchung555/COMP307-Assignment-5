package com.winniechung_josephtinoco_comp307lab5_ex1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RestaurantActivity extends AppCompatActivity {

    private String chosenCuisine;
    private ListView restaurants;
    private String[] restaurantArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        setTitle(getString(R.string.step2));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            chosenCuisine = intent.getStringExtra("cuisine");
        }

        ((TextView) findViewById(R.id.chosenCuisine)).setText(chosenCuisine);

        restaurants = (ListView) findViewById(R.id.restaurants);

        switch (chosenCuisine) {
            case "American":
                restaurantArray = getResources().getStringArray(R.array.american_restaurants);
                break;
            case "Italian":
                restaurantArray = getResources().getStringArray(R.array.italian_restaurants);
                break;
            case "Mediterranean":
                restaurantArray = getResources().getStringArray(R.array.mediterranean_restaurants);
                break;
            case "Chinese":
                restaurantArray = getResources().getStringArray(R.array.chinese_restaurants);
                break;
            case "Japanese":
                restaurantArray = getResources().getStringArray(R.array.japanese_restaurants);
                break;
            default:
                break;
        }
        restaurants.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantArray));
        restaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("restaurant", restaurantArray[i]);
                startActivity(intent);
            }
        });
    }
}
