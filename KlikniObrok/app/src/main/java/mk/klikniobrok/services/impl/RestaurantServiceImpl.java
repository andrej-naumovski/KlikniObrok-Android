package mk.klikniobrok.services.impl;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mk.klikniobrok.http.HttpMethods;
import mk.klikniobrok.models.Restaurant;
import mk.klikniobrok.services.RestaurantService;

/**
 * Created by gjorgjim on 2/13/17.
 */

public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public List<Restaurant> getAllRestaurants(String authToken) {
        Gson gsonParser = new GsonBuilder().create();
        Type listType = new TypeToken<List<Restaurant>>() {
        }.getType();
        String jsonArrayRestaurants = HttpMethods.doGet(
                "https://klikniobrok-java.herokuapp.com/restaurants/",
                null,
                authToken
        );
        List<Restaurant> restaurants = gsonParser.fromJson(jsonArrayRestaurants, listType);
        return restaurants;
    }

    @Override
    public List<Restaurant> filterRestaurantsByLocation(List<Restaurant> restaurants, Location location) {
        Location restLocation = new Location("");
        List<Restaurant> newArray = new ArrayList<>();
        List<Float> results = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++) {
            restLocation.setLongitude(restaurants.get(i).getLocation().getLongitude());
            restLocation.setLatitude(restaurants.get(i).getLocation().getLatitude());
            float result = location.distanceTo(restLocation);
            if (result <= 400) {
                newArray.add(restaurants.get(i));
                results.add(result);
            }
        }
        int minResult = 0;
        for (int i = 1; i < results.size(); i++) {
            if (results.get(minResult) > results.get(i)) {
                minResult = i;
            }
        }
        if (minResult != 0) {
            Restaurant tempRestaurant = newArray.get(0);
            newArray.set(0, newArray.get(minResult));
            newArray.set(minResult, tempRestaurant);
        }
        return newArray;
    }

    @Override
    public List<String> getRestaurantEntryTypes(String authToken, Restaurant restaurant) {
        List<String> entryTypes;
        Gson gsonParser = new Gson();
        Type listType = new TypeToken<List<String>>(){}.getType();
        String jsonArrayEntryTypes = HttpMethods.doGet(
                "https://klikniobrok-java.herokuapp.com/restaurants/" + restaurant.getId() + "/entry_types",
                null,
                authToken
        );
        entryTypes = gsonParser.fromJson(jsonArrayEntryTypes, listType);
        Log.d("entry types", entryTypes.toString());
        return entryTypes;
    }
}
