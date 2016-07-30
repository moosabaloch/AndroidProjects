package com.retrofit.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit client = ApiClient.getClient();
        Pop pop= client.create(Pop.class);
        HashMap<String ,String> map= new HashMap<>();
        map.put("tourist_guide_key_mob","yest_true");
        map.put("city_id","64");
        map.put("id","2");
       // robots("yest_true","64","2")


        /*

        {
            "server_key":"your_server_key",
            "user_id":"4567m4mn5646",
            "data_json":
                {
                    "your_data":"dummy_data",
                    "img_url":"img.png",
                    "description":"description"
                 },
            "notify_title":"New Message",
            "notify_body":"Friend Commented on post"
         }

        */
        HashMap<String ,Object> map2= new HashMap<>();
        map2.put("server_key","2your_server_key");
        map2.put("user_id","meri user id");
        HashMap<String,String> dat=new HashMap<>();
        dat.put("your_data","Kuch bhi data");
        dat.put("img_url","any thing");
        map2.put("data_json",dat);
        map2.put("notify_title","My msg title");
        map2.put("notify_body","Notification body");





        pop.fcmRequest(map2).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("onResponse();",""+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onError();",""+call.toString());
            }
        });
    }

    public interface Pop {
        @FormUrlEncoded
        @POST("Place/getplacesbycatid")
        Call<ResponseBody> robots(@Field("tourist_guide_key_mob")String key,
                                  @Field("city_id")String city_id,
                                  @Field("id")String id
                                  );

        @FormUrlEncoded
        @POST("Place/getplacesbycatid")
        Call<ResponseBody> mapRequest(@FieldMap HashMap<String,String> map);

        @FormUrlEncoded
        @POST("fcm/jsonpost")
        Call<ResponseBody> fcmRequest(@FieldMap HashMap<String,Object> map);

    }
}