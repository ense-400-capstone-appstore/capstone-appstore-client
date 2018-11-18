package me.matryoshkadoll.app.ui;

import android.app.ActionBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO: This view is a placeholder for demonstration purposes only. Need to refactor.
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout appsList;
    private SwipeRefreshLayout refreshLayout;

    /**
     * Inflate the options menu
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Check if user triggered a refresh:
            case R.id.menu_refresh:
                fetchAndroidApps();
                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://matryoshkadoll.me/api/v1/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AndroidAppsClient client = retrofit.create(AndroidAppsClient.class);
        Call<List<AndroidApp>> call = client.androidApps();

        // Notify user that fetch is in progress
        appsList.removeAllViews();
        TextView androidAppView = new TextView(MainActivity.this);
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
                        CardView androidAppView = new CardView(MainActivity.this);
                        androidAppView.setMaxCardElevation(4);
                        androidAppView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        // Vertical layout inside card
                        LinearLayout cardLayout = new LinearLayout(MainActivity.this);
                        cardLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        cardLayoutParams.setMargins(20, 20, 20, 20);
                        cardLayout.setLayoutParams(cardLayoutParams);

                        // Title
                        TextView androidAppName = new TextView(MainActivity.this);
                        androidAppName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        androidAppName.setText(app.getName());
                        androidAppName.setTextSize(30);
                        androidAppName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        cardLayout.addView(androidAppName);

                        // Description
                        TextView androidAppDescription = new TextView(MainActivity.this);
                        androidAppDescription.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        androidAppDescription.setText(app.getDescription());
                        androidAppDescription.setTextSize(15);
                        cardLayout.addView(androidAppDescription);

                        // Price
                        TextView androidAppPrice = new TextView(MainActivity.this);
                        androidAppPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        androidAppPrice.setText("Price: " + app.getPrice());
                        androidAppPrice.setTextSize(15);
                        androidAppPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                        cardLayout.addView(androidAppPrice);

                        // Space between cards
                        Space androidListSpace = new Space(MainActivity.this);
                        androidListSpace.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 25));

                        androidAppView.addView(cardLayout);
                        appsList.addView(androidAppView);
                        appsList.addView(androidListSpace);
                    }
                } else {
                    TextView androidAppView = new TextView(MainActivity.this);
                    androidAppView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    androidAppView.setText("There are no apps.");
                    appsList.addView(androidAppView);
                }

                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<AndroidApp>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching Android Apps!", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
