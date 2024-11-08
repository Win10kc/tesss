package com.example.flickr1.Model;

public class Comment {

    private int id; // Primary key for the comment in the database
    private int postId; // ID of the post associated with this comment
    private int userId; // ID of the user who made the comment
    private String text; // Content of the comment
    private String timestamp; // Timestamp for when the comment was created

    // Default constructor
    public Comment() {
    }

    // Constructor with all fields
    public Comment(int id, int postId, int userId, String text, String timestamp) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    // Constructor without ID (for new comments before saving to the database)
    public Comment(int postId, int userId, String text, String timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
