package me.matryoshkadoll.app.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.network.OkHTTPClientInstance;

import static me.matryoshkadoll.app.login.LoginActivity.MY_PREFS_NAME;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileimg;
    private TextView name;
    private TextView email;
    private int userid;
    private String An;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TemplateMethod();


    }


    private void TemplateMethod(){
        FetchToken();
        DisplayProfileImg();
    }

    private void FetchToken(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        An = prefs.getString("AccessToken", "No name defined");
        userid = prefs.getInt("UserId",1);

    }
    private void DisplayProfileImg(){
        String url = "https://matryoshkadoll.me/api/v1/users/"+userid+"/avatar";

        OkHTTPClientInstance aa = new OkHTTPClientInstance();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(aa.getAvatar(An)))
                .build();
        profileimg= (ImageView) findViewById(R.id.profile);
        picasso.load(url).into(profileimg);
    }
}
