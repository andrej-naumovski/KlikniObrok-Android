package mk.klikniobrok.database.model;

/**
 * Created by gjorgjim on 1/17/17.
 */

public class UserDB {
    private int id;
    private String token;
    private long time;
    private String restaurantName;

    public UserDB(String token, long time, String restaurantName) {
        this.token = token;
        this.time = time;
        this.restaurantName = restaurantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
