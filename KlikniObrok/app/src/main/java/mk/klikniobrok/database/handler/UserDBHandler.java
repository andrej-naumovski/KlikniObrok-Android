package mk.klikniobrok.database.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import java.util.Date;

import mk.klikniobrok.database.model.UserDB;

/**
 * Created by gjorgjim on 1/17/17.
 */

public class UserDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RESTAURANT = "restaurantName";
    public static final String COLUMN_TABLE_ID = "tableid";

    public UserDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TOKEN + " TEXT, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_RESTAURANT + " TEXT, " +
                COLUMN_TABLE_ID + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void addUserToDB(String token) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, token);
        Date date = new Date();
        long time = date.getTime();
        values.put(COLUMN_TIME, time);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addUserToDB(String token, String restaurantName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, token);
        Date date = new Date();
        long time = date.getTime();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_RESTAURANT, restaurantName);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addUserToDB(String token, String restaurantName, String tableid) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, token);
        Date date = new Date();
        long time = date.getTime();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_RESTAURANT, restaurantName);
        values.put(COLUMN_TABLE_ID, tableid);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public UserDB getUserDB(String token) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_TOKEN)) != null) {
                if(c.getString(c.getColumnIndex(COLUMN_TOKEN)).equals(token)) {
                    db.close();
                    return new UserDB(c.getString(c.getColumnIndex(COLUMN_TOKEN)),
                            c.getLong(c.getColumnIndex(COLUMN_TIME)), c.getString(c.getColumnIndex(COLUMN_RESTAURANT)),
                            c.getString(c.getColumnIndex(COLUMN_TABLE_ID)));
                }
            }
        }
        c.close();
        db.close();
        return null;
    }

    public UserDB getUserDB() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1";

        Cursor c = db.rawQuery(query, null);;
        if (c.moveToFirst()) {
            if(c.getString(c.getColumnIndex(COLUMN_TOKEN)) != null) {
                db.close();
                return new UserDB(c.getString(c.getColumnIndex(COLUMN_TOKEN)),
                        c.getLong(c.getColumnIndex(COLUMN_TIME)), c.getString(c.getColumnIndex(COLUMN_RESTAURANT)),
                        c.getString(c.getColumnIndex(COLUMN_TABLE_ID)));

            }
            c.close();
            db.close();
            return null;
        }
        c.close();
        db.close();
        return null;
        }

    public void updateUserDB(String token) {
        deleteUserDB(token);
        addUserToDB(token);
    }

    public void updateUserDB(String token,String restaurantName) {
        deleteUserDB(token);
        addUserToDB(token, restaurantName);
    }

    public void updateUserDB(String token, String restaurantName, String tableid) {
        deleteUserDB(token);
        addUserToDB(token, restaurantName, tableid);
    }

    public void deleteUserDB(String token) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_TOKEN + "=\"" + token + "\";";
        db.execSQL(deleteQuery);
    }
}
