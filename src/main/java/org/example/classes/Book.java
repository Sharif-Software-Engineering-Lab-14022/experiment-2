package org.example.classes;

public class Book {
    private int id;
    private String title;
    private String author;

    public Book(String title, String author, int id) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public int getBid() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}