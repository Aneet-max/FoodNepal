package com.gaurav.foodnepal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;

public class PlaceDetail extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent;
    private String placeName, placeId;
    private ImageView coverImageView;
    private GeoDataClient mGeoDataClient;
    private TextView titleTV;
    private FloatingActionButton directionFAB;
    private double latitude, longitude;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        intent = getIntent();

        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(this, null);


        placeName = intent.getStringExtra("selected");
        placeId = intent.getStringExtra("placeId");

        context = getApplicationContext();

        latitude = intent.getDoubleExtra("lat", 0.0);
        longitude = intent.getDoubleExtra("lng", 0.0);

        titleTV = findViewById(R.id.placeTitle);
        directionFAB = findViewById(R.id.fabDirection);

        titleTV.setText(placeName);

        coverImageView = findViewById(R.id.coverImageView);

        /**
         * Fetching photos of places using placeId and setting photos on imageview of recyclerview
         */
        if (!placeId.equals(null)) {
            final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
            photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                    try {
                        // Get the list of photos.
                        PlacePhotoMetadataResponse photos = task.getResult();
                        // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        // Get the first photo in the list.
                        PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                        // Get the attribution text.
                        CharSequence attribution = photoMetadata.getAttributions();
                        // Get a full-size bitmap for the photo.
                        Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                                PlacePhotoResponse photo = task.getResult();
                                Bitmap bitmap = photo.getBitmap();
                                coverImageView.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (RuntimeExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar4));

        getSupportActionBar().

                setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(placeName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        directionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGoogleMapsInstalled()) {
                    String uri = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Please enable your Google Maps!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetail.this);
                    builder.setMessage("Please install Google Maps");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Install", getGoogleMapsListener());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(latitude, longitude);
        if (!intent.getStringExtra("selected").equals("null")) {
            mMap.addMarker(new MarkerOptions().position(location).title(placeName)).showInfoWindow();
        }
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);


    }

    /**
     * Method to check whethere google maps is installed or not
     *
     * @return
     */
    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Method to get google maps from playstore if user hasn't installed google maps
     *
     * @return
     */
    public DialogInterface.OnClickListener getGoogleMapsListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                startActivity(intent);

                //Finish the activity so they can't circumvent the check
                finish();
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
