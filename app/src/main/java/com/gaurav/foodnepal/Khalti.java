package com.gaurav.foodnepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import khalti.checkOut.api.Config;
import khalti.checkOut.api.OnCheckOutListener;
import khalti.widget.KhaltiButton;

public class Khalti extends AppCompatActivity {

    KhaltiButton khaltiButton;
    long amount = 1000;
    String khaltiAPI = "test_public_key_c3568353ee8a4916b45756741c754ad8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khalti);



        khaltiButton =  findViewById(R.id.khalti_button);

        Config config = new Config(khaltiAPI, "Product ID", "Product Name", "Product Url", amount, new OnCheckOutListener() {

            @Override
            public void onSuccess(HashMap<String, Object> data) {
                Log.i("Payment confirmed", data+"");
                Toast.makeText(Khalti.this, "Thank you", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String action, String message) {
                Log.i(action, message);
            }
        });

        khaltiButton.setCheckOutConfig(config);
    }
}
