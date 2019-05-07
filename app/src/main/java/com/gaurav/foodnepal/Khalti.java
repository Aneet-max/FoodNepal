package com.gaurav.foodnepal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import khalti.checkOut.api.Config;
import khalti.checkOut.api.OnCheckOutListener;
import khalti.checkOut.helper.KhaltiCheckOut;

public class Khalti extends AppCompatActivity {

    Button khaltiButton;
    long amount = 1000;
    String khaltiAPI = "test_public_key_c3568353ee8a4916b45756741c754ad8";
    EditText contributeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khalti);


        khaltiButton = findViewById(R.id.khalti_button);
        contributeET = findViewById(R.id.contributeET);


        khaltiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                amount = Long.parseLong(contributeET.getText().toString());
                if (amount >= 10) {
                    amount *= 100;

                    Config config = new Config(khaltiAPI, "Product ID", "Product Name", "Product Url", amount, new OnCheckOutListener() {

                        @Override
                        public void onSuccess(HashMap<String, Object> data) {
                            Log.i("Payment confirmed", data + " ");
                        }

                        @Override
                        public void onError(String action, String message) {
                            Log.i(action, message);
                        }
                    });

                    KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(Khalti.this, config);

                    khaltiCheckOut.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Minimum contribution is Rs 10.", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
