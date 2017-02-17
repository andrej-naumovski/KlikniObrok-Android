package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.LocationActivity;
import mk.klikniobrok.R;
import mk.klikniobrok.fragments.adapters.RestaurantsRecyclerViewAdapter;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.models.Restaurant;

import java.util.List;

/**
 * Created by gjorgjim on 12/4/16.
 */

public class YourLocationFragment extends Fragment {
    private LocationActivity lActivity;
    private Restaurant nearestRestaurant;
    private TypefaceChangeListener typefaceChangeListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        typefaceChangeListener = (TypefaceChangeListener) context;
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
        AppCompatTextView imHere = (AppCompatTextView)view.findViewById(R.id.imHereTextView);
        CardView firstCardView = (CardView) view.findViewById(R.id.firstCardView);
        CardView secondCardView = (CardView) view.findViewById(R.id.secondCardView);
        AppCompatTextView yourLocationTextView = (AppCompatTextView)view.findViewById(R.id.yourLocationTextView);
        AppCompatButton checkLocationAgain = (AppCompatButton)view.findViewById(R.id.checkLocationAgainButton);

        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", checkLocationAgain);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", yourLocationTextView);



        if(array.size() > 0) {

            nearestRestaurant = array.get(0);
            array.remove(0);

            if(array.size() == 0) {
                titleTextView.setText(nearestRestaurant.getName());
                addressTextView.setText(nearestRestaurant.getAddress().getName());

                secondCardView.setVisibility(View.GONE);
            }
            else {
                if(array.size() > 0) {
                    titleTextView.setText(nearestRestaurant.getName());
                    addressTextView.setText(nearestRestaurant.getAddress().getName());

                    RecyclerView.Adapter adapter = new RestaurantsRecyclerViewAdapter(array);
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
        } else {
            firstCardView.setVisibility(View.GONE);
            secondCardView.setVisibility(View.GONE);
            yourLocationTextView.setText("Нема локал на вашата локација");
            checkLocationAgain.setVisibility(View.VISIBLE);
        }


        imHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("imHereOnClick", nearestRestaurant.getName());
                lActivity.restaurantActivity(nearestRestaurant.getName());
            }
        });

        checkLocationAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lActivity.checkLocationAgain();
            }
        });

        return view;
    }
}
