package mk.klikniobrok.services.impl;

import java.util.HashMap;

import mk.klikniobrok.http.HttpMethods;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;

/**
 * Created by gjorgjim on 1/16/17.
 */

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String login(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String response = HttpMethods.doPost("https://klikniobrok-java.herokuapp.com/auth/login", params);
        return response;
    }

    @Override
    public String register(User user) {
        return HttpMethods.doPost("https://klikniobrok-java.herokuapp.com/auth/register", user);
    }

    @Override
    public String fbLogin(String email, String tag) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("tag", tag);
        return HttpMethods.doPost("https://klikniobrok-java.herokuapp.com/auth/fb", params);
    }
}
