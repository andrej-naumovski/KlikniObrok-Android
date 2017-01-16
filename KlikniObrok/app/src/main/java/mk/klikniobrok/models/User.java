package mk.klikniobrok.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gjorgjim on 12/6/16.
 */

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private int enabled;
    private java.util.Date dateCreated;
    private java.util.Date lastUsed;
    private Role role;

    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password,
                int enabled, java.util.Date dateCreated, java.util.Date lastUsed, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.dateCreated = dateCreated;
        this.lastUsed = lastUsed;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
