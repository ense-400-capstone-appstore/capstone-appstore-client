package me.matryoshkadoll.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.matryoshkadoll.app.R;
import me.matryoshkadoll.app.api.model.Categories;

public class Categories_Adapter extends RecyclerView.Adapter<Categories_Adapter.MyViewHolder> {

    private Categories categories;
private List<Categories.Datum> ip;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mTextView  = (TextView) itemView.findViewById(R.id.textView4);

        }
    }
    public Categories_Adapter() {


    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Categories_Adapter(

            Categories categories) {

        this.categories = categories;
        this.ip = categories.getData();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public Categories_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_adapter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Categories.Datum ipp = ip.get(position);
        holder.mTextView.setText(ipp.getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return ip.size();

    }
}
