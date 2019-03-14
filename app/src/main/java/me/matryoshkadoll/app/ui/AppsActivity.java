package me.matryoshkadoll.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.adapter.Android_Apps_Adapter;
import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.login.LoginActivity;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class AppsActivity extends AppCompatActivity {
private Intent it;
private int catid;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        templatemethod();
    }
    private  void templatemethod(){
        getcatid();
        getprefskey();
        fetchAndroidApps();
    }

    private void getcatid(){
        it=getIntent();
        catid = it.getIntExtra("cat_id",999);
    }
    private  void getprefskey(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String An = prefs.getString("AccessToken", "No name defined");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    protected void fetchAndroidApps() {
        // View to populate android apps into

        // HTTP API connection setup
        AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);
        //fetch token
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String An = prefs.getString("AccessToken", "No name defined");
        Call<AndroidApp> call = client.getAppsbyCategories(An,catid);


        // HTTP callback
        call.enqueue(new Callback<AndroidApp>() {
            @Override
            public void onResponse(Call<AndroidApp> call, Response<AndroidApp> response) {
                // Get data from response
                AndroidApp androidApps = response.body();
                if(androidApps != null){
                    List<AndroidApp.Datum> datum = androidApps.getData();


                    Log.i("Status", "Status code " + response.code());
                    Log.i("AndroidAppsFetched", "Fetched " + response.body());

                    // Remove all current items in the list

                    // Populate the list with data from the API
                    if (datum != null) {
                        // specify an adapter (see also next example)
                        mAdapter = new Android_Apps_Adapter(datum, getApplicationContext(),An );
                        mRecyclerView.setAdapter(mAdapter);

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<AndroidApp> call, Throwable t) {
                Toast.makeText(AppsActivity.this, "Error fetching Android Apps!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
