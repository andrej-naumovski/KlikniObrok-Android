package mk.klikniobrok.fragments.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mk.klikniobrok.R;

/**
 * Created by gjorgjim on 1/26/17.
 */

public class SubMenuRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.ViewHolder> {
    private List<String> array;

    public SubMenuRecyclerViewAdapter(List<String> array) {
        this.array = array;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    @Override
    public RestaurantsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_menu_recycler_view_layout, parent, false);
        return new RestaurantsRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RestaurantsRecyclerViewAdapter.ViewHolder holder, int position) {
        AppCompatTextView subMenu = (AppCompatTextView) holder.view.findViewById(R.id.subMenuText);
        subMenu.setText(array.get(position));
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
}
