package hci.geeks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GroupsDatabaseHelper extends SQLiteOpenHelper {

    // Variables for Database
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "groupsDB";
    private static final String GROUPS_TABLE = "groups";

    // Table columns
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_PRIVACY = "privacy";
    private static final String KEY_MEMBERS = "members";


    // Database Constructor
    public GroupsDatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    // Creates table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+GROUPS_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,subject TEXT,privacy TEXT,members INTEGER)");
    }

    // Upgrades table
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + GROUPS_TABLE);
        onCreate(database);
    }

    // Insert item to the database. Returns if it was successful or not
    public boolean addGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());
        values.put(KEY_SUBJECT, group.getSubject());
        values.put(KEY_PRIVACY, group.getPrivacy());
        values.put(KEY_MEMBERS, group.getMembers());
        long id = db.insert(GROUPS_TABLE, null, values);
        group.setId((int) id);
        db.close();

        if(id == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // Returns all items that are in the database
    public ArrayList<Group> allGroups(){
        ArrayList<Group> groups = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + GROUPS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String subject = cursor.getString(2);
                String privacy = cursor.getString(3);
                int members = cursor.getInt(4);
                Group group = new Group(id, name, subject, privacy, members);
                groups.add(group);
            } while (cursor.moveToNext());
        }
        return groups;
    }

    // Deletes all items in the database
    public void deleteGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GROUPS_TABLE, null, new String[]{});
        db.close();
    }

    // Deletes one item in the database
    public void deleteGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GROUPS_TABLE, KEY_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    // Updates an item from the database
    public void updateGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());
        values.put(KEY_SUBJECT, group.getSubject());
        values.put(KEY_PRIVACY, group.getPrivacy());
        values.put(KEY_MEMBERS, group.getMembers());
        db.update(GROUPS_TABLE, values,KEY_ID + " = ?", new String[]{String.valueOf(group.getId())});
        db.close();
    }
}
