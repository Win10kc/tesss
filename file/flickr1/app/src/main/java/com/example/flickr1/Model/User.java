package com.example.flickr1.Model;

public class User {

    private int id; // Primary key in the database
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
    private String bio;
    private boolean isLoggedIn;
    String imageUrl;
    // Default constructor
    public User() {
    }

    // Constructor for all fields
    public User(int id, String username, String email, String password, String profileImageUrl, String bio, boolean isLoggedIn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.isLoggedIn = isLoggedIn;
    }

    // Constructor without ID (for new users)
    public User(String username, String email, String password, String profileImageUrl, String bio, boolean isLoggedIn) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.isLoggedIn = isLoggedIn;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    // Helper method to toggle login status
    public void toggleLoginStatus() {
        this.isLoggedIn = !this.isLoggedIn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
