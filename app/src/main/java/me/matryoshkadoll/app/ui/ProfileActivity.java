package me.matryoshkadoll.app.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.UserName;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.OkHTTPClientInstance;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        DisplayName();
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
    private void DisplayName(){
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.emailh);
        AndroidAppsClient client = RetrofitClientInstance.getRetrofitInstance().create(AndroidAppsClient.class);

        Call<UserName> calluser = client.GetUserName(An,userid);
        // HTTP callback
        calluser.enqueue(new Callback<UserName>() {
            @Override
            public void onResponse(Call<UserName> call, Response<UserName> response) {
                // Get data from response
                UserName myUserName = response.body();
                if(myUserName != null){
                    email.setText(myUserName.getData().getEmail());
                    name.setText(myUserName.getData().getName());
                }
            }
            @Override
            public void onFailure(Call<UserName> call, Throwable t) {

            }
        });

    }
}
