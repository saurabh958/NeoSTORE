package com.example.saurabhneostore.drawer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.StoreLocatorAdapter;
import com.example.saurabhneostore.model.Store.LocationModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StoreLocator extends AppCompatActivity implements OnMapReadyCallback {

    RecyclerView recyclerView;
    StoreLocatorAdapter storeLocatorAdapter;
    private GoogleMap mMap;
    LatLng mumbai;
    ImageButton backbutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_locator);
        recyclerView = findViewById(R.id.store_recycler);
        backbutton=findViewById(R.id.store_backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLocator.super.onBackPressed();
            }
        });

        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "location.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<LocationModel>>() {
        }.getType();

        List<LocationModel> users = gson.fromJson(jsonFileString, listUserType);
        for (int i = 0; i < users.size(); i++) {
            Log.i("data", "> Item " + i + "\n" + users.get(i));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        storeLocatorAdapter = new StoreLocatorAdapter(StoreLocator.this, users);
        recyclerView.setAdapter(storeLocatorAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

// Add a marker in Sydney and move the camera
        mumbai = new LatLng(19.2297281, 72.845639);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Royal Touche"));
// mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mumbai = new LatLng(19.2337028, 72.8621114);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("A to Z Furnishing"));
// mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mumbai = new LatLng(19.22786, 72.8551824);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Godrej Interio-Furniture Store"));
// mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mumbai = new LatLng(19.232180, 72.869150);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Shree Mahalaxmi Furniture"));
// mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mumbai = new LatLng(19.229500, 72.860320);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Radha Krushna Furniture"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mumbai, 13.0f));


    }


}
