package com.example.flickr1.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.flickr1.R;
import com.example.flickr1.Model.SharedViewModel;

public class PostDetailFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private SharedViewModel viewModel;
    private Uri imageUri;
    private NavController navController;  // Declare NavController here

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        // Initialize the NavController here, after view is created
        navController = NavHostFragment.findNavController(this);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ImageView imageView = view.findViewById(R.id.selected_image_view);
        Button postButton = view.findViewById(R.id.post_button);

        // Set up a click listener to open the gallery
        view.findViewById(R.id.open_gallery_button).setOnClickListener(v -> openGallery());

        // Observe the selected image URI and display it in the ImageView
        viewModel.getSelectedImageUri().observe(getViewLifecycleOwner(), uri -> {
            imageUri = uri;
            imageView.setImageURI(uri);
        });

        // Set up the post button to save the image and navigate to HomeFragment
        postButton.setOnClickListener(v -> {
            if (imageUri != null) {
                viewModel.setSelectedImageUri(imageUri); // Save the URI in SharedViewModel
                navController.navigate(R.id.action_postDetailFragment_to_homeFragment); // Navigate to HomeFragment
            }
        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                viewModel.setSelectedImageUri(uri); // Store URI in the ViewModel
            }
        }
    }
}
