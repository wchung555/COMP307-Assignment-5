package com.winniechung_josephtinoco_comp307lab5_ex1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
/*

MAP ACTIVITY

Displays the map and markers for the selected restaurant

*/

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private String chosenRestaurant;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setTitle(R.string.map);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            chosenRestaurant = intent.getStringExtra("restaurant");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Context self = this;

        // Apply markers after the map is fully loaded.
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Geocoder coder = new Geocoder(self, Locale.CANADA);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                LatLngBounds bounds;
                int padding = 20;
                try {
                    List<Address> geocodeResults = coder.getFromLocationName(chosenRestaurant, 3, 43.34, -79.79, 44.12, -79.06);
                    Iterator<Address> locations = geocodeResults.iterator();
                    while (locations.hasNext()) {
                        Address location = locations.next();
                        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                        String label = "";
                        for (int i = 1; i < location.getMaxAddressLineIndex(); i++) {
                            label += location.getAddressLine(i);
                            if (i != location.getMaxAddressLineIndex() - 1) {
                                label += "\n";
                            }
                        }
                        builder.include(coordinates);
                        mMap.addMarker(new MarkerOptions().position(coordinates).title(chosenRestaurant).snippet(label));

                        // Prepare an info window to display the address
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                            @Override
                            public View getInfoWindow(Marker arg0) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {

                                LinearLayout info = new LinearLayout(self);
                                info.setOrientation(LinearLayout.VERTICAL);

                                TextView title = new TextView(self);
                                title.setTextColor(Color.BLACK);
                                title.setGravity(Gravity.CENTER);
                                title.setTypeface(null, Typeface.BOLD);
                                title.setText(marker.getTitle());

                                TextView snippet = new TextView(self);
                                snippet.setTextColor(Color.GRAY);
                                snippet.setText(marker.getSnippet());

                                info.addView(title);
                                info.addView(snippet);

                                return info;
                            }
                        });
                    }
                    bounds = builder.build();
                    CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.setMaxZoomPreference(15.0f);
                    mMap.moveCamera(camera);
                } catch (IOException e) {
                    Log.d(getString(R.string.app_name), e.toString());
                }
            }
        });


    }
}
