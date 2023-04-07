package com.example.simpletodo;
import android.R.layout;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*Responsible for displaying data from the model into a row in the recycler view*/
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void OnItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;
    /*Constructor*/
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        /*Use layout inflator to inflate a view*/
        /*Using an android resource file here! (simple_list_item_1)*/
        View todoView = LayoutInflater.from(viewGroup.getContext()).inflate(layout.simple_list_item_1, viewGroup, false);
        /*Wrap inside a View Holder and return*/
        return new ViewHolder(todoView);
    }

    /*Responsible for binding data to a particular view holder*/
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder viewHolder, int i) {
        /*Grab the item at position i*/
        String item = items.get(i);
        /*Bind the item into the specified view holder*/
        viewHolder.bind(item);
    }

    /*Tells RV how many items are in the list*/
    @Override
    public int getItemCount() { return items.size(); }

    /*Container to provide easy access to views that represent each row of the list*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*Using android since we're referencing a built-in android resource file*/
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        /*Update the view inside of the view holder with data*/
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    /*Notify listener which item was long pressed*/
                    longClickListener.OnItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
