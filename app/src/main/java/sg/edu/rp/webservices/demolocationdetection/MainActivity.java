package sg.edu.rp.webservices.demolocationdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    Button btnGetLocation, btnReceiveUpdate, btnRemoveUpdate;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnReceiveUpdate = findViewById(R.id.btnReceiveUpdate);
        btnRemoveUpdate = findViewById(R.id.btnRemoveUpdate);

        client = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(10000);
        mlocationRequest.setFastestInterval(5000);
        mlocationRequest.setSmallestDisplacement(100);

        LocationCallback mlocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                    Location data = locationResult.getLastLocation();
                    double lat = data.getLatitude();
                    double lng = data.getLongitude();
                    Toast.makeText(getApplicationContext(),"Lat: " + lat + ", Long: " + lng,Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnGetLocation.setOnClickListener(v -> {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(),"Permission Given",Toast.LENGTH_SHORT).show();
            }
        });

        btnReceiveUpdate.setOnClickListener(v -> {
            if (checkPermission())
                client.requestLocationUpdates(mlocationRequest, mlocationCallback, null);
            else
                Log.i("Permission", "Denied");
        });
        btnRemoveUpdate.setOnClickListener(v -> {
        });
    }

    private boolean checkPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }
}