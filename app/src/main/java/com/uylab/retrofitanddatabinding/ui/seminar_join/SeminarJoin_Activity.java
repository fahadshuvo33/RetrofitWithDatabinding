package com.uylab.retrofitanddatabinding.ui.seminar_join;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.uylab.retrofitanddatabinding.AppConstraint;
import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ActivitySeminarJoinBinding;
import com.uylab.retrofitanddatabinding.util.model.seminar_join.SeminarJoin;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiInterface;
import com.uylab.retrofitanddatabinding.util.retrofit.RemoteApiProvider;
import com.uylab.retrofitanddatabinding.util.retrofit.RetrofitUtil;

import retrofit2.Call;

public class SeminarJoin_Activity extends AppCompatActivity implements RetrofitUtil.NetworkCallListener {
    ActivitySeminarJoinBinding joinBinding;
    RemoteApiInterface apiInterface;
    RetrofitUtil mUtil;
    String id,name,number,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        joinBinding= DataBindingUtil.setContentView ( SeminarJoin_Activity.this, R.layout.activity_seminar_join_ );

        apiInterface= RemoteApiProvider.getInstance ().getRemoteApi ();
        mUtil= new RetrofitUtil ();
        mUtil.setListener ( this );

        joinBinding.joinButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(view!=null){
                     id= String.valueOf ( getIntent().getIntExtra("mykey",0) );
                     name= joinBinding.nameText.getText ().toString ().trim ();
                     number = joinBinding.numberText.getText ().toString ().trim ();
                     email = joinBinding.emailText.getText ().toString ().trim ();

                    Call call =apiInterface.seminarJoin (name,id,number,email );
                    mUtil.NetworkCall ( call );


                }
            }
        } );
    }

    @Override
    public void OnSuccess(Object object) {
        SeminarJoin seminarJoin= (SeminarJoin) object;
        Log.d ( "chk", "OnSuccess: "+object.toString () );
        if(seminarJoin.getCode ()== AppConstraint.Code_Ok){
            Toast.makeText ( SeminarJoin_Activity.this,"Id "+id+" name "+name,Toast.LENGTH_SHORT ).show ();
        }
    }

    @Override
    public void OnFailed(String msg) {
        Log.d ( "tag", "OnFailed: "+msg );
    }
}
