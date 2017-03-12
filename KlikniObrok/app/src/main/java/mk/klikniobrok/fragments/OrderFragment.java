package mk.klikniobrok.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.R;
import mk.klikniobrok.RestaurantActivity;
import mk.klikniobrok.fragments.adapters.PagerTabStripAdapter;

/**
 * Created by gjorgjim on 1/21/17.
 */

public class OrderFragment extends Fragment {
    private ViewPager viewPager = null;
    private PagerTabStrip pagerTabStrip;
    private RestaurantActivity restaurantActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_layout, container, false);

        pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.title);
        pagerTabStrip.setTabIndicatorColor(Color.argb(236, 64, 122, 0));

        viewPager = (ViewPager)view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        android.support.v4.app.FragmentManager fragmentManager = restaurantActivity.getSupportFragmentManager();
        viewPager.setAdapter(new PagerTabStripAdapter(fragmentManager));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        restaurantActivity = (RestaurantActivity) context;
    }
}
