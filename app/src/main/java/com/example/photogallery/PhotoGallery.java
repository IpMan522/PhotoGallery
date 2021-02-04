package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.photogallery.api.FlickrAPI;
import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.model.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class PhotoGallery extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        Retrofit r = ServiceAPI.getRetrofit();
        r.create(FlickrAPI.class).getRecent().enqueue(new Callback<Response>()
        {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                PhotoAdapter pa = new PhotoAdapter(this, response.body().getPhotos().getPhoto());
                rv.setAdapter(pa);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
            }
        });
    }
}