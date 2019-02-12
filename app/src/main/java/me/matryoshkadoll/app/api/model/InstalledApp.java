package me.matryoshkadoll.app.api.model;

import android.graphics.drawable.Drawable;

public class InstalledApp {
    private String label;
    private String packageName;
    private Drawable icon;

    public Drawable getIcon() {
        return icon;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getPackageName() {
        return packageName;
    }

    public String getLabel() {
        return label;
    }




}
