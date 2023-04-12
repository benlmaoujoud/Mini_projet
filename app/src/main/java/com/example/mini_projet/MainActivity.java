package com.example.mini_projet;

import android.annotation.SuppressLint;
import android.net.TrafficStats;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myTextView = findViewById(R.id.myTextView);
        Mobile mobile =new Mobile();
        mobile.startSpeedTest();

        String res =mobile.endSpeedTest();

        myTextView.setText(res);




    }
}