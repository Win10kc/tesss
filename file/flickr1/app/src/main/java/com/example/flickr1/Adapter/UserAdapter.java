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
import com.example.flickr1.Model.User;
import com.example.flickr1.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUserList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public UserAdapter(Context context, List<User> userList, OnItemClickListener listener) {
        this.mContext = context;
        this.mUserList = userList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = mUserList.get(position);

        // Set username
        holder.username.setText(user.getUsername());

        // Set profile image using Glide
        Glide.with(mContext).load(user.getImageUrl()).into(holder.profileImage);

        // Handle item clicks
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(user));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setUsers(List<User> users) {
        this.mUserList = users;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImage;
        public TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
        }
    }
}
