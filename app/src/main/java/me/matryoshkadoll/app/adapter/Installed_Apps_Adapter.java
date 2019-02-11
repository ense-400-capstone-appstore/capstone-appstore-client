package me.matryoshkadoll.app.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.InstalledApp;

public class Installed_Apps_Adapter extends RecyclerView.Adapter<Installed_Apps_Adapter.MyViewHolder> {

    private List<InstalledApp> appList;
    private List<String> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mTextView  = (TextView) itemView.findViewById(R.id.firstLine);;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Installed_Apps_Adapter(List<String> myDataset) {
        values = myDataset;
            //List<InstalledApp> appList) {

        //this.appList = appList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Installed_Apps_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_installed__apps__adapter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //InstalledApp ip = appList.get(position);
       // holder.mTextView.setText(ip.getPackageName());
        //holder.itemView.setTag(ip);
        final String name = values.get(position);
        holder.mTextView.setText(name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        //return appList.size();
        return values.size();

    }
}
