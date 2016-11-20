package com.winniechung_josephtinoco_comp307lab5_ex1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
    private boolean permissionGranted = true;

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
                if (Build.VERSION.SDK_INT >= 23 &&
                        (checkSelfPermission(android.Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{android.Manifest.permission.LOCATION_HARDWARE}, 123);
                }

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("restaurant", restaurantArray[i]);
                if (permissionGranted) {
                    startActivity(intent);
                } else {
                    Toast.makeText(RestaurantActivity.this, "Insufficient permissions: cannot access GPS functionality", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissionGranted = false;
                    Toast.makeText(RestaurantActivity.this, "Cannot proceed without access to GPS functionality", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
