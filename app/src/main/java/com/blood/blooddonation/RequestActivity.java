package com.blood.blooddonation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Toolbar toolbar = findViewById(R.id.toolbar_req);
//        toolbar.setTitle("Red Blood");
        toolbar.setBackgroundColor(getResources().getColor(R.color.main));

        setSupportActionBar(toolbar);


    }
}