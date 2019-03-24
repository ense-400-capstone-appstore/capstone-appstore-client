package me.matryoshkadoll.app.api.service.matryoshka;



import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.Categories;
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

    @GET ("categories")
    Call<Categories>getCategories(@Header("Authorization")String token);

    @GET ("categories/{id}/android_apps")
    Call<AndroidApp>getAppsbyCategories(@Header("Authorization")String token, @Path("id") int id);

    @GET("android_apps")
    Call<AndroidApp> androidApps(@Header("Authorization") String authorization);

    @GET("android_apps/{id}")
    Call<AndroidappInfo> androidappinfo(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("android_apps/package_name/{package_name}")
    Call<AndroidappInfo> checkappexist(@Header("Authorization") String authorization, @Path("package_name") String pkname);

    @Headers({"Accept:application/json",
            "Content-Type:application/json"})
    @POST("login")
    Call<User> UserLogin( @Body UserInfo body);
}
