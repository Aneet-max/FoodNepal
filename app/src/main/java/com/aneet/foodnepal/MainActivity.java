package com.aneet.foodnepal;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = "MainActivity";
    double longitude = 0;
    double latitude = 0;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    //    private Location location;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
                fetchAndShowLocation(mMap);
            }
        });

        // setting user email on navbar
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences pref = getSharedPreferences("SCTPref", MODE_PRIVATE);
        String userEmail = pref.getString("email", "");

        View header = navigationView.getHeaderView(0);
        TextView emailTV = header.findViewById(R.id.navUserEmail);
        Menu nav_Menu = navigationView.getMenu();

        emailTV.setText(userEmail);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contribute) {
            Intent intent = new Intent(MainActivity.this, Khalti.class);
            startActivity(intent);
        }

        if (id == R.id.nav_restaurant) {
            Intent intent = new Intent(MainActivity.this, PlaceList.class);
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("lng", String.valueOf(longitude));
            intent.putExtra("type", "restaurant");
            startActivity(intent);
        }
        if (id == R.id.nav_bakery) {
            Intent intent = new Intent(MainActivity.this, PlaceList.class);
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("lng", String.valueOf(longitude));
            intent.putExtra("type", "bakery");
            startActivity(intent);
        }
        if (id == R.id.nav_cafe) {
            Intent intent = new Intent(MainActivity.this, PlaceList.class);
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("lng", String.valueOf(longitude));
            intent.putExtra("type", "cafe");
            startActivity(intent);
        }
        if (id == R.id.nav_bar) {
            Intent intent = new Intent(MainActivity.this, PlaceList.class);
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("lng", String.valueOf(longitude));
            intent.putExtra("type", "bar");
            startActivity(intent);
        }

        if (id == R.id.nav_about_us) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        }

        if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);
        }

        if (id == R.id.nav_logout) {

            // removing user info from sharedpref
            SharedPreferences.Editor editor = getSharedPreferences("SCTPref", MODE_PRIVATE).edit();
            editor.putBoolean("userRegistered", false);
            editor.putString("email", "");
            editor.apply();

            // logout user from firebase
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(MainActivity.this, Start.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        fetchAndShowLocation(googleMap);
    }

    private void fetchAndShowLocation(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            checkLocationPermission();
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            latLng = new LatLng(latitude, longitude);
                            Log.i("TAG", "onMapReady: latlng: " + latLng.toString());
                            // zoom level recommended
                            // 1: World
                            // 5: Landmass/continent
                            // 10: City
                            // 15: Streets
                            // 20: Buildings
                            float zoomLevel = 15.0f; //This goes up to 21


                            // Creating a marker
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Setting the position for the marker
                            markerOptions.position(latLng);

                            // Setting the title for the marker.
                            // This will be displayed on taping the marker
                            markerOptions.title("You are currently here!");

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
                            mMap.addMarker(markerOptions);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enable location.", Toast.LENGTH_LONG).show();
                            openLocationSettings();
                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        checkLocationPermission();
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    Toast.makeText(getApplicationContext(), "Please grant location permission", Toast.LENGTH_LONG).show();
//                    openAppSettings();
                }
                return;
            }

        }
    }

    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openLocationSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (checkLocationPermission()) {
            fetchAndShowLocation(mMap);
        }
    }

}
