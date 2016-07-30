package com.retrofit.retrofitsample;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Moosa moosa.bh@gmail.com on 7/31/2016 31 July.
 * Everything is possible in programming.
 */
public class ApiClient {

    public static final String BASE_URL = "http://alisonstech.com/tourist/index.php/";//Place/getplacesbycatid";
    public static final String BASE_URL2 = "https://fcmserver.herokuapp.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)

                   // .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
