package com.example.flickr1.Model;

public class Notification {

    private int id; // Primary key in the database
    private int userId; // ID of the user receiving the notification
    private int postId; // ID of the related post, if applicable
    private String text; // Notification text
    private String timestamp;
    private boolean isRead; // Flag to check if the notification is read

    // Default constructor
    public Notification() {
    }

    // Constructor with all fields
    public Notification(int id, int userId, int postId, String text, String timestamp, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    // Constructor without ID (for new notifications before saving to the database)
    public Notification(int userId, int postId, String text, String timestamp, boolean isRead) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Notification(int id, String content, String timestamp) {

    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Method to mark the notification as read
    public void markAsRead() {
        this.isRead = true;
    }
}
