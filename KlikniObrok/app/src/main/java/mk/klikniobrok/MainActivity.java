package mk.klikniobrok;

import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import mk.klikniobrok.fragments.LoginFragment;
import mk.klikniobrok.fragments.RegisterFragmentOne;
import mk.klikniobrok.fragments.RegisterFragmentTwo;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;
import mk.klikniobrok.models.User;

import static android.support.v7.appcompat.R.styleable.View;

public class MainActivity extends AppCompatActivity implements TypefaceChangeListener, UserManagementListener, OnFragmentChangeListener {
    private User user;
    private LoginFragment loginFragment;
    private RegisterFragmentOne registerFragmentOne;
    private RegisterFragmentTwo registerFragmentTwo;
    private AppCompatImageView logo;
    private FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (AppCompatImageView) findViewById(R.id.logo);
        logo.setAdjustViewBounds(true);

        container = (FrameLayout) findViewById(R.id.container);
        container.setVisibility(android.view.View.INVISIBLE);

        splashScreenAnimation(logo);
        inOpacityAnimation(container);

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

    public void splashScreenAnimation(AppCompatImageView view) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        TranslateAnimation animation = new TranslateAnimation(
                view.getX(),
                view.getX(),
                (height - view.getHeight()) / 4,
                view.getY());
        animation.setDuration(750);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setStartOffset(1000);
        view.startAnimation(animation);
    }

    public void inOpacityAnimation(View view) {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(750);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setStartOffset(1200);
        view.startAnimation(animation);
        view.setVisibility(android.view.View.VISIBLE);
    }
}
