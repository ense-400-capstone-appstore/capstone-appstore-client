package me.matryoshkadoll.app.api.model;

import android.app.Application;
import android.content.pm.PackageInfo;

public class InstalledApp extends Application {
    PackageInfo packageInfo;

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }



}
