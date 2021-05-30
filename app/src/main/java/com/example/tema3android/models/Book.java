package com.example.tema3android.models;

import androidx.annotation.Nullable;

public class Book {
    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Book))
            return false;
        else return ((Book) obj).getAuthor().equals(this.getAuthor()) && ((Book) obj).getTitle().equals(this.getTitle());
    }
}
