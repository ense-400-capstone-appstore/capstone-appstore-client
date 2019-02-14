package me.matryoshkadoll.app.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.widget.ImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class OkHTTPClientInstance  {

    public OkHTTPClientInstance(){

    }

    //@Override
    public OkHttpClient getAvatar (String st) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", st)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();


       return client;
    }


}
