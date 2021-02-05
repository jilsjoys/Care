package com.example.care;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationResult;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MyLocationService extends BroadcastReceiver implements OnMapReadyCallback {
public  static final String ACTION_PROCESS_UPDATE="com.example.care.UPDATE_LOCATION";
    DatabaseReference publiclocation;
    private String currentUserID;
    private FirebaseAuth mAuth;
  int uid;

    public MyLocationService()
    {
        publiclocation= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

    }


    @Override
    public void onReceive(final Context context, Intent intent) {


if(intent!=null)
{

    final String action=intent.getAction();
    if(ACTION_PROCESS_UPDATE.equals(action))
    {
        LocationResult resul =LocationResult.extractResult(intent);


        if (resul!=null)
        {

            Location location=resul.getLastLocation();
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

            LatLng latlong =new LatLng(location.getLatitude(),location.getLongitude());

            try {



               publiclocation.child("location").child(currentUserID).push().setValue(latlong);



            }
            catch (Exception e)
            {
                Toast.makeText(context, "eRROORRR", Toast.LENGTH_SHORT).show();
               // publiclocation.child("location").setValue(location);
            }
            }
        }
    }

}


    @Override
    public void onMapReady(GoogleMap googleMap) {
    }
}

