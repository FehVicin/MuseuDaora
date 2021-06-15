package com.example.appmuseu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class Cafe extends AppCompatActivity implements OnSuccessListener<Location>, OnFailureListener {

    public final static int LOCATION_CODE = 1;
    TextView txtlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        txtlocation = findViewById(R.id.txtlocation);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
            Toast toast = Toast.makeText(this, "A permissão não foi concedida", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            final FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
            fusedLocationProviderClient.getLastLocation().addOnFailureListener(this);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e){
        Log.e("Location not detected", "errors", e);}

    @Override
    public void onSuccess(Location location){
        if(location != null){
            double latitude1 = location.getLatitude();
            double longitude1 = location.getLongitude();
            Geocoder geocoder = new Geocoder(Cafe.this, Locale.getDefault());
            try{
                List<Address> locales = geocoder.getFromLocation(40.7844, -73.9582, 1);
                if(locales.size() == 0){
                    return;
                }
                Address local = locales.get(0);
                double latitude2 = local.getLatitude();
                double longitude2 = local.getLongitude();
                float[] results = new float[1];
                Location.distanceBetween(latitude1, longitude1, latitude2, longitude2, results);
                float results2 = results[0] / 1000;

                txtlocation.setText(String.valueOf("Por mais que você esteja a " + results2 + " km de distância de nós. Por que não passar para tomar um cafezinho?"));
            } catch (Exception e){
                Log.e("Exception", "errors", e);
            }
        }
        else{
            txtlocation.setText(String.valueOf("Parece que alguém esta perdido 🙄"));
        }
    }

}