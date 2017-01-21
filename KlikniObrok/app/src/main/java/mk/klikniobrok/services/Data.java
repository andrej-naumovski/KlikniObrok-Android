package mk.klikniobrok.services;

import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import mk.klikniobrok.models.Address;
import mk.klikniobrok.models.LatLng;
import mk.klikniobrok.models.Restaurant;

/**
 * Created by gjorgjim on 1/12/17.
 */

public class Data {
    public static List<Restaurant> getRestaurantList(Location location) {
        List<Restaurant> array = new ArrayList<>();
        array.add(new Restaurant(0, "Vlae doma", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.002457, 21.407883 )));
        array.add(new Restaurant(0, "Kaj mete doma", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(41.988460, 21.451722 )));
        array.add(new Restaurant(0, "Public Room 3", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.002457, 21.407883 )));
        array.add(new Restaurant(0, "Public Room 4", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.002497, 21.407903 )));
        array.add(new Restaurant(0, "Public Room 5", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.002537, 21.407953 )));
        array.add(new Restaurant(0, "Public Room 6", null, null,
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.002587, 21.407993 )));

        Location restLocation = new Location("");
        List<Restaurant> newArray = new ArrayList<>();
       List<Float> results = new ArrayList<>();
        for(int i = 0; i < array.size(); i++) {
            restLocation.setLongitude(array.get(i).getLocation().getLongitude());
            restLocation.setLatitude(array.get(i).getLocation().getLatitude());
            float result = location.distanceTo(restLocation);
            if(result <= 400) {
                newArray.add(array.get(i));
                results.add(result);
            }
        }
        int minResult = 0;
        for(int i = 1; i < results.size(); i++) {
            if(results.get(minResult) > results.get(i)) {
                minResult = i;
            }
        }
        if(minResult != 0) {
            Restaurant tempRestaurant = newArray.get(0);
            newArray.set(0, newArray.get(minResult));
            newArray.set(minResult, tempRestaurant);
        }
        return newArray;
    }
}
