package com.uylab.retrofitanddatabinding.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ActivityGalleryBinding;
import com.uylab.retrofitanddatabinding.util.model.gallery.Datum;
import com.uylab.retrofitanddatabinding.util.model.gallery.Gallery;
import com.uylab.retrofitanddatabinding.util.model.gallery.Photo;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiInterface;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiProvider;
import com.uylab.retrofitanddatabinding.util.retrofit.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import static com.uylab.retrofitanddatabinding.AppConstraint.Code_Ok;

public class Gallery_Activity extends AppCompatActivity implements Gallery_header_adapter.ItemHeaderListener, RetrofitUtil.NetworkCallListener {

    private ActivityGalleryBinding galleryBinding;
    private RemoteApiInterface apiInterface;
    private RetrofitUtil mUtil;

    private Gallery_header_adapter header_adapter;
    private Gallery_photo_adapter photo_adapter;

    private List<Datum> dataList;
    private List<Photo> photoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        galleryBinding= DataBindingUtil.setContentView ( Gallery_Activity.this, R.layout.activity_gallery );

        apiInterface = RemoteApiProvider.getInstance ().getRemoteApi ();

        dataList= new ArrayList<> (  );
        header_adapter=new Gallery_header_adapter (dataList);
        galleryBinding.headerRecycle.setAdapter ( header_adapter );
        galleryBinding.headerRecycle.setLayoutManager ( new LinearLayoutManager ( Gallery_Activity.this,LinearLayoutManager.HORIZONTAL,false ) );

        photoList = new ArrayList<> (  );
        photo_adapter= new Gallery_photo_adapter ( photoList );
        galleryBinding.photoRecycle.setAdapter ( photo_adapter );
        galleryBinding.photoRecycle.setLayoutManager ( new GridLayoutManager(Gallery_Activity.this,2) );

        mUtil= new RetrofitUtil ();
        mUtil.setListener ( this );
        mUtil.NetworkCall ( apiInterface.gallery () );
        header_adapter.setListener ( new Gallery_header_adapter.ItemHeaderListener () {
            @Override
            public void OnItemClick(Datum datum, int position) {
                List<Photo> photos=datum.getPhotos ();
                photoList.clear ();
                photoList.addAll ( photos );
                photo_adapter.notifyDataSetChanged ();
                Toast.makeText ( Gallery_Activity.this,""+datum.getTitle ()+" Photo",Toast.LENGTH_SHORT ).show ();
            }
        } );

    }

    @Override
    public void OnSuccess(Object object) {
        Log.d("chk","OnSucces"+object.toString ());
        Gallery gallery = (Gallery) object;
        if(gallery.getCode ()== Code_Ok){
            dataList.clear ();
            dataList.addAll ( gallery.getData () );
            header_adapter.notifyDataSetChanged ();

          //  LoadGalleryImage ( gallery.getData ().get ( 0 ) );
        }
    }

    @Override
    public void OnFailed(String msg) {
        Log.d ( "chk", "OnFailed: "+msg );
    }

    public void LoadGalleryImage (Datum datum){
        if(datum!=null){
            photoList.clear ();
            photoList.addAll ( datum.getPhotos () );
            photo_adapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void OnItemClick(Datum datum, int position) {
        Toast.makeText ( Gallery_Activity.this,""+dataList.get ( position ),Toast.LENGTH_SHORT ).show ();
        LoadGalleryImage ( datum );
    }
}
