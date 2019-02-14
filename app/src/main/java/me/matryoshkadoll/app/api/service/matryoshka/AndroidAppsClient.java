package me.matryoshkadoll.app.api.service.matryoshka;



import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.User;

import me.matryoshkadoll.app.api.model.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AndroidAppsClient {
    @GET("android_apps")
    Call<AndroidApp> androidApps(@Header("Authorization") String authorization);


    @Headers({"Accept:application/json",
            "Content-Type:application/json"})
    @POST("login")
    Call<User> UserLogin( @Body UserInfo body);
}
