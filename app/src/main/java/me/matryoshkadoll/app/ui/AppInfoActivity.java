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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.UserName;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class AppInfoActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_app_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
}
    private void FetchToken(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        An = prefs.getString("AccessToken", "No name defined");
        //userid = prefs.getInt("UserId",1);

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
        File file=new File(getExternalFilesDir(null),AppId+".apk");
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        String url = "https://matryoshkadoll.me/api/v1/android_apps/"+AppId+"/file";
        //fetch token
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String An = prefs.getString("AccessToken", "No name defined");

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
                .addRequestHeader("Authorization", An)
                .setTitle(AppId+".apk")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
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




    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

            Toast.makeText(ctxt, "Download Complete!", Toast.LENGTH_LONG).show();
        }
    };

    BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Downloadinggggggg!", Toast.LENGTH_LONG).show();
        }
    };
}


