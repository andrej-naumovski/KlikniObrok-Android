package mk.klikniobrok.fragments.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mk.klikniobrok.R;
import mk.klikniobrok.models.Entry;
import mk.klikniobrok.models.QuantityType;

/**
 * Created by gjorgjim on 1/26/17.
 */

public class SubMenuRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.ViewHolder> {
    private List<Entry> array;

    public SubMenuRecyclerViewAdapter(List<Entry> array) {
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
        AppCompatTextView name = (AppCompatTextView) holder.view.findViewById(R.id.subMenuName);
        AppCompatTextView ingredients = (AppCompatTextView)holder.view.findViewById(R.id.subMenuIngredients);
        AppCompatTextView price = (AppCompatTextView)holder.view.findViewById(R.id.subMenuPrice);
        AppCompatTextView quantity = (AppCompatTextView)holder.view.findViewById(R.id.subMenuQuantity);
        AppCompatTextView quanityType = (AppCompatTextView)holder.view.findViewById(R.id.subMenuQuantityType);
        name.setText(array.get(position).getName());
        ingredients.setText(array.get(position).getIngredients());
        price.setText(array.get(position).getPrice() + " ден");
        quantity.setText(array.get(position).getQuantity() + "");
        if(array.get(position).getQuantityType() == QuantityType.GRAMS) {
            quanityType.setText("г");
        } else {
            quanityType.setText("мл");
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
}
