package mk.klikniobrok;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import mk.klikniobrok.fragments.LoginFragment;
import mk.klikniobrok.fragments.RegisterFragmentOne;
import mk.klikniobrok.fragments.RegisterFragmentTwo;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;
import mk.klikniobrok.models.User;

public class MainActivity extends AppCompatActivity implements TypefaceChangeListener, UserManagementListener, OnFragmentChangeListener {
    private User user;
    private LoginFragment loginFragment;
    private RegisterFragmentOne registerFragmentOne;
    private RegisterFragmentTwo registerFragmentTwo;
    private AppCompatImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (AppCompatImageView) findViewById(R.id.logo);
        logo.setAdjustViewBounds(true);

        loginFragment = new LoginFragment();
        registerFragmentOne = new RegisterFragmentOne();
        registerFragmentTwo = new RegisterFragmentTwo();

        getSupportFragmentManager().beginTransaction().add(R.id.container, loginFragment).commit();

        user = new User();
    }

    //TypefaceChangeListener implementation
    @Override
    public void changeTypeface(String fontName, TextView... views) {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontName);
        for(TextView view : views) {
            view.setTypeface(typeface);
        }
    }

    //UserManagementListener implementation

    @Override
    public void login(String username, String password) {
        //TODO: Implement login functionality
    }

    @Override
    public void saveUserInfo(String firstName, String lastName, String username) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
    }

    @Override
    public void registerUser(String password, String email) {
        user.setPassword(password);
        user.setEmail(email);
        user.register();
    }

    //OnFragmentChangeListener implementation

    @Override
    public void onFragmentChanged(Class fragment, boolean isRegisterProcess) {
        if(isRegisterProcess) {
            if (fragment.getName().equals(RegisterFragmentOne.class.getName())) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, registerFragmentTwo).commit();
            } else if (fragment.getName().equals(RegisterFragmentTwo.class.getName())) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
            }
        } else {
            if(fragment.getName().equals(LoginFragment.class.getName())) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, registerFragmentOne).commit();
            } else if(fragment.getName().equals(RegisterFragmentOne.class.getName())
                    || fragment.getName().equals(RegisterFragmentTwo.class.getName())) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
            }
        }
    }
}
