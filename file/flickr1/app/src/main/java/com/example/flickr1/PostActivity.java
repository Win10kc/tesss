package com.example.flickr1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private DatabaseHelper dbHelper;
    private EditText titleEditText, descriptionEditText;
    private ImageView imageAdded;
    private TextView postButton, closeButton;
    private Bitmap postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        dbHelper = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.title);
        descriptionEditText = findViewById(R.id.description);
        imageAdded = findViewById(R.id.image_added);
        postButton = findViewById(R.id.post);
        closeButton = findViewById(R.id.close);

        // Capture or select an image
        imageAdded.setOnClickListener(v -> openCamera());

        postButton.setOnClickListener(v -> savePost());

        closeButton.setOnClickListener(v -> finish());
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            postImage = (Bitmap) extras.get("data");
            imageAdded.setImageBitmap(postImage);
        }
    }

    private void savePost() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || postImage == null) {
            if (title.isEmpty()) titleEditText.setError("Title required");
            if (description.isEmpty()) descriptionEditText.setError("Description required");
            if (postImage == null)
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("image", DbBitmapUtility.getBytes(postImage)); // Convert bitmap to byte array

        long result = db.insert("posts", null, values);
        if (result != -1) {
            Toast.makeText(this, "Post saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save post", Toast.LENGTH_SHORT).show();
        }
    }
}
