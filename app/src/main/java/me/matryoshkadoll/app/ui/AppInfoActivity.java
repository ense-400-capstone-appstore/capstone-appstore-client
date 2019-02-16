package me.matryoshkadoll.app.ui;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class AppInfoActivity extends AppCompatActivity {
    private TextView tvs;
    private Button button;
    private long downloadID;
    private  String AppId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_app_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent it=getIntent();
        tvs = (TextView) findViewById(R.id.textView2);
        int tt = it.getIntExtra("app_id",999);
        tvs.setText(Integer.toString(tt));
        AppId=Integer.toString(tt);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginDownload();

            }
        });

    }
// under oncreate


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

}
