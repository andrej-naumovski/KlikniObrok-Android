package mk.klikniobrok.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
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

public class OrderFragment extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerTitleStrip pagerTabStrip;
    private RestaurantActivity restaurantActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_layout, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Активни нарачки"));
        tabLayout.addTab(tabLayout.newTab().setText("Моментални нарачки"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        android.support.v4.app.FragmentManager fragmentManager = restaurantActivity.getSupportFragmentManager();
        viewPager.setAdapter(new PagerTabStripAdapter(fragmentManager));
        tabLayout.setSelected(false);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        restaurantActivity = (RestaurantActivity) context;
    }
}
