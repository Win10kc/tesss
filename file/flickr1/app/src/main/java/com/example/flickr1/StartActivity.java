package com.example.flickr1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flickr1.R;


public class StartActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        dbHelper = new DatabaseHelper(this);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Log the schema of each table in the database
        dbHelper.logTableSchemas();

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            // If user is logged in, redirect to MainActivity
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        } else {
            // If not logged in, show login and register buttons
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
            });

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                }
            });
        }
    }

    private boolean isUserLoggedIn() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",       // Table name
                new String[] {"id"},  // Columns to return
                "isLoggedIn = ?",  // WHERE clause
                new String[] {"1"}, // WHERE arguments (1 for true)
                null, // GROUP BY
                null, // HAVING
                null  // ORDER BY
        );

        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isLoggedIn;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
