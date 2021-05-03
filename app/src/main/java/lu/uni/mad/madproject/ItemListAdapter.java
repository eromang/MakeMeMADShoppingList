package lu.uni.mad.madproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.roomwordssample.R;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays a list of items.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems; // Cached copy of items
    private static ItemListAdapter.ClickListener clickListener;

    ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            String item = "Qty : " + current.getQuantity() + " of " + current.getItem();
            holder.itemItemView.setText(item);
        } else {
            // Covers the case of data not being ready yet.
            holder.itemItemView.setText(R.string.no_item);
        }
    }

    /**
     * Associates a list of items with this adapter
     */
    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mItems has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    /**
     * Gets the item at a given position.
     * This method is useful for identifying which item
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the item in the RecyclerView
     * @return The item at the given position
     */
    public Item getItemAtPosition(int position) {
        return mItems.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemItemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ItemListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
