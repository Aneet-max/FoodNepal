package com.gaurav.foodnepal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gaurav.foodnepal.adapter.PlaceListAdapter;
import com.google.android.gms.location.places.GeoDataClient;
import com.gaurav.foodnepal.model.Places;
import com.gaurav.foodnepal.touchlistener.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaceList extends AppCompatActivity {

    private static final String TAG = PlaceList.class.getSimpleName();
    private String latitude;
    private String longitude;
    private Intent intent;
    private String type = "restaurant";
    private String title = "Restaurant";
    private ProgressDialog dialog;
    private List<Places> placeArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlaceListAdapter placeListAdapter;
    private GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        dialog = new ProgressDialog(PlaceList.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(this, null);

        intent = getIntent();
        latitude = intent.getStringExtra("lat");
        longitude = intent.getStringExtra("lng");

        // get places details
        getPlaces();


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar3));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        recyclerView = findViewById(R.id.placeListRV);

        placeListAdapter = new PlaceListAdapter(getBaseContext(), placeArrayList, latitude, longitude, mGeoDataClient);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(placeListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Places places = placeArrayList.get(position);
                intent = new Intent(PlaceList.this, PlaceDetail.class);
                intent.putExtra("lat", places.getLat());
                intent.putExtra("lng", places.getLng());
                intent.putExtra("selected", places.getName());
                intent.putExtra("placeId", places.getPlaceId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    /**
     * Method to search certain type of place using Places API
     * Uses latitude and longitude from global variable
     */
    private void getPlaces() {
        String api = getResources().getString(R.string.placeAPI);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                latitude + "," + longitude + "&rankby=distance&type=" + type + "&key=" + api;

        Log.i("PlaceList", "url: " + url);

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Places place;
                        Log.i("PlaceList", "onResponse: response: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject geometry = jsonObject.getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                double lat = location.getDouble("lat");
                                double lng = location.getDouble("lng");
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String placeId = jsonObject.getString("place_id");
                                double rating = 0.0;
                                if (jsonObject.has("rating")) {
                                    rating = jsonObject.getDouble("rating");
                                }
                                String vicinity = jsonObject.getString("vicinity");

                                String photoReference = "null";
                                if (jsonObject.has("photos")) {
                                    JSONArray photos = jsonObject.getJSONArray("photos");
                                    JSONObject photosObject = photos.getJSONObject(0);
                                    photoReference = photosObject.getString("photo_reference");
                                }

                                place = new Places(lat, lng, id, name, placeId, rating, photoReference, vicinity);
                                placeArrayList.add(place);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        if (placeArrayList.size() <= 0) {
                            Toast.makeText(getApplicationContext(), "Deu to Google policy, we are unable to fetch data. Try in few minutes", Toast.LENGTH_LONG).show();
                        } else {
                            placeListAdapter.notifyDataSetChanged();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: error: " + error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
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
