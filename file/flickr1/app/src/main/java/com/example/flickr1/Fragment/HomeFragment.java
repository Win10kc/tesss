package com.example.flickr1.Fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import com.example.flickr1.Adapter.PostAdapter;
import com.example.flickr1.DatabaseHelper;
import com.example.flickr1.Model.Post;
import com.example.flickr1.R;
import com.example.flickr1.Model.SharedViewModel;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;
    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        databaseHelper = new DatabaseHelper(getContext());
        loadPosts();

        // Initialize ViewModel and observe for image URI updates
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelectedImageUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                // Create a new Post object with the URI
                Post post = new Post(uri.toString()); // Assuming Post has a constructor taking URI as a String

                // Add to the post list and update the adapter
                postList.add(0, post); // Add new post at the top
                postAdapter.notifyDataSetChanged(); // Refresh the RecyclerView
            }
        });


        return view;
    }

    private void loadPosts() {
        postList.clear();
        Cursor cursor = databaseHelper.getAllPosts();
        int titleIndex = cursor.getColumnIndex("title");
        int descriptionIndex = cursor.getColumnIndex("description");
        int imageUrlIndex = cursor.getColumnIndex("imageUrl");

        if (titleIndex != -1 && descriptionIndex != -1 && imageUrlIndex != -1) {
            do {
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String imageUrl = cursor.getString(imageUrlIndex);
                Post post = new Post(title, description, imageUrl);
                postList.add(post);
            } while (cursor.moveToNext());
        } else {
            // Handle the error: log or show a message
            Log.e("HomeFragment", "One or more columns not found in database.");
        }

        postAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private void uploadImage(Uri imageUri) {
        // Implement the upload logic here, e.g., upload to server
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
