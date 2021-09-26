package com.bignerdranch.android.thecatapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class ImageActivity extends AppCompatActivity {
    final static String KEY = "image_ID";

    private final String WSG_KEY = "WSG_KEY";
    private final String YCS_KEY = "YCS_KEY";

    private Boolean wasSavedInGallery = false;
    private Boolean youCheckedSaving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_image);

        ImageView imageView = findViewById(R.id.my_image_view);
        ImageView saveImageView = findViewById(R.id.saveImageView);
        ImageView shareImageView = findViewById(R.id.shareImageView);

        if (savedInstanceState != null){
            wasSavedInGallery = savedInstanceState.getBoolean(WSG_KEY);
            youCheckedSaving = savedInstanceState.getBoolean(YCS_KEY);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int imageId = extras.getInt(KEY);
            imageView.setImageResource(imageId);
        }

        saveImageView.setOnClickListener(v -> {
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

        shareImageView.setOnClickListener(v -> {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Uri uri = convertBitmapToUri(bitmap);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/png");

            startActivity(Intent.createChooser(intent, "Share Via"));
        });
    }

    private Uri convertBitmapToUri(Bitmap bitmap) {
        File imageFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imageFolder.mkdirs();
            File file = new File(imageFolder, "shared_image.jpeg");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            String authority = "com.anni.shareimage.fileprovider";
            uri = FileProvider.getUriForFile(this, authority, file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(WSG_KEY, wasSavedInGallery);
        outState.putBoolean(YCS_KEY, youCheckedSaving);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}