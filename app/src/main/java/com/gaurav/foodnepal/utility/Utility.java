package com.gaurav.foodnepal.utility;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by sabin on 8/1/17.
 */

public class Utility {

    /**
     * Method to check internet is connected and working or not.
     *
     * @param context
     * @return
     */
    public static boolean isDeviceOnline(Context context) {

        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(5000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress != null && !inetAddress.equals("");
    }

    /**
     * Haversine function
     * Method to calculate distance between two latitude and longitude.
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double distance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double earthRadius = 6371;
        double latDiff = Math.PI * (lat_b - lat_a) / 180;
        double lngDiff = Math.PI * (lng_b - lng_a) / 180;
        double lat1 = Math.PI * (lat_a) / 180;
        double lat2 = Math.PI * (lat_b) / 180;
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;


        return (distance);
    }


    /**
     * Hides soft keyboard
     *
     * @param context
     * @param view
     */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}