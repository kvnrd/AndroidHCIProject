package hci.geeks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsersDatabaseHelper extends SQLiteOpenHelper {

    // Variables for Database
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "userssDB";
    private static final String USERS_TABLE = "users";

    // Table columns
    private static final String KEY_ID = "_id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    // Database Constructor
    public UsersDatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    // Creates table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+USERS_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,firstname TEXT,lastname TEXT,email TEXT,username TEXT,password TEXT)");
    }

    // Upgrades table
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(database);
    }

    // Insert item to the database. Returns if it was successful or not
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_LASTNAME, user.getLastName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        long id = db.insert(USERS_TABLE, null, values);
        user.setId((int) id);
        db.close();

        if(id == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // Returns all items that are in the database
    public ArrayList<User> allUsers(){
        ArrayList<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + USERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String firstname = cursor.getString(1);
                String lastname = cursor.getString(2);
                String email = cursor.getString(3);
                String username = cursor.getString(4);
                String password = cursor.getString(5);
                User user = new User(id, firstname, lastname, email, username, password);
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    // Deletes all items in the database
    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS_TABLE, null, new String[]{});
        db.close();
    }

    // Deletes one item in the database
    public void deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS_TABLE, KEY_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    // Updates an item from the database
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_LASTNAME, user.getLastName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        db.update(USERS_TABLE, values,KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username, String password){
        boolean log = false;
        String user;
        String pass;
        String selectQuery = "SELECT * FROM " + USERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                user = cursor.getString(4);
                pass = cursor.getString(5);
                if(username.equals(user) && password.equals(pass)){
                    log = true;
                    break;
                }
            } while (cursor.moveToNext());
        }
        return log;
    }
}
