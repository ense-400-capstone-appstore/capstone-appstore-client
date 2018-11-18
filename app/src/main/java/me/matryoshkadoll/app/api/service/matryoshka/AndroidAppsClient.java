package me.matryoshkadoll.app.api.service.matryoshka;

import java.util.List;

import me.matryoshkadoll.app.api.model.AndroidApp;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AndroidAppsClient {
    @GET("android_apps")
    Call<List<AndroidApp>> androidApps();
}
