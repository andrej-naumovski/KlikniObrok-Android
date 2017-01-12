package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.LocationActivity;
import mk.klikniobrok.R;
import mk.klikniobrok.fragments.adapters.RecyclerViewAdapter;
import mk.klikniobrok.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjorgjim on 12/4/16.
 */

public class YourLocationFragment extends Fragment {
    private LocationActivity lActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lActivity = (LocationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_restaurant, container, false);

        List<Restaurant> array = lActivity.getRestaurants();


        AppCompatTextView titleTextView = (AppCompatTextView) view.findViewById(R.id.titleTextView);
        AppCompatTextView addressTextView = (AppCompatTextView) view.findViewById(R.id.addressTextView);
        AppCompatTextView otherLocationsTextView = (AppCompatTextView) view.findViewById(R.id.otherLocationsTextView);

        if(array.size() > 0) {
            if(array.size() == 1) {
                Restaurant nearestRestaurant = array.get(0);
                array.remove(0);

                titleTextView.setText(nearestRestaurant.getName());
                addressTextView.setText(nearestRestaurant.getAddress().getName());

                otherLocationsTextView.setText("Нема други локации");
            }
            else {
                if(array.size() >= 1) {
                    Restaurant nearestRestaurant = array.get(0);
                    array.remove(0);

                    titleTextView.setText(nearestRestaurant.getName());
                    addressTextView.setText(nearestRestaurant.getAddress().getName());

                    RecyclerView.Adapter adapter = new RecyclerViewAdapter(array);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(lActivity) {
                        @Override
                        public boolean canScrollVertically(){
                            return false;
                        }
                    };
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }
            }
        }


        AppCompatTextView imHere = (AppCompatTextView)view.findViewById(R.id.imHereTextView);
        imHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                lActivity.sucessfullLocation();
            }
        });

        return view;
    }
}
