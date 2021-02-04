package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.photogallery.api.FlickrAPI;
import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.model.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class PhotoGallery extends AppCompatActivity {

    RecyclerView rv;
    Retrofit r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        r = ServiceAPI.getRetrofit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                r.create(FlickrAPI.class).getSearchPhotos(query).enqueue(
                        new Callback<Response>()
                        {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                PhotoAdapter pa = new PhotoAdapter(this, response.body().getPhotos().getPhoto());
                                rv.setAdapter(pa);
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                            }
                        }
                );
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}