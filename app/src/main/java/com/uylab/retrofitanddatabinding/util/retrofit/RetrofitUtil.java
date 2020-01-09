package com.uylab.retrofitanddatabinding.util.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitUtil {
    NetworkCallListener listener;

    public void setListener(NetworkCallListener listener) {
        this.listener = listener;
    }

    public  void NetworkCall(Call call){
        call.enqueue ( new Callback () {
            @Override
            public void onResponse(Call call, Response response) {
                if(listener!=null) {
                    if (response.isSuccessful ()) {
                        Object object = response.body ();
                        listener.OnSuccess ( object );
                    }

                else {

                        listener.OnFailed ( "Error : " + response.code () );
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.OnFailed ( "Error : "+ t.getMessage () );
            }
        } );
    }
    public interface NetworkCallListener {
        void OnSuccess (Object object);
        void OnFailed (String msg);
    }
}
