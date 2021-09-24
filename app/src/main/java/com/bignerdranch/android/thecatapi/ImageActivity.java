package com.bignerdranch.android.thecatapi;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ImageActivity extends AppCompatActivity {
    final static String KEY = "image_ID";
    Boolean wasSavedInGallery = false;
    Boolean youCheckedSaving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_image);

        ImageView imageView = findViewById(R.id.my_image_view);
        AppCompatButton saveButton = findViewById(R.id.save_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int imageId = extras.getInt(KEY);
            imageView.setImageResource(imageId);
        }

        saveButton.setOnClickListener(v -> {
            if (!wasSavedInGallery) {
                Drawable drawable = imageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        bitmap, "Cat", "Image of cat");

                Toast.makeText(getApplicationContext(),
                        R.string.saved, Toast.LENGTH_SHORT).show();

                wasSavedInGallery = true;
            } else if (!youCheckedSaving) {
                Toast.makeText(getApplicationContext(),
                        R.string.was_saved, Toast.LENGTH_SHORT).show();
                youCheckedSaving = true;
            }
        });
    }
}