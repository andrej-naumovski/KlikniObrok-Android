package mk.klikniobrok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import id.zelory.compressor.Compressor;
import mk.klikniobrok.database.handler.UserDBHandler;
import mk.klikniobrok.fragments.LoginFragment;
import mk.klikniobrok.fragments.RegisterFragmentOne;
import mk.klikniobrok.fragments.RegisterFragmentTwo;
import mk.klikniobrok.fragments.FacebookLoginFragment;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;
import mk.klikniobrok.models.Role;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.impl.AuthenticationServiceImpl;

public class MainActivity extends AppCompatActivity implements TypefaceChangeListener, UserManagementListener, OnFragmentChangeListener {
    private User user;
    private LoginFragment loginFragment;
    private RegisterFragmentOne registerFragmentOne;
    private RegisterFragmentTwo registerFragmentTwo;
    private FacebookLoginFragment facebookLoginFragment;
    private AppCompatImageView logo;
    private FrameLayout container;
    private AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private UserDBHandler userDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDbHandler = new UserDBHandler(this, null, null, 1);
        if(isLoggedIn()) {
            if(isInRestaurant()) {
                Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        setContentView(R.layout.activity_main);


        logo = (AppCompatImageView) findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.logo).into(logo);
        logo.setAdjustViewBounds(true);

        container = (FrameLayout) findViewById(R.id.container);
        container.setVisibility(android.view.View.INVISIBLE);

        splashScreenAnimation(logo);
        inOpacityAnimation(container);

        loginFragment = new LoginFragment();
        registerFragmentOne = new RegisterFragmentOne();
        registerFragmentTwo = new RegisterFragmentTwo();
        facebookLoginFragment = new FacebookLoginFragment();

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
    public void loginUser(String username, String password) {
        new Login().execute(username, password);
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
        user.setRole(Role.CUSTOMER);
        user.setEnabled(1);
        new Register().execute(user);
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

    class Login extends AsyncTask<String, Void, String> {
        private ProgressDialog spinner = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPostExecute(String s) {
            spinner.dismiss();
            JSONObject tokenObject;
            try {
                tokenObject = new JSONObject(s);
                String status = tokenObject.getString("responseStatus");
                if(status.equalsIgnoreCase("SUCCESS")) {
                    String token = tokenObject.getString("token");
                    userDbHandler.addUserToDB(token);
                    Intent locationActivity = new Intent(MainActivity.this, LocationActivity.class);
                    locationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(locationActivity);
                } else {
                    AlertDialog errorDialog = new AlertDialog.Builder(MainActivity.this).create();
                    errorDialog.setMessage("Грешна комбинација на корисничко име/лозинка, обидете се повторно.");
                    errorDialog.show();
                }
            } catch (JSONException e) {
                AlertDialog errorDialog = new AlertDialog.Builder(MainActivity.this).create();
                errorDialog.setMessage("Не може да се воспостави конекција со серверот, Ве молиме обидете се повторно.");
                errorDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            return authenticationService.login(strings[0], strings[1]);
        }

        @Override
        protected void onPreExecute() {
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.loginProgress));
            spinner.show();
        }
    }

    class Register extends AsyncTask<User, Void, String> {
        private ProgressDialog spinner = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.registerProgress));
            spinner.show();
        }

        @Override
        protected void onPostExecute(String s) {
            spinner.dismiss();

        }

        @Override
        protected String doInBackground(User... users) {
            return authenticationService.register(users[0]);
        }
    }

    class LoginFb extends AsyncTask<String, Void, String> {
        private ProgressDialog spinner = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.registerProgress));
            spinner.show();
        }

        @Override
        protected void onPostExecute(String s) {
            spinner.dismiss();
            JSONObject tokenObject;
            Log.d("fb register", s);
            try {
                tokenObject = new JSONObject(s);
                String status = tokenObject.getString("responseStatus");
                if(status.equalsIgnoreCase("SUCCESS")) {
                    String token = tokenObject.getString("token");
                    userDbHandler.addUserToDB(token);
                    Intent locationActivity = new Intent(MainActivity.this, LocationActivity.class);
                    locationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(locationActivity);
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, facebookLoginFragment).commit();
                }
            } catch (JSONException e) {
                LoginManager.getInstance().logOut();
                AlertDialog errorDialog = new AlertDialog.Builder(MainActivity.this).create();
                errorDialog.setMessage("Не може да се воспостави конекција со серверот, Ве молиме обидете се повторно.");
                errorDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            return authenticationService.fbLogin(strings[0], strings[1]);
        }
    }

    class RegisterFb extends AsyncTask<User, Void, String> {
        private ProgressDialog spinner = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.registerProgress));
            spinner.show();
        }

        @Override
        protected void onPostExecute(String s) {
            spinner.dismiss();
            Log.d("fb register", s);
            new Login().execute(user.getUsername(), user.getPassword());
        }

        @Override
        protected String doInBackground(User... users) {
            return authenticationService.register(users[0]);
        }
    }

    public boolean isLoggedIn() {
        if(userDbHandler.getUserDB() != null) {
            Date date = new Date();
            if(date.getTime() - userDbHandler.getUserDB().getTime() < 10800000) {
                return true;
            } else {
                if(LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut();
                }
                userDbHandler.deleteUserDB(userDbHandler.getUserDB().getToken());
                return false;
            }
        }
        return false;
    }

    public boolean isInRestaurant() {
        if(userDbHandler.getUserDB().getRestaurantName() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void registerFbUser(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(1);
        user.setRole(Role.CUSTOMER);
        user.setDateCreated(new Date());
        user.setLastUsed(new Date());
        new RegisterFb().execute(user);
    }

    @Override
    public void loginFbUser(User user) {
        this.user = user;
        new LoginFb().execute(user.getEmail(), "fb");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(logo);
    }
}
