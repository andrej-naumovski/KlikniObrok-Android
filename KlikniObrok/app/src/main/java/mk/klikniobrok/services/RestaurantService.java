package mk.klikniobrok.services;

import android.location.Location;

import java.util.List;

import mk.klikniobrok.models.Entry;
import mk.klikniobrok.models.EntryWrapper;
import mk.klikniobrok.models.Restaurant;

/**
 * Created by gjorgjim on 2/13/17.
 */

public interface RestaurantService {
    List<Restaurant> getAllRestaurants(String authToken);
    List<Restaurant> filterRestaurantsByLocation(List<Restaurant> restaurants, Location location);
    List<String> getRestaurantEntryTypes(String authToken, Restaurant restaurant);
    List<Entry> getEntriesByType(String authToken, Restaurant restaurant, String entryType);
}
