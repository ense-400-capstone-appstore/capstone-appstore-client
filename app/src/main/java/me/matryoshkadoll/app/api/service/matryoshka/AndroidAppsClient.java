package me.matryoshkadoll.app.api.service.matryoshka;



import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.User;

import me.matryoshkadoll.app.api.model.UserInfo;
import me.matryoshkadoll.app.api.model.UserName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AndroidAppsClient {
    @GET("users/{id}")
    Call<UserName> GetUserName(@Header("Authorization")String token, @Path("id") int id );


    @GET("android_apps")
    Call<AndroidApp> androidApps(@Header("Authorization") String authorization);

    @GET("android_apps/{id}")
    Call<AndroidappInfo> androidappinfo(@Header("Authorization") String authorization, @Path("id") int id);

    @Headers({"Accept:application/json",
            "Content-Type:application/json"})
    @POST("login")
    Call<User> UserLogin( @Body UserInfo body);
}
