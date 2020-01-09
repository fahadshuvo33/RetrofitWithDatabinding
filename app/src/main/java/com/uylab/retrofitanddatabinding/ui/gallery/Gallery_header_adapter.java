package com.uylab.retrofitanddatabinding.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ItemGalleryHeaderBinding;
import com.uylab.retrofitanddatabinding.util.model.gallery.Datum;

import java.util.List;

public class Gallery_header_adapter extends RecyclerView.Adapter<Gallery_header_adapter.ViewHolder>{
    ItemGalleryHeaderBinding headerBinding;
    List<Datum> data ;
    ItemHeaderListener listener;

    public void setListener(ItemHeaderListener listener) {
        this.listener = listener;
    }

    public Gallery_header_adapter(List<Datum> data) {
        this.data = data;
    }

    public void setHeaderBinding(ItemGalleryHeaderBinding headerBinding) {
        this.headerBinding = headerBinding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
        headerBinding = DataBindingUtil.inflate (inflater, R.layout.item_gallery_header,parent,false );
        View view = headerBinding.getRoot ();
        ViewHolder holder = new ViewHolder ( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String string= data.get ( position ).getTitle ();
        headerBinding.textView.setText ( data.get ( position ).getTitle () );
        headerBinding.textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.OnItemClick ( data.get ( position ),position );
                }

            }
        } );

    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
        }
    }
    public interface ItemHeaderListener {
        public void OnItemClick(Datum datum, int position);
    }
}
