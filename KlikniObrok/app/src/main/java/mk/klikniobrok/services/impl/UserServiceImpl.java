package mk.klikniobrok.services.impl;

import com.google.gson.Gson;

import java.util.HashMap;

import mk.klikniobrok.http.HttpMethods;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.UserService;

/**
 * Created by gjorgjim on 2/27/17.
 */

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUsername(String authToken, String username) {
        Gson gsonParser = new Gson();
        String jsonUser = HttpMethods.doGet(
                "https://klikniobrok-java.herokuapp.com/user/" + username,
                null,
                authToken
        );
        return gsonParser.fromJson(jsonUser, User.class);
    }

    @Override
    public User getUserByEmail(String authToken, String email) {
        Gson gsonParser = new Gson();
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        String jsonUser = HttpMethods.doGet(
                "https://klikniobrok-java.herokuapp.com/user",
                params,
                authToken
        );
        return gsonParser.fromJson(jsonUser, User.class);
    }
}
