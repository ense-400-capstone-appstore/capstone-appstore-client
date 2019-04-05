package me.matryoshkadoll.app.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.adapter.Categories_Adapter;
import me.matryoshkadoll.app.api.model.Categories;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
private Categories categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_categories);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //getCategories();
        // specify an adapter (see also next example)
        getCategories();



    }

    private void getCategories() {

        // HTTP API connection setup
        AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);
        //fetch token
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String An = prefs.getString("AccessToken", "No name defined");
        Call<Categories> call = client.getCategories(An);

        // HTTP callback
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                // Get data from response
                 categories = response.body();
                Log.i("Status", "Status code " + response.code());
                if(categories==null){}else{
                mAdapter = new Categories_Adapter(categories);
                mRecyclerView.setAdapter(mAdapter);}
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "Error fetching Android Apps!", Toast.LENGTH_SHORT).show();
            }
        });

        //return categories;
    }
}
