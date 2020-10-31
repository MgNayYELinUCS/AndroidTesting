package com.htut.testingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomizeJerseyActivity extends AppCompatActivity {

    RelativeLayout txtName;
    CustomView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_jersey);

        txtName = findViewById(R.id.txtName);
        cardView = new CustomView(this);
        txtName.addView(cardView);

    }
}