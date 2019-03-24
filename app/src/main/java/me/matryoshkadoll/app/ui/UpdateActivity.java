package me.matryoshkadoll.app.ui;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.adapter.Installed_Apps_Adapter;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.InstalledApp;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class UpdateActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String An;
    private boolean a;

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

        appList = getInstalledApps();

        FetchToken();


        // specify an adapter (see also next example)
        mAdapter = new Installed_Apps_Adapter(appList, getApplicationContext() );
        mRecyclerView.setAdapter(mAdapter);







    }

    private List<InstalledApp> getInstalledApps() {
        List<InstalledApp> res = new ArrayList<InstalledApp>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == false)) {
                InstalledApp ins = new InstalledApp();
                ins.setPackageInfo(p);
                ins.setExistonserver(checkAvailablity(ins.getPackageInfo().packageName));
                res.add(ins);
            }
        }
        return res;
    }
private  boolean checkAvailablity(String pkname){
    AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);
if(pkname == null){
    return false;
}
    Call<AndroidappInfo> callapp = client.checkappexist(An,pkname);
    // HTTP callback
    callapp.enqueue(new Callback<AndroidappInfo>() {
        @Override
        public void onResponse(Call<AndroidappInfo> call, Response<AndroidappInfo> response) {
            // Get data from response
            AndroidappInfo myappinfo = response.body();
            if(response.code()==404){
                a = false;
            }
            else if(response.code()==200){
                a = true;
            }else{
                a = false;
            }
        }
        @Override
        public void onFailure(Call<AndroidappInfo> call, Throwable t) {

        }
    });
    return a;

}
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
    private void FetchToken(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        An = prefs.getString("AccessToken", "No name defined");

    }
}
