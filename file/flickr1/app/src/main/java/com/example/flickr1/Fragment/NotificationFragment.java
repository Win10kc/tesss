package com.example.flickr1.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.widget.Toolbar;
import com.example.flickr1.Adapter.NotificationAdapter;
import com.example.flickr1.DatabaseHelper;
import com.example.flickr1.Model.Notification;
import com.example.flickr1.R;
import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        notificationList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        AppBarLayout appBarLayout = view.findViewById(R.id.bar);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadNotifications();

        notificationAdapter = new NotificationAdapter(getContext(), notificationList, notification -> {
            // Handle notification click event
        });
        recyclerView.setAdapter(notificationAdapter);

        return view;
    }

    private void loadNotifications() {
        Cursor cursor = databaseHelper.getAllNotifications();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                notificationList.add(new Notification(id, content, timestamp));
            }
            cursor.close();
        }
    }
}
