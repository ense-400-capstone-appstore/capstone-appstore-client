package me.matryoshkadoll.app.api.service.matryoshka;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.model.User;

import me.matryoshkadoll.app.api.model.UserInfo;
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
    Call<AndroidApp> androidApps(@Header("Authorization") String authorization);


    @Headers({"Accept:application/json",
            "Content-Type:application/json"})
    @POST("login")
    Call<User> UserLogin( @Body UserInfo body);
}
