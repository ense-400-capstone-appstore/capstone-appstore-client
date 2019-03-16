package me.matryoshkadoll.app.ui;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import me.matryoshkadoll.app.BuildConfig;
import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.UserName;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.OkHTTPClientInstance;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class AppInfoActivity extends AppCompatActivity {
    private Context context;
    private TextView tvs;
    private Button button;
    private long downloadID;
    private  String AppId;
    private TextView appname;
    private TextView appdescription;
private AndroidappInfo.Data data;
    AlertDialog.Builder builder;
    private Intent it;
    private  int appid;
    private  String An;
    private  ImageView appimg;
    private Uri uri;
    private DownloadManager manager;
    private long downloadId;
    File apkFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_app_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();


        TemplateMethod();

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(onNotificationClick,
                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

    }
    // under oncreate
    private void TemplateMethod(){
        FetchToken();
        fetchappid();
        downloadbutton();
        getappinfo();
        DisplayProfileImg();
}
    private void FetchToken(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        An = prefs.getString("AccessToken", "No name defined");

    }
    private void fetchappid(){
        it=getIntent();
    appid = it.getIntExtra("app_id",999);
    tvs = (TextView) findViewById(R.id.textView2);

    tvs.setText(Integer.toString(appid));
    AppId=Integer.toString(appid);


}
    private void downloadbutton(){
    button=findViewById(R.id.button);
    builder = new AlertDialog.Builder(this);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            builder.setTitle("Confirm Download")
                    .setMessage("Are you sure you want to Download this app ?")
                    .setCancelable(false)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            beginDownload();
                            Toast.makeText(getApplicationContext(),"Download Begins",
                                    Toast.LENGTH_SHORT).show();
                            button.setText("Downloading");
                            button.setEnabled(false);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"Download Stopped",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.show();
        }
    });

}
    private void beginDownload(){
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = data.getName()+".apk";
        String destinationf = destination + fileName;
        uri = Uri.parse("file://" + destinationf);

        //Delete update file if exists
        File file = new File(destinationf);
        if (file.exists())
            //file.delete() - test this, I think sometimes it doesnt work
            file.delete();
        apkFile = new File(destination, fileName);
        //get url of app on server
        String url = "https://matryoshkadoll.me/api/v1/android_apps/"+AppId+"/file";

            DownloadManager.Request request=new DownloadManager.Request(uri.parse(url))
                    .addRequestHeader("Authorization", An)
                    .setTitle(data.getName()+".apk")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationUri(uri)// Uri of the destination file
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network

            // get download service and enqueue file
            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId = manager.enqueue(request);



    }
    private void getappinfo(){


        AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);

        Call<AndroidappInfo> callapp = client.androidappinfo(An,appid);
        // HTTP callback
        callapp.enqueue(new Callback<AndroidappInfo>() {
            @Override
            public void onResponse(Call<AndroidappInfo> call, Response<AndroidappInfo> response) {
                // Get data from response
                AndroidappInfo myappinfo = response.body();

                if(myappinfo != null){
                    data = myappinfo.getData();
                    appname = (TextView)findViewById(R.id.textView3);
                    appdescription = (TextView)findViewById(R.id.description);
                    appname.setText(data.getName());
                    appdescription.setText(data.getDescription());
                }
            }
            @Override
            public void onFailure(Call<AndroidappInfo> call, Throwable t) {

            }
        });
    }

    private void DisplayProfileImg(){
        String url = "https://matryoshkadoll.me/api/v1/android_apps/" + appid+"/avatar";

        OkHTTPClientInstance aa = new OkHTTPClientInstance();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(aa.getAvatar(An)))
                .build();
        appimg= (ImageView) findViewById(R.id.imageView2);
        picasso.load(url).into(appimg);
    }

    //set BroadcastReceiver to install app when .apk is downloaded
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Download Complete!", Toast.LENGTH_LONG).show();


            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            startActivity(install);
            //finish();
        }
    };
    BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Downloadinggggggg!", Toast.LENGTH_LONG).show();
        }
    };
    @Override public void onDestroy() {
        this.unregisterReceiver(onComplete);
        this.unregisterReceiver(onNotificationClick);

        super.onDestroy();
    }


}


