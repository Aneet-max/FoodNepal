package com.aneet.foodnepal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aneet.foodnepal.R;
import com.aneet.foodnepal.model.Places;
import com.aneet.foodnepal.utility.Utility;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceListViewHolder> {

    Double distance = 0.0;
    private List<Places> placeList;
    private Context context;
    private String key = "AIzaSyBXK6vd1O5hLkN5ZQtZCZbj8t6O9-CBriM";
    private double lat;
    private double lng;
    private String baseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private GeoDataClient mGeoDataClient;


    public PlaceListAdapter(Context context, List<Places> placeList, String lat, String lng, GeoDataClient mGeoDataClient) {
        this.context = context;
        this.placeList = placeList;
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
        this.mGeoDataClient = mGeoDataClient;
    }

    @NonNull
    @Override
    public PlaceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_card, parent, false);

        return new PlaceListAdapter.PlaceListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceListAdapter.PlaceListViewHolder holder, int position) {

        Places places = placeList.get(position);

        /**
         * Fetching photos of places using placeId and setting photos on imageview of recyclerview
         */
        if (!places.getPhotoRefrence().equals("null")) {
//            String photorefrence = places.getPhotoRefrence();
//            String url = baseUrl + photorefrence + "&key=" + key;
//
//            Glide.with(context).load(url).into(holder.placeListIV);


            final String placeId = places.getPlaceId();
            final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
            photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
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
                            holder.placeListIV.setImageBitmap(bitmap);
                        }
                    });
                }
            });
        }

        /**
         * Calculating distance in meter using two coordinates and setting it in distance textview
         */
        distance = Utility.distance(lat, lng, places.getLat(), places.getLng());
        holder.placeDistance.setText(new DecimalFormat("##.##").format(distance) + " km");

        /**
         * Setting place name, place rating
         */
        holder.placeListNameTV.setText(places.getName());
        holder.placeRatingTV.setText(Double.toString(places.getRating()));
        holder.placeRating.setRating((float) places.getRating());

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceListViewHolder extends RecyclerView.ViewHolder {

        public ImageView placeListIV;
        public TextView placeListNameTV, placeDistance, placeRatingTV;
        public RatingBar placeRating;

        public PlaceListViewHolder(View itemView) {
            super(itemView);
            placeListIV = itemView.findViewById(R.id.placeListIV);
            placeListNameTV = itemView.findViewById(R.id.placeListNameTV);
            placeDistance = itemView.findViewById(R.id.distance);
            placeRatingTV = itemView.findViewById(R.id.ratingText);
            placeRating = itemView.findViewById(R.id.rating);
        }


    }
}
