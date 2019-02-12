package me.matryoshkadoll.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.InstalledApp;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.AndroidApp;
public class Android_Apps_Adapter  extends RecyclerView.Adapter<me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder> {

        private List<AndroidApp.Datum> appList;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ImageView mDrawable;
            public MyViewHolder(View v) {
                super(v);
                mTextView  = (TextView) itemView.findViewById(R.id.list_app_name);
                mDrawable  = (ImageView) itemView.findViewById(R.id.app_icon);;

            }
        }
        public Android_Apps_Adapter() {


        }
        // Provide a suitable constructor (depends on the kind of dataset)
        public Android_Apps_Adapter(

                List<AndroidApp.Datum> appList) {

            this.appList = appList;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public me.matryoshkadoll.app.adapter.Android_Apps_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                    int viewType) {
            // create a new view
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_installed__apps__adapter, parent, false);

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
            //holder.mDrawable.setImageDrawable(ip.getDescription());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return appList.size();

        }
    }

