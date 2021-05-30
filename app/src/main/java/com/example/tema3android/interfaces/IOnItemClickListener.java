package com.example.tema3android.interfaces;

import com.example.tema3android.models.Book;

public interface IOnItemClickListener {
    void openBookFragment(Book book);
    void deleteBook(Book book);
}
