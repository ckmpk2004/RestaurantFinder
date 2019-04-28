package com.example.restaurantfinder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String Lat;
    private String Long;
    private Double curLat;
    private Double curLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Lat = intent.getStringExtra("Lat_EXTRA");
        Long = intent.getStringExtra("Long_EXTRA");
        curLat = intent.getDoubleExtra("Current_Lat_EXTRA", 10);
        curLong = intent.getDoubleExtra("Current_Long_EXTRA", 10);
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

        double shop_lat = Double.parseDouble(Lat);
        double shop_Long = Double.parseDouble(Long);

        // Add a marker in Sydney and move the camera
        LatLng shop = new LatLng(shop_lat, shop_Long);
        LatLng curLocation = new LatLng(curLat, curLong);
        mMap.addMarker(new MarkerOptions().position(shop).title("Targeted Shop Location"));
        mMap.addMarker(new MarkerOptions().position(curLocation).title("Your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop, 18f));

        mMap.addPolyline(new PolylineOptions()
                .add(shop)
                .add(curLocation)
                .width(8f)
                .color(Color.RED)
        );
    }

    @Override
    public void onPause() {
        mMap.clear();
        super.onPause();
    }
}
