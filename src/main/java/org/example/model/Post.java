package org.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Post {
    private final SimpleIntegerProperty amount_of_workers;
    private final SimpleStringProperty process;
    private final SimpleIntegerProperty amount_of_equip;

    public Post() {
        this(0, "", 0);
    }

    public Post(Integer amount_of_workers, String process, Integer amount_of_equip) {
        this.amount_of_workers = new SimpleIntegerProperty(amount_of_workers);
        this.process = new SimpleStringProperty(process);
        this.amount_of_equip = new SimpleIntegerProperty(amount_of_equip);
    }

    public Integer getPostId() {
        return amount_of_workers.get();
    }

    public void setPostId(Integer postId) {
        this.amount_of_workers.set(postId);
    }

    public String getText() {
        return process.get();
    }

    public void setText(String text) {
        this.process.set(text);
    }

    public Integer getAuthorId() {
        return amount_of_equip.get();
    }

    public void setAuthorId(Integer authorId) {
        this.amount_of_equip .set(authorId);
    }
}
