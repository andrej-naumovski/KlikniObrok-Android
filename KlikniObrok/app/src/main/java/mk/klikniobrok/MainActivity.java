package mk.klikniobrok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import mk.klikniobrok.database.handler.DBHandler;
import mk.klikniobrok.fragments.LoginFragment;
import mk.klikniobrok.fragments.RegisterFragmentOne;
import mk.klikniobrok.fragments.RegisterFragmentTwo;
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
    private AppCompatImageView logo;
    private FrameLayout container;
    private AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler(this, null, null, 1);
        if(isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());


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
                    dbHandler.addUserToDB(token);
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
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.loginProgress));
            spinner.show();
        }
    }

    class Register extends AsyncTask<User, Void, String> {
        private ProgressDialog spinner = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            spinner.setIndeterminate(true);
            spinner.setMessage(MainActivity.this.getResources().getString(R.string.registerProgress));
            spinner.show();
        }

        @Override
        protected void onPostExecute(String s) {
            spinner.dismiss();
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setMessage(s);
            alert.show();
        }

        @Override
        protected String doInBackground(User... users) {
            return authenticationService.register(users[0]);
        }
    }

    public boolean isLoggedIn() {
        if(dbHandler.getUserDB() != null) {
            Date date = new Date();
            if(date.getTime() - dbHandler.getUserDB().getTime() < 10800000) {
                return true;
            } else {
                dbHandler.deleteUserDB(dbHandler.getUserDB().getToken());
                return false;
            }

        }
        return false;
    }
}
