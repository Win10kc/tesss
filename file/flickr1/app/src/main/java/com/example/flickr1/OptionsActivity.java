package com.example.flickr1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flickr1.R;

public class OptionsActivity extends AppCompatActivity {

    private EditText optionNameEditText, optionValueEditText;
    private Button saveButton, loadButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Initialize views
        optionNameEditText = findViewById(R.id.option_name);
        optionValueEditText = findViewById(R.id.option_value);
        saveButton = findViewById(R.id.save_button);
        loadButton = findViewById(R.id.load_button);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set up save button to insert a new option into the database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOption();
            }
        });

        // Set up load button to retrieve an option from the database
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOption();
            }
        });
    }

    private void saveOption() {
        String optionName = optionNameEditText.getText().toString();
        String optionValue = optionValueEditText.getText().toString();

        if (optionName.isEmpty() || optionValue.isEmpty()) {
            Toast.makeText(this, "Please enter both name and value.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", optionName);
        values.put("value", optionValue);

        long newRowId = db.insert("options", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Option saved!", Toast.LENGTH_SHORT).show();
            optionNameEditText.setText("");
            optionValueEditText.setText("");
        } else {
            Toast.makeText(this, "Error saving option.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void loadOption() {
        String optionName = optionNameEditText.getText().toString();

        if (optionName.isEmpty()) {
            Toast.makeText(this, "Please enter an option name.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"value"};
        String selection = "name = ?";
        String[] selectionArgs = {optionName};

        Cursor cursor = db.query("options", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String optionValue = cursor.getString(cursor.getColumnIndexOrThrow("value"));
            optionValueEditText.setText(optionValue);
            Toast.makeText(this, "Option loaded!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Option not found.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
}
