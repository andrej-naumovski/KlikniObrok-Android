package mk.klikniobrok.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.R;
import mk.klikniobrok.fragments.listeners.LocationManagerListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;

/**
 * Created by gjorgjim on 12/4/16.
 */

public class ProgressBarFragment extends Fragment {
    private TypefaceChangeListener typefaceChangeListener;
    private LocationManagerListener locationManagerListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        typefaceChangeListener = (TypefaceChangeListener) context;
        locationManagerListener = (LocationManagerListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spinning_bar_layout, container, false);

        AppCompatTextView gettingLocation = (AppCompatTextView) view.findViewById(R.id.gettingLocationTextView);
        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", gettingLocation);

        locationManagerListener.findCurrentLocation();

        return view;
    }
}
