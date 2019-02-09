package me.matryoshkadoll.app.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.login.LoginActivity;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout appsList;
    private SwipeRefreshLayout refreshLayout;
    //private TextView txv = (TextView) findViewById(R.id.textView);
    private TextView tvs;

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
                case R.id.navigation_notifications:
                    tvs.setText(R.string.title_notifications);

                    final PackageManager packageManager = getPackageManager();
                    List<ApplicationInfo> installedApplications =
                            packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

                    for (ApplicationInfo appInfo : installedApplications)
                    {
                        Log.i("OUTPUT", "Package name : " + appInfo.packageName);
                        Log.i("OUTPUT", "Name: " + appInfo.loadLabel(packageManager));
                    }
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

        Call<List<AndroidApp>> call = client.androidApps();

        // Notify user that fetch is in progress
        appsList.removeAllViews();
        TextView androidAppView = new TextView(DrawerActivity.this);
        androidAppView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        androidAppView.setText("Fetching apps from server ...");
        appsList.addView(androidAppView);


        // HTTP callback
        call.enqueue(new Callback<List<AndroidApp>>() {
            @Override
            public void onResponse(Call<List<AndroidApp>> call, Response<List<AndroidApp>> response) {
                // Get data from response
                List<AndroidApp> androidApps = response.body();
                Log.i("Status", "Status code " + response.code());
                Log.i("AndroidAppsFetched", "Fetched " + response.body());

                // Remove all current items in the list
                appsList.removeAllViews();

                // Populate the list with data from the API
                if (androidApps != null) {
                    for(AndroidApp app : androidApps) {
                        CardView androidAppView = new CardView(DrawerActivity.this);
                        androidAppView.setMaxCardElevation(4);
                        androidAppView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        // Vertical layout inside card
                        LinearLayout cardLayout = new LinearLayout(DrawerActivity.this);
                        cardLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        cardLayoutParams.setMargins(20, 20, 20, 20);
                        cardLayout.setLayoutParams(cardLayoutParams);

                        // Title
                        TextView androidAppName = new TextView(DrawerActivity.this);
                        androidAppName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        androidAppName.setText(app.getName());
                        androidAppName.setTextSize(30);
                        androidAppName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        cardLayout.addView(androidAppName);

                        // Image
                        if(app.getAvatar() != null && app.getAvatar() != "empty") {
                            String baseUrl = "https://matryoshkadoll.me/storage/";
                            ImageView androidAppAvatar = new ImageView(DrawerActivity.this);
                            Picasso.get().load(baseUrl + app.getAvatar()).resize(150, 150).into(androidAppAvatar);
                            cardLayout.addView(androidAppAvatar);
                        }

                        // Description
                        TextView androidAppDescription = new TextView(DrawerActivity.this);
                        androidAppDescription.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        androidAppDescription.setText(app.getDescription());
                        androidAppDescription.setTextSize(15);
                        cardLayout.addView(androidAppDescription);

                        // Price
                        TextView androidAppPrice = new TextView(DrawerActivity.this);
                        androidAppPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        androidAppPrice.setText("Price: " + app.getPrice());
                        androidAppPrice.setTextSize(15);
                        androidAppPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                        cardLayout.addView(androidAppPrice);

                        // Space between cards
                        Space androidListSpace = new Space(DrawerActivity.this);
                        androidListSpace.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 25));

                        androidAppView.addView(cardLayout);
                        appsList.addView(androidAppView);
                        appsList.addView(androidListSpace);
                    }
                } else {
                    TextView androidAppView = new TextView(DrawerActivity.this);
                    androidAppView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    androidAppView.setText("There are no apps.");
                    appsList.addView(androidAppView);
                }

                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<AndroidApp>> call, Throwable t) {
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
