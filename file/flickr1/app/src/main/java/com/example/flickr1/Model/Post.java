package com.example.flickr1.Model;

public class Post {
    private int id;  // Using 'int' for ID
    private String title;
    private String description;
    private String imageUrl;
    private String username;
    private int likes;
    private String publisher;
    private String publisherImage;
    private String postImageUrl;
    private String content;

    // Default constructor
    public Post() {
    }

    public Post(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Constructor with id, title, and description, using int for id
    public Post(String title, String description, String postImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.postImageUrl = postImageUrl;
    }

    public Post(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Constructor for cases where id is passed as String
    public Post(String id, String title, String description, String postImageUrl) {
        this.id = Integer.parseInt(id);  // Converts String id to int
        this.title = title;
        this.description = description;
        this.postImageUrl = postImageUrl;
    }

    // Full constructor for detailed initialization
    public Post(int id, String title, String description, String imageUrl, String username, int likes, String publisher, String publisherImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.username = username;
        this.likes = likes;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherImage() {
        return publisherImage;
    }

    public void setPublisherImage(String publisherImage) {
        this.publisherImage = publisherImage;
    }

    // Getter for postImageUrl
    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;

    }

    // Getter method for the image URI
    public String getImageUri() {
        return postImageUrl;
    }

    // Setter method for the image URI (if needed)
    public void setImageUri(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

}
