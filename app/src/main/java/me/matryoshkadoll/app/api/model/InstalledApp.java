package me.matryoshkadoll.app.api.model;

public class InstalledApp {
    private String label;
    private String packageName;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
