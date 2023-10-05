package com.example.jsonviaweb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonviaweb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);


    }
}
