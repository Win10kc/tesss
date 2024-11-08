package com.example.flickr1;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickr1.Adapter.PostAdapter;
import com.example.flickr1.Fragment.HomeFragment;
import com.example.flickr1.Fragment.NotificationFragment;
import com.example.flickr1.Fragment.PostDetailFragment;
import com.example.flickr1.Fragment.ProfileFragment;
import com.example.flickr1.Fragment.SearchFragment;
import com.example.flickr1.Model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private DatabaseHelper dbHelper;
    private Button addPostButton;
    private static final int REQUEST_CODE_ADD_POST = 100; // You can use any integer value here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        addPostButton = findViewById(R.id.addPostButton);

        // Configure RecyclerView and adapter
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

        loadPosts();

        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_POST);
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            Bundle bundle = new Bundle();

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
                bundle.putString("userId", "current_user_id"); // Replace with actual user ID
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new PostDetailFragment();
                bundle.putString("postId", "example_post_id"); // Replace with actual post ID
            } else if (itemId == R.id.nav_heart) {
                selectedFragment = new NotificationFragment();
            }

            if (selectedFragment != null) {
                selectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true;
        });

        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }


    private void loadPosts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("posts", null, null, null, null, null, "id DESC");

        postList.clear();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String postImageUrl = cursor.getString(cursor.getColumnIndexOrThrow("postImageUrl")); // Load image URL

                Post post = new Post(String.valueOf(id), title, content, postImageUrl);

                postList.add(post);
            }
            cursor.close();
        }
        postAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_POST && resultCode == RESULT_OK) {
            loadPosts(); // Refresh posts after a new post is added
        }
    }


}
