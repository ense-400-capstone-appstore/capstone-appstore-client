package me.matryoshkadoll.app.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout appsList;
    private SwipeRefreshLayout refreshLayout;
    //private TextView txv = (TextView) findViewById(R.id.textView);
    private TextView tvs;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tvs.setText(R.string.title_home);

                    return true;
                case R.id.navigation_dashboard:
                    tvs.setText(R.string.title_dashboard);
                    startActivity(new Intent(DrawerActivity.this, CategoriesActivity.class));
                    return true;
                case R.id.navigation_update:
                    tvs.setText(R.string.title_update);
                    startActivity(new Intent(DrawerActivity.this, UpdateActivity.class));


                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvs = (TextView) findViewById(R.id.textView);
        tvs.setText("android app");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Fetch android apps on refresh swipe gesture event
        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this::fetchAndroidApps);

        // Fetch android apps now
        fetchAndroidApps();
    }

    protected void fetchAndroidApps() {
        // View to populate android apps into
        LinearLayout appsList = findViewById(R.id.android_apps_list);

        // HTTP API connection setup
        AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);
        String Bearer = "Bearer ";
        //fetch token
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String An = prefs.getString("AccessToken", "No name defined");
        Call<AndroidApp> call = client.androidApps(Bearer+An);

        // Notify user that fetch is in progress
        appsList.removeAllViews();
        TextView androidAppView = new TextView(DrawerActivity.this);
        androidAppView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        androidAppView.setText("Fetching apps from server ...");
        appsList.addView(androidAppView);


        // HTTP callback
        call.enqueue(new Callback <AndroidApp>() {
            @Override
            public void onResponse(Call<AndroidApp> call, Response<AndroidApp> response) {
                // Get data from response
                AndroidApp androidApps = response.body();

                List<AndroidApp.Datum> datum = androidApps.getData();

                Log.i("Status", "Status code " + response.code());
                Log.i("AndroidAppsFetched", "Fetched " + response.body());

                // Remove all current items in the list
                appsList.removeAllViews();

                // Populate the list with data from the API
                if (datum != null) {
                    // specify an adapter (see also next example)
                    mAdapter = new Android_Apps_Adapter(datum);
                    mRecyclerView.setAdapter(mAdapter);

                } else {
                    TextView androidAppView = new TextView(DrawerActivity.this);
                    androidAppView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    androidAppView.setText("There are no apps.");
                    appsList.addView(androidAppView);
                }

                //refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<AndroidApp> call, Throwable t) {
                Toast.makeText(DrawerActivity.this, "Error fetching Android Apps!", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
        /**
         * Inflate the options menu
         * @param menu
         * @return true
         */




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_refresh) {
            fetchAndroidApps();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
