package com.example.flickr1.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flickr1.DatabaseHelper;
import com.example.flickr1.R;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView username, bio, postCount, followers, following, fullname;
    private Button editProfileButton;
    private ImageButton myFotosButton, savedFotosButton;
    private DatabaseHelper databaseHelper;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        profileImage = view.findViewById(R.id.image_profile);
        username = view.findViewById(R.id.username);
        bio = view.findViewById(R.id.bio);
        postCount = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        fullname = view.findViewById(R.id.fullname);
        editProfileButton = view.findViewById(R.id.edit_profile);
        myFotosButton = view.findViewById(R.id.my_fotos);
        savedFotosButton = view.findViewById(R.id.saved_fotos);

        // Initialize database
        initializeViews(view);
        databaseHelper = new DatabaseHelper(getContext());

        // Get userId from arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("userId")) {
            String userId = args.getString("userId");
            if (userId != null) {
                loadUserProfile(userId); // Method to load user profile
                loadUserStats(userId);
            } else {
                Toast.makeText(getContext(), "User ID not found", Toast.LENGTH_SHORT).show();
            }
        }

        return view;

    }

    private void initializeViews(View view) {
        // Initialize your views here, for example:
        TextView fullNameTextView = view.findViewById(R.id.fullname);
        TextView usernameTextView = view.findViewById(R.id.username);
        TextView bioTextView = view.findViewById(R.id.bio);
        Button editProfileButton = view.findViewById(R.id.edit_profile);

        // And any other views you need
    }


    private void loadUserProfile(String userId) {
        Cursor cursor = databaseHelper.getUserById(userId);

        if (cursor != null && cursor.moveToFirst()) {
            String usernameStr = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String bioStr = cursor.getColumnIndex("bio") != -1 ? cursor.getString(cursor.getColumnIndex("bio")) : "Bio not available";
            String profileImageUrl = cursor.getString(cursor.getColumnIndexOrThrow("profileImageUrl"));
            String fullnameStr = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));

            username.setText(usernameStr);
            bio.setText(bioStr);
            fullname.setText(fullnameStr);

            // Load profile image
            Glide.with(this).load(profileImageUrl).into(profileImage);
        } else {
            Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    private void loadUserStats(String userId) {
        int postCountValue = databaseHelper.getPostCountByUserId(userId);
        int followersCount = databaseHelper.getFollowersCount(userId);
        int followingCount = databaseHelper.getFollowingCount(userId);

        postCount.setText(String.valueOf(postCountValue));
        followers.setText(String.valueOf(followersCount));
        following.setText(String.valueOf(followingCount));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
