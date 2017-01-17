package mk.klikniobrok.database.model;

/**
 * Created by gjorgjim on 1/17/17.
 */

public class UserDB {
    private int id;
    private String token;
    private long time;

    public UserDB(String token, long time) {
        this.token = token;
        this.time = time;
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
}
