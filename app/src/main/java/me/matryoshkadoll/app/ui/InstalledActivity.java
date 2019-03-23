package me.matryoshkadoll.app.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.matryoshkadoll.app.R;

public class InstalledActivity extends AppCompatActivity {



    TextView appLabel, packageName, version, features;
    TextView permissions, andVersion, installed, lastModify, path;
    PackageInfo packageInfo;
    private Intent it;
    private  String pkname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed);
        findViewsById();
        it=getIntent();
        pkname = it.getStringExtra("package_name");
        try {
            packageInfo  = this.getPackageManager().getPackageInfo(pkname, 0);


        } catch (Exception e) {

        }

setValues();
    }

    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.applabel);
        packageName = (TextView) findViewById(R.id.package_name);
        version = (TextView) findViewById(R.id.version_name);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);
    }

    private void setValues() {
        // APP name
        appLabel.setText(getPackageManager().getApplicationLabel(
                packageInfo.applicationInfo));

        // package name
        packageName.setText(packageInfo.packageName);

        // version name
        version.setText(packageInfo.versionName);

        // target version
        andVersion.setText(Integer
                .toString(packageInfo.applicationInfo.targetSdkVersion));

        // path
        path.setText(packageInfo.applicationInfo.sourceDir);

        // first installation
        installed.setText(setDateFormat(packageInfo.firstInstallTime));

        // last modified
        lastModify.setText(setDateFormat(packageInfo.lastUpdateTime));


    }

    @SuppressLint("SimpleDateFormat")
    private String setDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        return strDate;
    }


}
