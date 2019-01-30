package me.matryoshkadoll.app.api.service.matryoshka;

import java.util.List;

import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AndroidAppsClient {
    @GET("android_apps")
    Call<List<AndroidApp>> androidApps();


    @Headers("Content-Type:application/json ")
    @POST("login")
    Call<User> UserLogin( @Body String body );
}
