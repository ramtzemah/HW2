//package com.example.hw2;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.LocationListener;
//
//public class location implements LocationListener {
//private LocationManager locationManager;
//private String strlat = "";
//private String strlot = "";
//private Context context;
//private static final int ACCESS_FINE_LOCATION = 101;
//private Activity activity;
//
//    public location(LocationManager locationManager, TheGameActivity activityService) {
//        activity= activityService;
//        this.locationManager = locationManager;
//        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        this.context = activityService;
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull android.location.Location location) {
//            strlat = Double.toString(location.getLatitude());
//            strlot = Double.toString(location.getLongitude());
//
//    }
//
//    public void checkPermission(String permission, int requestCode)
//    {
//        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//            // Requesting the permission
//            ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
//        }
//        else {
//            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
