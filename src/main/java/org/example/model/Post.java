package org.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Post {
    private final SimpleIntegerProperty postId;
    private final SimpleStringProperty text;
    private final SimpleIntegerProperty authorId;

    public Post() {
        this(0, "", 0);
    }

    public Post(Integer postId, String text, Integer authorId) {
        this.postId = new SimpleIntegerProperty(postId);
        this.text = new SimpleStringProperty(text);
        this.authorId = new SimpleIntegerProperty(authorId);
    }

    public Integer getPostId() {
        return postId.get();
    }

    public void setPostId(Integer postId) {
        this.postId.set(postId);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public Integer getAuthorId() {
        return authorId.get();
    }

    public void setAuthorId(Integer authorId) {
        this.authorId .set(authorId);
    }
}
