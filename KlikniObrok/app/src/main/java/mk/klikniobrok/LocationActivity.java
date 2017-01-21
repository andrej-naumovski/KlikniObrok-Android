package mk.klikniobrok;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import mk.klikniobrok.database.handler.DBHandler;
import mk.klikniobrok.database.model.UserDB;
import mk.klikniobrok.fragments.ProgressBarFragment;
import mk.klikniobrok.fragments.YourLocationFragment;
import mk.klikniobrok.fragments.listeners.LocationManagerListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.models.Restaurant;
import mk.klikniobrok.services.Data;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LocationActivity extends AppCompatActivity implements TypefaceChangeListener, LocationManagerListener {

    private ProgressBarFragment progressBarFragment;
    private YourLocationFragment yourLocationFragment;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private List<Restaurant> array;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler(this, null, null, 1);
        if(isInRestaurant()) {
            Intent intent = new Intent(LocationActivity.this, RestaurantActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_location);


        progressBarFragment = new ProgressBarFragment();
        yourLocationFragment = new YourLocationFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, progressBarFragment).commit();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    @Override
    public void changeTypeface(String fontName, TextView... views) {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontName);
        for(TextView view : views) {
            view.setTypeface(typeface);
        }
    }

    @Override
    public void findCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                }, 10);
            }
            return;
        } else {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            manageLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 10) {
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                manageLocation();
            }
        }
    }

    public void manageLocation() {
        if(currentLocation != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, yourLocationFragment).commitAllowingStateLoss();
        } else {
            Toast.makeText(this, "Локацијата не може да се добие", Toast.LENGTH_SHORT).show();
            findCurrentLocation();
        }

    }

    public List<Restaurant> getRestaurants() {
        array = Data.getRestaurantList(currentLocation);
        return array;
    }

    @Override
    public void onResume() {
        Date currentDate = new Date();
        if(currentDate.getTime() - dbHandler.getUserDB().getTime() > 10800000) {
            dbHandler.deleteUserDB(dbHandler.getUserDB().getToken());
            startActivity(new Intent(LocationActivity.this, MainActivity.class));
        }
        //TODO: Check if he is in a restaurant
        super.onResume();
    }

    public void restaurantActivity(String restaurantName) {
        UserDB userdb = dbHandler.getUserDB();
        userdb.setRestaurantName(restaurantName);
        dbHandler.updateUserDB(userdb.getToken(), restaurantName);
        //TODO: Implement QR Code scan.
        Intent intent = new Intent(LocationActivity.this, RestaurantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public boolean isInRestaurant() {
        if(dbHandler.getUserDB().getRestaurantName() != null) {
            return true;
        } else return false;
    }
}
