package com.uylab.retrofitanddatabinding.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ItemGalleryPhotoBinding;
import com.uylab.retrofitanddatabinding.util.model.gallery.Photo;

import java.util.List;

public class Gallery_photo_adapter extends RecyclerView.Adapter<Gallery_photo_adapter.ViewHolder> {
    private List<Photo> photoList;
    ItemGalleryPhotoBinding photoBinding;

    public Gallery_photo_adapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
        photoBinding = DataBindingUtil.inflate ( inflater, R.layout.item_gallery_photo,parent,false );
        View view = photoBinding.getRoot ();
        ViewHolder viewHolder = new ViewHolder ( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with ( photoBinding.getRoot ().getContext () ).load ( photoList.get ( position ).getThumbnailUrl () ).into ( photoBinding.imageView );
    }

    @Override
    public int getItemCount() {
        return photoList.size ();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
        }
    }
}
