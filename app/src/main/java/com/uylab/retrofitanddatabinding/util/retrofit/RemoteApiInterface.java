package com.uylab.retrofitanddatabinding.util.retrofit;

import com.uylab.retrofitanddatabinding.util.model.gallery.Gallery;
import com.uylab.retrofitanddatabinding.util.model.seminar_join.SeminarJoin;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.SeminarList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteApiInterface {
    /**
     * Request to server for registration
     * */
    @GET("gallery")
    Call<Gallery> gallery();

    @GET("seminars")
    Call<SeminarList> seminarList();

    @FormUrlEncoded
    @POST("seminar/enrolment")
    Call<SeminarJoin> seminarJoin(@Field ( "participator_name" ) String participator_name , @Field ( "seminar_id" ) String seminar_id , @Field ( "mobile_number" ) String mobile_number , @Field ( "email_address" ) String email_address );

}
