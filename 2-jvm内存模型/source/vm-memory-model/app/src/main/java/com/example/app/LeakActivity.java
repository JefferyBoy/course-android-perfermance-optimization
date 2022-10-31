package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LeakActivity extends AppCompatActivity {

    private LeakActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        activity = this;
    }
}