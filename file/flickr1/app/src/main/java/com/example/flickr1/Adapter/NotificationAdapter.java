package com.example.flickr1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import com.example.flickr1.Model.Notification;
import com.example.flickr1.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private List<Notification> mNotificationList;
    private OnNotificationClickListener mListener;

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
    }

    public NotificationAdapter(Context context, List<Notification> notificationList, OnNotificationClickListener listener) {
        this.mContext = context;
        this.mNotificationList = notificationList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = mNotificationList.get(position);

        holder.textViewMessage.setText(notification.getText());
        Glide.with(mContext).load(R.drawable.default_profile_image).into(holder.imageViewProfile);

        holder.itemView.setOnClickListener(v -> mListener.onNotificationClick(notification));
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProfile;
        public TextView textViewMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.notification_profile_image);
            textViewMessage = itemView.findViewById(R.id.notification_message);
        }
    }
}
