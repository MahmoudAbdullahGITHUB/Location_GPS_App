package com.example.beso.gps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final long meter = 1;
    static final long mill = 1000;
    private static final int R_per = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("here is ");

        if ((int) Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, R_per);
                }
                return;
            }

        }

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, meter, mill, new locationListner(this));

    }

    ProgressDialog dialog;
    Context context;

    public void buLocation(View view) {
        context = this;
        dialog = new ProgressDialog(MainActivity.this);
        dialog.show();
        dialog.setMessage("Getting Last Location");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocationGPS = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if (lastKnownLocationGPS == null)
            lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        textView1.setText(Double.toString(lastKnownLocationGPS.getLongitude()));
        textView2.setText(Double.toString(lastKnownLocationGPS.getLatitude()));

        dialog.dismiss();


    }
}
