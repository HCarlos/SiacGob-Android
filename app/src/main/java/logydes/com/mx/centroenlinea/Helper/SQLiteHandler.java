package logydes.com.mx.centroenlinea.Helper;

/**
 * Created by devch on 15/06/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

import logydes.com.mx.centroenlinea.Utils.Singleton;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_USER_LABEL = "label";
    private static final String KEY_USER_DATA = "data";
    private static final String KEY_USER_MSG = "msg";

    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_IDUSER = "iduser";
    private static final String KEY_USER_FULLNAME = "fullname";
    private static final String KEY_USER_DOMICILIO = "domicilio";
    private static final String KEY_USER_NUMCELL = "numcell";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_LABEL + " TEXT,"
                + KEY_USER_DATA + " TEXT UNIQUE, "
                + KEY_USER_USERNAME + " TEXT, "
                + KEY_USER_IDUSER + " TEXT, "
                + KEY_USER_MSG + " TEXT,"
                + KEY_USER_FULLNAME + " TEXT, "
                + KEY_USER_DOMICILIO + " TEXT, "
                + KEY_USER_NUMCELL + " TEXT "
                + ");";

        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
//    public void addUser(String label, String data, String msg, String created_at) {
    public void addUser(String label, String data, String msg, String fullname, String domicilio, String celular) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.d(TAG, "KEY_USER_IDUSER: " + data);

        values.put(KEY_USER_LABEL, label);
        values.put(KEY_USER_DATA, data);
        values.put(KEY_USER_MSG, msg);

        values.put(KEY_USER_USERNAME, label);
        values.put(KEY_USER_IDUSER, data);

        values.put(KEY_USER_FULLNAME, fullname);
        values.put(KEY_USER_DOMICILIO, domicilio);
        values.put(KEY_USER_NUMCELL, celular);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        try {
            HashMap<String, String> user = new HashMap<String, String>();
            String selectQuery = "SELECT  * FROM " + TABLE_USER;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // Move to first row
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {

                Log.w(TAG,"0: "+cursor.getString(0));
                Log.w(TAG,"1: "+cursor.getString(1));
                Log.w(TAG,"2: "+cursor.getString(2));
                Log.w(TAG,"3: "+cursor.getString(3));
                Log.w(TAG,"4: "+cursor.getString(4));
                Log.w(TAG,"5: "+cursor.getString(5));
                Log.w(TAG,"6: "+cursor.getString(6));
                Log.w(TAG,"7: "+cursor.getString(7));
                Log.w(TAG,"8: "+cursor.getString(8));

                user.put(KEY_USER_LABEL, cursor.getString(1));
                user.put(KEY_USER_DATA, cursor.getString(2));
                user.put(KEY_USER_MSG, cursor.getString(5));
                user.put(KEY_USER_USERNAME, cursor.getString(1));
                user.put(KEY_USER_IDUSER, cursor.getString(2) );
                user.put(KEY_USER_FULLNAME, cursor.getString(6));
                user.put(KEY_USER_DOMICILIO, cursor.getString(7));
                user.put(KEY_USER_NUMCELL, cursor.getString(8));

                int val = 1;
                if (user.get(KEY_ID) != null){
                    val = Integer.valueOf(user.get(KEY_ID));
                }

                Singleton singleton = new Singleton(
                        val,
                        0,
                        Integer.valueOf(user.get(KEY_USER_IDUSER)),
                        user.get(KEY_USER_USERNAME),
                        user.get(KEY_USER_FULLNAME),
                        user.get(KEY_USER_DOMICILIO),
                        user.get(KEY_USER_NUMCELL)
                );

            }
            Log.e(TAG, "Fetching user from Sqlite: " + user.toString());

            cursor.close();
            db.close();

            return user;

        }catch (SQLiteException e ){
            SQLiteDatabase db = this.getWritableDatabase();
            this.onUpgrade(db, 0, 0);
            return this.getUserDetails();


        }
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        onUpgrade(db, 0, 0);

        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void updateUsers() {

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_FULLNAME,Singleton.getFullName()); //These Fields should be your String values of actual column names
        cv.put(KEY_USER_DOMICILIO,Singleton.getDomicilio());
        cv.put(KEY_USER_NUMCELL,Singleton.getNumCell());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, cv, KEY_ID+"="+Singleton.getID(), null);

        db.close();

        Log.d(TAG, "update user info from sqlite");
    }

}