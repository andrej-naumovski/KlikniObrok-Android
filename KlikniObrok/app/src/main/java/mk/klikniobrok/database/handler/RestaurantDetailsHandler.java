package mk.klikniobrok.database.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mk.klikniobrok.models.Address;
import mk.klikniobrok.models.LatLng;
import mk.klikniobrok.models.Restaurant;

/**
 * Created by gjorgjim on 2/19/17.
 */

public class RestaurantDetailsHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurantdetails.db";
    private static final String TABLE_RESTAURANT_DETAILS = "restaurant_details";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
    public static final String COLUMN_RESTAURANT_PHONE = "restaurant_phone";
    public static final String COLUMN_RESTAURANT_EMAIL = "restaurant_email";
    public static final String COLUMN_RESTAURANT_ADDRESS = "restaurant_address";
    public static final String COLUMN_RESTAURANT_LONGITUDE = "restaurant_longitude";
    public static final String COLUMN_RESTAURANT_LATITUDE = "restaurant_latitude";
    public RestaurantDetailsHandler(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_DETAILS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_RESTAURANT_DETAILS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RESTAURANT_ID + " INTEGER, " +
                COLUMN_RESTAURANT_NAME + " TEXT, " +
                COLUMN_RESTAURANT_PHONE + " TEXT, " +
                COLUMN_RESTAURANT_EMAIL + " TEXT, " +
                COLUMN_RESTAURANT_ADDRESS + " TEXT, " +
                COLUMN_RESTAURANT_LONGITUDE + " DOUBLE, " +
                COLUMN_RESTAURANT_LATITUDE + " DOUBLE" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    public void addRestaurantDetailsToDB(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_ADDRESS, restaurant.getAddress().getName());
        values.put(COLUMN_RESTAURANT_NAME, restaurant.getName());
        values.put(COLUMN_RESTAURANT_EMAIL, restaurant.getEmail());
        values.put(COLUMN_RESTAURANT_ID, restaurant.getId());
        values.put(COLUMN_RESTAURANT_PHONE, restaurant.getPhone());
        values.put(COLUMN_RESTAURANT_LATITUDE, restaurant.getLocation().getLatitude());
        values.put(COLUMN_RESTAURANT_LONGITUDE, restaurant.getLocation().getLongitude());
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_RESTAURANT_DETAILS, null, values);
        database.close();
    }

    public Restaurant getRestaurantDetails() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RESTAURANT_DETAILS + " WHERE 1";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Restaurant restaurant = new Restaurant(cursor.getInt(cursor.getColumnIndex(COLUMN_RESTAURANT_ID)),
                                            cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_NAME)),
                                            cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_PHONE)),
                                            cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_EMAIL)),
                    new Address(cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_ADDRESS)), null, null, null, 0),
                    new LatLng(cursor.getDouble(cursor.getColumnIndex(COLUMN_RESTAURANT_LATITUDE)),
                                cursor.getDouble(cursor.getColumnIndex(COLUMN_RESTAURANT_LONGITUDE))));
        cursor.close();
        return restaurant;
    }

    public int getRestaurantID() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RESTAURANT_DETAILS + " WHERE 1";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_RESTAURANT_ID));
        cursor.close();
        return id;
    }

    public void deleteRestaurantDetails() {
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_RESTAURANT_DETAILS + " WHERE 1";
        db.execSQL(deleteQuery);
    }
}
