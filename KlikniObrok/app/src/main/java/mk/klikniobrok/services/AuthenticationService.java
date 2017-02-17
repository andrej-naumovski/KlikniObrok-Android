package mk.klikniobrok.services;

import mk.klikniobrok.models.User;

/**
 * Created by gjorgjim on 1/12/17.
 */

public interface AuthenticationService {
    String login(String username, String password);
    String register(User user);
    String fbLogin(String email, String tag);
}
