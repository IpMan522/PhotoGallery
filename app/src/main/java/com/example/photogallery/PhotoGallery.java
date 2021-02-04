package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class PhotoGallery extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        rv = findViewById(R.id.rv);
        rv.setAdapter(new PhotoAdapter());
        rv.setLayoutManager(new GridLayoutManager(this, 3));
    }
}