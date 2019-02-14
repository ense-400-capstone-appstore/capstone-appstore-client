package me.matryoshkadoll.app.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
