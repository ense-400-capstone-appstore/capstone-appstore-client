package me.matryoshkadoll.app.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.matryoshkadoll.app.R;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApplications =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : installedApplications)
        {
            Log.i("OUTPUT", "Package name : " + appInfo.packageName);
            Log.i("OUTPUT", "Name: " + appInfo.loadLabel(packageManager));
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
