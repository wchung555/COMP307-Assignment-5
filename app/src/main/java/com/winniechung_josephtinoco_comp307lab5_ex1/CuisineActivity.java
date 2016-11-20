package com.winniechung_josephtinoco_comp307lab5_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CuisineActivity extends AppCompatActivity {

    private ListView cuisines;
    private String[] cuisineArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);

        setTitle(getString(R.string.step1));

        cuisineArray = getResources().getStringArray(R.array.cuisines);

        cuisines = (ListView) findViewById(R.id.cuisines);
        cuisines.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cuisineArray));
        cuisines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                intent.putExtra("cuisine", cuisineArray[i]);
                startActivity(intent);
            }
        });
    }
}
