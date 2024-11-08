package com.example.flickr1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flickr1.Adapter.CommentAdapter;
import com.example.flickr1.Model.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private EditText addComment;
    private TextView post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);  // Updated layout reference

        recyclerView = findViewById(R.id.recycler_view);  // RecyclerView ID from activity_comments.xml
        addComment = findViewById(R.id.add_comment);      // EditText ID from activity_comments.xml
        post = findViewById(R.id.post);                   // TextView ID for posting a comment

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList);  // Updated constructor
        recyclerView.setAdapter(commentAdapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = addComment.getText().toString();
                if (!commentText.isEmpty()) {
                    addComment(commentText);
                }
            }
        });
    }

    private void addComment(String commentText) {
        // Add your logic to insert the comment into SQLite or relevant database
        Comment comment = new Comment();  // Ensure Comment model has appropriate fields
        comment.setText(commentText);  // Set text for comment
        commentList.add(comment);
        commentAdapter.notifyDataSetChanged();
        addComment.setText("");
    }
}
