package com.bignerdranch.android.thecatapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    final static String KEY = "image_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int imageId = extras.getInt(KEY);
            imageView.setImageResource(imageId);
        }
    }
}