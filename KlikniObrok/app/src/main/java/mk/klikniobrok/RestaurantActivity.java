package mk.klikniobrok;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mk.klikniobrok.database.handler.RestaurantDetailsHandler;
import mk.klikniobrok.database.handler.UserDBHandler;
import mk.klikniobrok.database.handler.UserDetailsHandler;
import mk.klikniobrok.fragments.MenuFragment;
import mk.klikniobrok.fragments.OrderFragment;
import mk.klikniobrok.fragments.SubMenuFragment;
import mk.klikniobrok.fragments.listeners.OnItemClickListener;
import mk.klikniobrok.fragments.listeners.RestaurantMenuListener;
import mk.klikniobrok.fragments.listeners.SubMenuEntriesListener;
import mk.klikniobrok.models.Entry;
import mk.klikniobrok.models.EntryWrapper;
import mk.klikniobrok.services.RestaurantService;
import mk.klikniobrok.services.impl.RestaurantServiceImpl;

public class RestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClickListener, RestaurantMenuListener, SubMenuEntriesListener {

    private MenuFragment menuFragment = null;
    private OrderFragment orderFragment = null;
    private SubMenuFragment subMenuFragment = null;
    private UserDBHandler userDbHandler;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private RestaurantDetailsHandler restaurantDetailsHandler;
    private UserDetailsHandler userDetailsHandler;
    private RestaurantService restaurantService;
    private com.shawnlin.numberpicker.NumberPicker numberPicker;
    private View numberPickerView;
    private AlertDialog.Builder dialog;
    private AlertDialog alert;

    private List<String> entryTypes;
    private HashMap<String, List<Entry>> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(this.getString(R.string.menu));
        setSupportActionBar(toolbar);


        numberPickerView = getLayoutInflater().inflate(R.layout.number_picker_layout, null);
        numberPicker = (com.shawnlin.numberpicker.NumberPicker) numberPickerView.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);

        numberPicker.setDividerColor(getResources().getColor(R.color.colorPrimary));
        numberPicker.setDividerColorResource(R.color.colorPrimary);

        numberPicker.setTextColor(getResources().getColor(R.color.colorPrimary));
        numberPicker.setTextColorResource(R.color.colorPrimary);


        dialog = new AlertDialog.Builder(RestaurantActivity.this);
        dialog.setView(numberPickerView);
        dialog.setTitle("Одбери количина");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert = dialog.create();

        menuFragment = new MenuFragment();
        orderFragment = new OrderFragment();
        subMenuFragment = new SubMenuFragment();

        userDbHandler = new UserDBHandler(this, null, null, 1);
        restaurantDetailsHandler = new RestaurantDetailsHandler(this, null, null, 1);
        userDetailsHandler = new UserDetailsHandler(this, null, null, 1);

        restaurantService = new RestaurantServiceImpl();

        entries = new HashMap<>();

        if(entryTypes == null) {
            new GetEntryTypes().execute(userDbHandler.getUserDB().getToken());
        } else {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
            navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_restaurant);
        AppCompatTextView userName = (AppCompatTextView) headerLayout.findViewById(R.id.navHeaderUserNameTextView);
        AppCompatTextView restaurantName = (AppCompatTextView) headerLayout.findViewById(R.id.navHeaderRestaurantNameTextView);
        AppCompatImageView profilePicture = (AppCompatImageView) headerLayout.findViewById(R.id.profilepicture);
        userName.setText(userDetailsHandler.getUserDetails().getFirstName() + " " + userDetailsHandler.getUserDetails().getLastName());
        restaurantName.setText(restaurantDetailsHandler.getRestaurantDetails().getName());
        Glide.with(this).load(R.drawable.noprofilepicture_klikniobrok).into(profilePicture);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    if(subMenuFragment.isVisible()) {
                        onNavigationItemSelected(navigationView.getMenu().getItem(0));
                        navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
                        toolbar.setNavigationIcon(R.drawable.ic_menu_white_48dp);
                    } else {
                        Log.d("open", "drawer");
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(subMenuFragment.isVisible()) {
                subMenuFragment.onDestroy();
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
                navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
                toolbar.setNavigationIcon(R.drawable.ic_menu_white_48dp);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu) {
            toolbar.setTitle(this.getString(R.string.menu));
            menuFragment = new MenuFragment();
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_restaurant, menuFragment).commit();
        } else if (id == R.id.order) {
            toolbar.setTitle(this.getString(R.string.order));
            orderFragment = new OrderFragment();
            menuFragment = new MenuFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_restaurant, orderFragment).commit();
        } else if (id == R.id.callEmployee) {

        } else if (id == R.id.logOut) {
            userDbHandler.deleteUserDB(userDbHandler.getUserDB().getToken());
            restaurantDetailsHandler.deleteRestaurantDetails();
            userDetailsHandler.removeUserDetails();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(RestaurantActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        Date currentDate = new Date();
        if(currentDate.getTime() - userDbHandler.getUserDB().getTime() > 10800000) {
            userDbHandler.deleteUserDB(userDbHandler.getUserDB().getToken());
            restaurantDetailsHandler.deleteRestaurantDetails();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(RestaurantActivity.this, MainActivity.class));
        }
        if(restaurantDetailsHandler.getRestaurantDetails() != null) {
            if(entryTypes == null) {
                new GetEntryTypes().execute(userDbHandler.getUserDB().getToken());
            } else {
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
                navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
            }
        }
        super.onResume();
    }

    @Override
    public void onItemClick(String key) {
        new GetEntriesByType().execute(key);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public List<String> getEntryTypes() {
        return entryTypes;
    }

    @Override
    public List<Entry> getEntriesByType(String type) {
        return entries.get(type);
    }

    class GetEntryTypes extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            return restaurantService.getRestaurantEntryTypes(params[0], restaurantDetailsHandler.getRestaurantDetails());
        }

        @Override
        protected void onPostExecute(List<String> s) {
            if(s == null) {
                new GetEntryTypes().execute(userDbHandler.getUserDB().getToken());
            } else {
                entryTypes = s;
                menuFragment.addMenuItems();
            }
        }
    }

    class GetEntriesByType extends AsyncTask<String, Void, List<Entry>> {
        @Override
        protected List<Entry> doInBackground(String... params) {
            return restaurantService.getEntriesByType(userDbHandler.getUserDB().getToken(), restaurantDetailsHandler.getRestaurantDetails(), params[0]);
        }

        @Override
        protected void onPostExecute(List<Entry> entriesByType) {
            entries.put(entriesByType.get(0).getEntryType().toString(), entriesByType);
            Bundle bundle = new Bundle();
            Log.d("entries", entries.get(entriesByType.get(0).getEntryType().toString()).toString());
            bundle.putString("type", entriesByType.get(0).getEntryType().toString());
            subMenuFragment.setArguments(bundle);
            RestaurantActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_restaurant, subMenuFragment).commit();
            toolbar.setTitle(entriesByType.get(0).getEntryType().toString());
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public void onSubMenuEntryClick(Entry entry) {
        numberPicker.setValue(1);
        alert.show();
    }
}
