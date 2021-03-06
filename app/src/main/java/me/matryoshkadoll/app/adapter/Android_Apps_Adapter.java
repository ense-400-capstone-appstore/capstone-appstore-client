package me.matryoshkadoll.app.adapter;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.matryoshkadoll.app.R;

import me.matryoshkadoll.app.api.model.AndroidApp;
import me.matryoshkadoll.app.network.OkHTTPClientInstance;
import me.matryoshkadoll.app.ui.AppInfoActivity;


public class Android_Apps_Adapter  extends RecyclerView.Adapter<me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder> {

        private List<AndroidApp.Datum> appList;
    private Context context;
private String token;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public TextView mTextView2;
            private int appid;

            public ImageView mDrawable;
            public MyViewHolder(View v) {
                super(v);
                mTextView  = (TextView) itemView.findViewById(R.id.list_app_name);
                mDrawable  = (ImageView) itemView.findViewById(R.id.app_icon);;
                mTextView2  = (TextView) itemView.findViewById(R.id.description);
                v.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        Intent it= new Intent(v.getContext(), AppInfoActivity.class);
                        it.putExtra("app_id",appid);
                        v.getContext().startActivity(it);


                    }
                });
            }
        }
        public Android_Apps_Adapter() {


        }
        // Provide a suitable constructor (depends on the kind of dataset)
        public Android_Apps_Adapter(

                List<AndroidApp.Datum> appList ,Context context ,String token) {

            this.appList = appList;
            this.context = context;
            this.token = token;

        }

        // Create new views (invoked by the layout manager)
        @Override
        public me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                    int viewType) {
            // create a new view
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_applist_adapter, parent, false);

            me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder vh = new me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            AndroidApp.Datum ip = appList.get(position);
            holder.mTextView.setText(ip.getName());
            holder.mTextView2.setText(ip.getDescription());
            holder.appid = ip.getId();
            Log.i("check app package", "Fetched " + ip.getPackageName());

            String url = "https://matryoshkadoll.me/api/v1/android_apps/"+ip.getId().toString()+"/avatar";

            OkHTTPClientInstance aa = new OkHTTPClientInstance();
            Picasso picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(aa.getAvatar(token)))
                    .build();
            picasso.load(url).into(holder.mDrawable);


        }




        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return appList.size();

        }


    }

