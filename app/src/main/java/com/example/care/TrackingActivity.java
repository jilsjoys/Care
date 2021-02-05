package com.example.care;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class TrackingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference trackinguserlocation;
    private String currentUserID;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        trackinguserlocation= FirebaseDatabase.getInstance().getReference().child("location").child(currentUserID);


    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

       mMap.getUiSettings().setZoomControlsEnabled(true);
mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.my_uber_style));
        trackinguserlocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String lat = child.child("latitude").getValue().toString();
                    String lng = child.child("longitude").getValue().toString();

                    double latitude = Double.parseDouble(lat);
                    double longitude = Double.parseDouble(lng);
                    LatLng loc = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(loc).title(""));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,16),4000,null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        }




}