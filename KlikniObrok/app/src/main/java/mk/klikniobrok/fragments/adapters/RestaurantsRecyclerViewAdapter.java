package mk.klikniobrok.fragments.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.R;
import mk.klikniobrok.models.Restaurant;

import java.util.List;

/**
 * Created by gjorgjim on 12/4/16.
 */

public class RestaurantsRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.ViewHolder> {
    private List<Restaurant> array;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public RestaurantsRecyclerViewAdapter(List<Restaurant> array) {
       // this.array = new ArrayList<>();
        this.array = array;
    }

    @Override
    public RestaurantsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_recycler_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        AppCompatTextView title = (AppCompatTextView) holder.view.findViewById(R.id.recycerViewTitle);
        AppCompatTextView address = (AppCompatTextView) holder.view.findViewById(R.id.recuclerViewAddress);
        title.setText(array.get(position).getName());
        address.setText(array.get(position).getAddress().getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return array.size();
    }
}
