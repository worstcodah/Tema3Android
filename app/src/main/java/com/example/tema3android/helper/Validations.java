package com.example.tema3android.helper;

import android.widget.EditText;

import com.example.tema3android.constants.Constants;
import com.example.tema3android.models.Book;

public class Validations {
    public static boolean isValidData(Book book, EditText bookTitle, EditText bookAuthor, EditText bookDescription) {
        boolean encounteredError = false;
        String title = book.getTitle();
        String author = book.getAuthor();
        String description = book.getDescription();
        if (title.length() > Constants.MAX_TITLE_CHARACTERS || title.isEmpty()) {
            bookTitle.setError(Constants.TITLE_ERROR_MESSAGE);
            encounteredError = true;
        }
        if (author.length() > Constants.MAX_AUTHOR_CHARACTERS || author.isEmpty()) {
            bookAuthor.setError(Constants.AUTHOR_ERROR_MESSAGE);
            encounteredError = true;
        }
        if (description.length() > Constants.MAX_DESCRIPTION_CHARACTERS || description.isEmpty()) {
            bookDescription.setError(Constants.DESCRIPTION_ERROR_MESSAGE);
            encounteredError = true;
        }
        return encounteredError;
    }
}
