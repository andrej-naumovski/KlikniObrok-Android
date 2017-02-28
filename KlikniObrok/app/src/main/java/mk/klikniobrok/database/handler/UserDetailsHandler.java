package mk.klikniobrok.database.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mk.klikniobrok.models.User;

/**
 * Created by gjorgjim on 2/19/17.
 */

public class UserDetailsHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userdetails.db";
    public static final String TABLE_USER_DETAILS = "user_details";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public UserDetailsHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_USER_DETAILS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    public void addUserDetailsToDB(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER_DETAILS, null, values);
        db.close();
    }

    public User getUserDetails() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER_DETAILS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        User user = new User();
        user.setFirstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
        user.setLastName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
        user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
        user.setUsername(c.getString(c.getColumnIndex(COLUMN_USERNAME)));

        c.close();
        return user;
    }

    public void removeUserDetails() {
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_USER_DETAILS + " WHERE 1";
        db.execSQL(deleteQuery);
    }
}
