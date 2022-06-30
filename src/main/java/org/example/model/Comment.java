package org.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Comment {
    private final SimpleIntegerProperty process;
    private final SimpleIntegerProperty postId;
    private final SimpleIntegerProperty authorId;
    private final SimpleStringProperty text;

    public Comment() {
        this(0, 0, 0, "");
    }

    public Comment(Integer process, Integer postId, Integer authorId, String text) {
        this.process = new SimpleIntegerProperty(process);
        this.postId = new SimpleIntegerProperty(postId);
        this.authorId = new SimpleIntegerProperty(authorId);
        this.text = new SimpleStringProperty(text);
    }

    public int getProcess() {
        return process.get();
    }

    public SimpleIntegerProperty processProperty() {
        return process;
    }

    public void setProcess(int process) {
        this.process.set(process);
    }

    public int getPostId() {
        return postId.get();
    }

    public SimpleIntegerProperty postIdProperty() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId.set(postId);
    }

    public int getAuthorId() {
        return authorId.get();
    }

    public SimpleIntegerProperty authorIdProperty() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId.set(authorId);
    }

    public String getText() {
        return text.get();
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
