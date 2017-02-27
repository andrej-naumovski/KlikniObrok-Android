package mk.klikniobrok.services;

import mk.klikniobrok.models.User;

/**
 * Created by gjorgjim on 2/27/17.
 */

public interface UserService {
    User getUserByUsername(String authToken, String username);
    User getUserByEmail(String authToken, String email);
}
