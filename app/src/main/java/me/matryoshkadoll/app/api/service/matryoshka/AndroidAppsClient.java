package me.matryoshkadoll.app.api.service.matryoshka;

import java.util.List;

import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AndroidAppsClient {
    @GET("android_apps")
    Call<List<AndroidApp>> androidApps();

    @POST("login")
    Call<User> getUserDetails(@Header("Authorization") String credentials);
}
