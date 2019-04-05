package me.matryoshkadoll.app.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.InstalledApp;
import me.matryoshkadoll.app.api.service.matryoshka.AndroidAppsClient;
import me.matryoshkadoll.app.network.RetrofitClientInstance;
import me.matryoshkadoll.app.ui.InstalledActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.pm.PackageManager;

public class Installed_Apps_Adapter extends RecyclerView.Adapter<Installed_Apps_Adapter.MyViewHolder> {

    private List<InstalledApp> appList;
    Context content;
    PackageManager packageManager;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mDrawable;
        private String package_name;
        public  TextView mTextview2;
        public MyViewHolder(View v) {
            super(v);
            mTextView  = (TextView) itemView.findViewById(R.id.list_app_name);
            mDrawable  = (ImageView) itemView.findViewById(R.id.app_icon);
            mTextview2 = (TextView) itemView.findViewById(R.id.textView9);
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent it= new Intent(v.getContext(), InstalledActivity.class);
                    it.putExtra("package_name",package_name);
                    v.getContext().startActivity(it);


                }
            });

        }
    }
    public Installed_Apps_Adapter() {


    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Installed_Apps_Adapter(

            List<InstalledApp> appList, Context con) {

        this.appList = appList;
        this.content = con;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Installed_Apps_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_applist_adapter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        InstalledApp ip = appList.get(position);
        try {
            holder.mDrawable.setImageDrawable(ip.getPackageInfo().applicationInfo.loadIcon(content.getPackageManager()));

        }
        catch (Exception e){}

    if(ip.isExistonserver()==true){

    }
    else if(ip.isExistonserver()==false){

        //holder.mTextview2.setText("Warning!! This app is not from Matryshka app store");
    }
       holder.mTextView.setText(ip.getPackageInfo().packageName);
        holder.package_name = ip.getPackageInfo().packageName;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return appList.size();

    }

}
