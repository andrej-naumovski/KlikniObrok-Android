package mk.klikniobrok.fragments.listeners;

/**
 * Created by gjorgjim on 12/6/16.
 */

public interface UserManagementListener {
    void loginUser(String username, String password);
    void saveUserInfo(String firstName, String lastName, String username);
    void registerUser(String password, String email);
}
