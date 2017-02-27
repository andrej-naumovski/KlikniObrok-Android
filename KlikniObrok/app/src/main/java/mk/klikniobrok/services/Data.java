package mk.klikniobrok.services;

import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
                new Address("lokacija 1", null, null, null, 0),new LatLng(42.007873, 21.374987 )));
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

    public static HashMap<String, List<String>> getHashMap() {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("Obrok1");
        list.add("Obrok2");
        list.add("Obrok3");
        list.add("Obrok4");
        list.add("Obrok5");
        list.add("Obrok6");
        hashMap.put("Pijaloci1", list);
        hashMap.put("Pijaloci2", list);
        hashMap.put("Pijaloci3", list);
        hashMap.put("Pijaloci4", list);
        hashMap.put("Pijaloci5", list);
        hashMap.put("Pijaloci6", list);

        return hashMap;
    }

    public static List<String> getKeys() {
        return new ArrayList<String>(getHashMap().keySet());
    }

    public static List<String> getSubMenuItems(String key) {
        return getHashMap().get(key);
    }
}
