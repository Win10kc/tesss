package com.example.flickr1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.flickr1.Model.Comment;
import com.example.flickr1.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mCommentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.mContext = context;
        this.mCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.commentText.setText(comment.getText());
        holder.commentUser.setText("User ID: " + comment.getUserId()); // You can replace this with a username if available
        holder.commentTimestamp.setText(comment.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public void setComments(List<Comment> comments) {
        this.mCommentList = comments;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView commentText;
        public TextView commentUser;
        public TextView commentTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment_text);
            commentUser = itemView.findViewById(R.id.comment_user);
            commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
        }
    }
}
