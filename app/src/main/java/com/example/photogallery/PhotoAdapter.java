package com.example.photogallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.db.PhotosDB;
import com.example.photogallery.db.PhotosDao;
import com.example.photogallery.model.Photo;
import com.example.photogallery.model.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class PhotoAdapter extends RecyclerView.Adapter <PhotoAdapter.ViewHolder> {

    private final List<Photo> values;
    private boolean from_bd;
    private PhotosDao dao;

    PhotoAdapter(List<Photo> items, PhotosDao dao, boolean from_bd) {
        values = items;
        this.dao = dao;
        this.from_bd = from_bd;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String s;
        s = "https://farm" + Integer.toString(values.get(position).getFarm()) + ".staticflickr.com/" +
                values.get(position).getServer() + "/" + values.get(position).getId() +
                "_" + values.get(position).getSecret() + "_q.jpg";
        Picasso.get().load(s).into(holder.idView);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView idView;

        ViewHolder(View view) {
            super(view);
            idView = view.findViewById(R.id.iv);
            if(from_bd)
            {
                idView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.deletePhoto(values.get(ViewHolder.this.getAdapterPosition()));
                    }
                });
            }
            else
            {
                idView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.insertPhoto(values.get(ViewHolder.this.getAdapterPosition()));
                    }
                });
            }
        }
    }
}
