package mk.klikniobrok;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import mk.klikniobrok.database.handler.DBHandler;
import mk.klikniobrok.fragments.MenuFragment;
import mk.klikniobrok.fragments.OrderFragment;
import mk.klikniobrok.models.Restaurant;

public class RestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MenuFragment menuFragment = null;
    private OrderFragment orderFragment = null;
    private DBHandler dbHandler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(this.getString(R.string.menu));
        setSupportActionBar(toolbar);


        dbHandler = new DBHandler(this, null, null, 1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());

        View headerView = navigationView.getHeaderView(0);
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_restaurant);
        AppCompatTextView tokenNavHeader = (AppCompatTextView) headerLayout.findViewById(R.id.navHeaderTokenTextView);
        tokenNavHeader.setText(dbHandler.getUserDB().getRestaurantName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            dbHandler.deleteUserDB(dbHandler.getUserDB().getToken());
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
        if(currentDate.getTime() - dbHandler.getUserDB().getTime() > 10800000) {
            dbHandler.deleteUserDB(dbHandler.getUserDB().getToken());
            startActivity(new Intent(RestaurantActivity.this, MainActivity.class));
        }
        //TODO: Check if he is in a restaurant
        super.onResume();
    }
}
