package me.matryoshkadoll.app.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.adapter.Installed_Apps_Adapter;
import me.matryoshkadoll.app.api.model.InstalledApp;

public class UpdateActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<InstalledApp> appList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //List<String> input = new ArrayList<>();
       // for (int i = 0; i < 100; i++) {
      //      input.add("Test" + i);
      //  }// define an adapter
// specify an adapter (see also next example)


        //fetch installed apps
        final PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApplications =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        //LinearLayout appsList = findViewById(R.id.android_apps_installed);

        for (ApplicationInfo appInfo : installedApplications)
        {

            InstalledApp app = new InstalledApp();
            app.setPackageName(appInfo.packageName);
            app.setLabel(appInfo.loadLabel(packageManager).toString());
            Log.i("OUTPUT", "Package name : " + app.getPackageName());
            Log.i("OUTPUT", "Name: " + app.getLabel());
            appList.add(app);

        }

        mAdapter = new Installed_Apps_Adapter(appList);
        mRecyclerView.setAdapter(mAdapter);







    }

}
