package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mk.klikniobrok.R;
import mk.klikniobrok.RestaurantActivity;
import mk.klikniobrok.fragments.adapters.SubMenuRecyclerViewAdapter;
import mk.klikniobrok.models.Entry;
import mk.klikniobrok.services.Data;

/**
 * Created by gjorgjim on 1/26/17.
 */

public class SubMenuFragment extends Fragment {
    private String type;
    private RestaurantActivity restaurantActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        restaurantActivity = (RestaurantActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_menu_fragment_layout, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            type = bundle.getString("type");
        }
        Log.d("key", type);
        List<Entry> array = restaurantActivity.getEntriesByType(type);

        RecyclerView.Adapter adapter = new SubMenuRecyclerViewAdapter(array);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(restaurantActivity);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.subMenuRecyclerView);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
