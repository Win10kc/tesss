package com.example.flickr1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flickr1_database.db";
    private static final int DATABASE_VERSION = 5;

    // User Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_IS_LOGGED_IN = "isLoggedIn"; // New column

    // Post Table
    private static final String TABLE_POSTS = "posts";
    private static final String COLUMN_POST_ID = "post_id";
    private static final String COLUMN_POST_TITLE = "title";
    private static final String COLUMN_POST_DESCRIPTION = "description";

    // Follower Table
    private static final String TABLE_FOLLOWERS = "followers";
    private static final String COLUMN_FOLLOWER_ID = "follower_id";

    // Notification Table
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_NOTIFICATION_ID = "notification_id";
    private static final String COLUMN_NOTIFICATION_MESSAGE = "message";
    private static final String COLUMN_NOTIFICATION_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String COLUMN_USER_BIO = "bio";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_USERNAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_BIO + " TEXT, " + // Add bio column here
                COLUMN_USER_IS_LOGGED_IN + " INTEGER DEFAULT 0)";

        String createPostsTable = "CREATE TABLE " + TABLE_POSTS + " (" +
                COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " + // Link each post to a user
                COLUMN_POST_TITLE + " TEXT, " +
                COLUMN_POST_DESCRIPTION + " TEXT)";


        String createFollowersTable = "CREATE TABLE " + TABLE_FOLLOWERS + " (" +
                COLUMN_FOLLOWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER)";

        String createNotificationsTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTIFICATION_MESSAGE + " TEXT, " +
                COLUMN_NOTIFICATION_TIMESTAMP + " TEXT)";

        db.execSQL(createUsersTable);
        db.execSQL(createPostsTable);
        db.execSQL(createFollowersTable);
        db.execSQL(createNotificationsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
            db.execSQL("ALTER TABLE posts ADD COLUMN userId TEXT");
            onCreate(db);
        }
    }

    // Method to check user credentials
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    // Method to insert a new post
    public long insertPost(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POST_TITLE, title);
        values.put(COLUMN_POST_DESCRIPTION, description);
        return db.insert(TABLE_POSTS, null, values);
    }

    // Method to retrieve all posts
    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("posts", null, null, null, null, null, null);
    }


    // Method to retrieve a single post by ID
    public Cursor getPostById(String postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_POSTS, null, COLUMN_POST_ID + "=?", new String[]{postId}, null, null, null);
    }



    // Method to count followers of a user
    public int getFollowersCount(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FOLLOWERS + " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    // Method to count following users
    public int getFollowingCount(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FOLLOWERS + " WHERE " + COLUMN_FOLLOWER_ID + " = ?", new String[]{userId});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    // Method to retrieve all notifications
    public Cursor getAllNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NOTIFICATIONS, null, null, null, null, null, COLUMN_NOTIFICATION_TIMESTAMP + " DESC");
    }

    // Method to search users by name
    public Cursor searchUsersByName(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, COLUMN_USER_EMAIL + " LIKE ?", new String[]{"%" + query + "%"}, null, null, null);
    }

    // Method to get user by ID
    public Cursor getUserById(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, COLUMN_USER_ID + "=?", new String[]{userId}, null, null, null);
    }

    private static final String CREATE_POSTS_TABLE = "CREATE TABLE posts (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId TEXT, " +
            "description TEXT, " +
            "imageUrl TEXT, " +
            "date TEXT)";



    public int getPostCountByUserId(String userId) {
        int postCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM posts WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor != null && cursor.moveToFirst()) {
            postCount = cursor.getInt(0);
            cursor.close();
        }
        return postCount;
    }

    public void updateLoginStatus(String email, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isLoggedIn", status ? 1 : 0);
        db.update("users", values, "email = ?", new String[]{email});
    }

    public void logTableSchemas() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] tables = {TABLE_USERS, TABLE_POSTS, TABLE_FOLLOWERS, TABLE_NOTIFICATIONS};

        for (String table : tables) {
            Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
            Log.d("Database Schema", "Schema for table: " + table);

            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String columnType = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                int notNull = cursor.getInt(cursor.getColumnIndexOrThrow("notnull"));
                String defaultValue = cursor.getString(cursor.getColumnIndexOrThrow("dflt_value"));
                Log.d("Database Schema", "Column: " + columnName + ", Type: " + columnType +
                        ", Not Null: " + notNull + ", Default: " + defaultValue);
            }
            cursor.close();
        }
    }









}
