package me.matryoshkadoll.app.api.model;

import android.app.Application;
import android.content.pm.PackageInfo;

public class InstalledApp extends Application {
    PackageInfo packageInfo;


    boolean existonserver;

    public boolean isExistonserver() {
        return existonserver;
    }

    public void setExistonserver(boolean existonserver) {
        this.existonserver = existonserver;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }



}
