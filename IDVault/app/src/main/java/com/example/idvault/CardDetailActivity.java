package com.example.idvault;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;

public class CardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(img);

        String file = getIntent().getStringExtra("file");

        try {
            FileInputStream fis = openFileInput(file);
            img.setImageBitmap(BitmapFactory.decodeStream(fis));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}