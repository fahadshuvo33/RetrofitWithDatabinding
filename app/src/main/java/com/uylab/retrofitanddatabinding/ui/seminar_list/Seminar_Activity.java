package com.uylab.retrofitanddatabinding.ui.seminar_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.uylab.retrofitanddatabinding.AppConstraint;
import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ActivitySeminarBinding;
import com.uylab.retrofitanddatabinding.ui.gallery.Gallery_Activity;
import com.uylab.retrofitanddatabinding.ui.seminar_join.SeminarJoin_Activity;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.Datum;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.Seminar;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.SeminarList;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiInterface;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiProvider;
import com.uylab.retrofitanddatabinding.util.retrofit.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Seminar_Activity extends AppCompatActivity implements RetrofitUtil.NetworkCallListener, Seminar_title_Adapter.Seminar_Title_Listener {

    private ActivitySeminarBinding seminarBinding;
    private List<Seminar> seminars;
    private List<Datum> datumList;

    private Seminar_Adapter seminar_adapter;
    private Seminar_title_Adapter title_adapter;
    private RetrofitUtil util;
    private RemoteApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        seminarBinding= DataBindingUtil.setContentView (Seminar_Activity.this, R.layout.activity_seminar_ );

        apiInterface= RemoteApiProvider.getInstance ().getRemoteApi ();

        datumList= new ArrayList<> (  );
        title_adapter= new Seminar_title_Adapter (datumList);
        seminarBinding.recycleTitle.setAdapter ( title_adapter );
        seminarBinding.recycleTitle.setLayoutManager ( new LinearLayoutManager ( Seminar_Activity.this, LinearLayoutManager.HORIZONTAL,false ) );

        seminars=new ArrayList<> (  );
        seminar_adapter= new Seminar_Adapter ( seminars );
        seminarBinding.recycleItem.setAdapter ( seminar_adapter );
        seminarBinding.recycleItem.setLayoutManager ( new LinearLayoutManager ( Seminar_Activity.this ) );

        util= new RetrofitUtil ();
        util.setListener ( this );
        util.NetworkCall ( apiInterface.seminarList () );

        title_adapter.setTitle_listener ( new Seminar_title_Adapter.Seminar_Title_Listener () {
            @Override
            public void OnItemClick(Datum datum, int position) {
                List<Seminar> seminarList =datum.getSeminars ();
                seminars.clear ();
                //seminars=new ArrayList<> (  );
                seminars.addAll ( seminarList );
                seminar_adapter.notifyDataSetChanged ();
                Toast.makeText (Seminar_Activity.this,datum.getName ()+" Details",Toast.LENGTH_SHORT).show ();
            }
        } );

        seminar_adapter.setAdapterClickListener ( new Seminar_Adapter.Seminar_AdapterClickListener () {
            @Override
            public void itemClickListener(Seminar datum, int position) {
                Intent intent=new Intent(Seminar_Activity.this, SeminarJoin_Activity.class);
                intent.putExtra("mykey",datum.getId ());
                startActivity(intent);
                finish ();
            }
        } );


    }

    @Override
    public void OnSuccess(Object object) {
        Log.d ( "chk", "OnSuccess: "+object.toString () );
        SeminarList seminarList = (SeminarList) object;

        if(seminarList.getCode ()== AppConstraint.Code_Ok){
            datumList.clear ();
            datumList.addAll ( seminarList.getData () );
            title_adapter.notifyDataSetChanged ();

        }
    }

    @Override
    public void OnFailed(String msg) {
        Log.d ( "chk", "OnFailed: "+msg );
    }

    public void LoadItems(Datum datum){
        if(datum != null){
            seminars.clear ();
            seminars.addAll ( datum.getSeminars () );
            seminar_adapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void OnItemClick(Datum datum, int position) {
        Toast.makeText ( Seminar_Activity.this,""+datumList.get ( position ),Toast.LENGTH_SHORT ).show ();
        LoadItems ( datum );
    }
}
