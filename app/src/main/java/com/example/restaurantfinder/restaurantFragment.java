package com.example.restaurantfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class restaurantFragment extends Fragment {
    private View view;
    private ListView listView;
    private TextView textView;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;

    private static final int REQUEST_LOCATION = 1;
    private double latitude, longitude;
    private double curLat, curLong;
    private String currentArea;


    //Array List
    private ArrayList<String> restaurants = new ArrayList<>();//For display restaurant
    private ArrayList<DataSnapshot> datas = new ArrayList<>();//For google map




    @Nullable
    @Override
    public View onCreateView(LayoutInflater i, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = i.inflate(R.layout.tab_restaurant, container, false);
        textView = (TextView) view.findViewById(R.id.test_textView);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }


          LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude,longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);

                curLat = location.getLatitude();
                curLong = location.getLongitude();

                currentArea = cityName;
                textView.setText(cityName);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listener);



        return view;
    }
    @Override
    public void onResume(){
        //Create List of shop base on user current Location
        //Then put them into List View
        listView = (ListView) view.findViewById(R.id.list_restaurant);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, restaurants);
        listView.setAdapter(arrayAdapter);


        //Try Connect FireBase
        if (getFireBase("HangHau")) {

            //Whenever a single data get from Firebase
            rootRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                    final String shopName = dataSnapshot.getKey();
                    final String shopLocation = (String) dataSnapshot.child("location").getValue();
                    restaurants.add(shopName + "\n" + shopLocation);
                    datas.add(dataSnapshot);
                    arrayAdapter.notifyDataSetChanged();


                    //When User clicked ListView Item
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent newIntent = new Intent(getActivity(), GoogleMapActivity.class);

                            //ListView items'X Y coord
                            final String Lat = datas.get(position).child("lit").getValue().toString();
                            final String Long = datas.get(position).child("long").getValue().toString();

                            //Pass Data to Google Map Activity
                            newIntent.putExtra("Lat_EXTRA", Lat);
                            newIntent.putExtra("Long_EXTRA", Long);
                            newIntent.putExtra("Current_Lat_EXTRA", curLat);
                            newIntent.putExtra("Current_Long_EXTRA", curLong);

                            startActivity(newIntent);
                        }
                    });
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            restaurants.add("Error Occur: FireBase Connection Failure");
            arrayAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    @Override
    public void onPause(){
        restaurants.removeAll(restaurants);
        super.onPause();
    }

    public boolean getFireBase(String path) {
        try {
            database = FirebaseDatabase.getInstance();
            rootRef = database.getReference("restaurants/" + path);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

