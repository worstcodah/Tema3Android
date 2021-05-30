package com.example.tema3android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tema3android.R;
import com.example.tema3android.fragments.BookFragment;
import com.example.tema3android.fragments.Fragment;
import com.example.tema3android.interfaces.IActivityFragmentCommunication;
import com.example.tema3android.models.Book;

public class MainActivity extends AppCompatActivity implements IActivityFragmentCommunication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragment();
    }

    @Override
    public void openFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = Fragment.class.getName();
        Fragment fragment = new Fragment();
        FragmentTransaction addTransaction = transaction.add(
                R.id.main_frame_layout, fragment, tag
        );
        addTransaction.addToBackStack(null);
        addTransaction.commit();
    }

    @Override
    public void openBookFragment(Book book) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = BookFragment.class.getName();
        Bundle bookBundle = new Bundle();
        bookBundle.putString("author", book.getAuthor());
        bookBundle.putString("title", book.getTitle());
        bookBundle.putString("description", book.getDescription());
        BookFragment bookFragment = new BookFragment();
        bookFragment.setArguments(bookBundle);
        FragmentTransaction addTransaction = transaction.replace(
                R.id.main_frame_layout, bookFragment, tag
        );
        addTransaction.addToBackStack(null);
        addTransaction.commit();
    }

}