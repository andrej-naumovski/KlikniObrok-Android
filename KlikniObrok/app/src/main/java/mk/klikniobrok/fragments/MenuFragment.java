package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mk.klikniobrok.LocationActivity;
import mk.klikniobrok.R;
import mk.klikniobrok.RestaurantActivity;
import mk.klikniobrok.fragments.adapters.MenuRecyclerViewAdapter;
import mk.klikniobrok.fragments.listeners.RecyclerItemClickListener;
import mk.klikniobrok.services.Data;

/**
 * Created by gjorgjim on 1/21/17.
 */

public class MenuFragment extends Fragment {
    private RestaurantActivity restaurantActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        restaurantActivity = (RestaurantActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_layout, container, false);

        final List<String> array = Data.getKeys();

        final RecyclerView.Adapter adapter = new MenuRecyclerViewAdapter(array);
        RecyclerView.LayoutManager manager = new GridLayoutManager(restaurantActivity, 2);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.menuRecyclerView);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(restaurantActivity, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        restaurantActivity.onItemClick(array.get(position));
                    }
                })
        );

        return view;
    }
}
