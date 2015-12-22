package com.home.amirraza.test;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    double latt,lngg;
    private Tracker tracker;
    private Button map_hybrid;
    private Button map_normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map_hybrid = (Button) findViewById(R.id.map_hybrid);
        map_normal = (Button) findViewById(R.id.map_normal);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

    }

    private void enableTracking() {
        tracker = new Tracker(this);
        if(tracker.isCanGetLocation()){
            latt = tracker.getLat();
            lngg = tracker.getLng();
            Toast.makeText(this, "Your Location is - \nLat: " + latt + "\nLong: " + lngg, Toast.LENGTH_LONG).show();
        }
        else{
            tracker.settingGPS();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        enableTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.stopGPS();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(latt,lngg);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Hi You are Here"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(latLng)
                .zoom(13)
                .bearing(90)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

        map_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        map_hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });


    }
}
