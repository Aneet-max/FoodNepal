package com.gaurav.foodnepal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurav.foodnepal.R;
import com.gaurav.foodnepal.model.Trip;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> tripList;
    private Context context;
    private GeoDataClient mGeoDataClient;
    private TripClickListener mListener;


    public TripAdapter(Context context, List<Trip> tripList, GeoDataClient mGeoDataClient) {
        this.tripList = tripList;
        this.context = context;
        this.mGeoDataClient = mGeoDataClient;
    }


    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_card, parent, false);

        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripViewHolder holder, final int position) {

        final Trip trip = tripList.get(position);

        /**
         * Fetching photos of places using placeId and setting photos on imageview of recyclerview
         */
        if (!trip.getPlaceId().equals("null")) {
            final String placeId = trip.getPlaceId();
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
                            holder.imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            });
        }

        holder.name.setText(trip.getTripName());
        holder.place.setText(trip.getPlace());
        holder.date.setText(trip.getDate());

    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: tripListSize: " + tripList.size());
        return tripList.size();
    }

    public void SetOnFavoriteClickListener(final TripClickListener mListener) {
        this.mListener = mListener;
    }

    public interface TripClickListener {
        void onDelete(View v, int position);

        void onImageClick(View v, int position);
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView name, place, date;
        public ImageView imageView, btnDelete;


        public TripViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tripName);
            place = itemView.findViewById(R.id.tripPlace);
            date = itemView.findViewById(R.id.tripDate);
            imageView = itemView.findViewById(R.id.tripIV);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onImageClick(v, getAdapterPosition());
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onDelete(v, getAdapterPosition());
                    }
                }
            });

        }
    }
}
