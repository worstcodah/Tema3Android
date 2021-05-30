package com.example.tema3android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema3android.R;
import com.example.tema3android.adapters.MyAdapter;
import com.example.tema3android.helper.Validations;
import com.example.tema3android.interfaces.IActivityFragmentCommunication;
import com.example.tema3android.interfaces.IOnItemClickListener;
import com.example.tema3android.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment extends androidx.fragment.app.Fragment implements IOnItemClickListener {

    private ArrayList<Book> bookList = new ArrayList<>();
    private EditText bookTitle;
    private EditText bookAuthor;
    private EditText bookDescription;
    private Button addUpdateButton;
    private View view;
    private RecyclerView recyclerView;
    private IActivityFragmentCommunication activityFragmentCommunication;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IActivityFragmentCommunication) {
            activityFragmentCommunication = (IActivityFragmentCommunication) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);
        bookTitle = view.findViewById(R.id.title_et);
        bookAuthor = view.findViewById(R.id.author_et);
        bookDescription = view.findViewById(R.id.description_et);
        addUpdateButton = view.findViewById(R.id.add_update_button);
        recyclerView = view.findViewById(R.id.fragment_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(bookList, this, getContext());
        recyclerView.setAdapter(myAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("books");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String author = dataSnapshot.child("author").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    String title = dataSnapshot.child("title").getValue().toString();
                    bookList.add(new Book(title, author, description));
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addUpdateButton.setOnClickListener(v -> {
            validateAndAddUpdate();
            myAdapter.notifyDataSetChanged();
        });

        return view;
    }

    private void validateAndAddUpdate() {
        String title = bookTitle.getText().toString();
        String author = bookAuthor.getText().toString();
        String description = bookDescription.getText().toString();
        Book newBook = new Book(title, author, description);
        if (!Validations.isValidData(newBook, bookTitle, bookAuthor, bookDescription)) {
            addOrUpdate(newBook);
        }
    }

    private void addOrUpdate(Book book) {
        Query query = databaseReference.orderByChild("author").equalTo(book.getAuthor());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        if (title.equals(book.getTitle())) {
                            updateEntry(book, dataSnapshot.getKey());
                        }
                    }
                } else {
                    databaseReference.push().setValue(book);
                    bookList.add(book);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Add/update canceled!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateEntry(Book book, String key) {
        HashMap hashMap = new HashMap();
        hashMap.put("author", book.getAuthor());
        hashMap.put("title", book.getTitle());
        hashMap.put("description", book.getDescription());
        databaseReference.child(key).updateChildren(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Succesful update!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Update failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void openBookFragment(Book book) {
        activityFragmentCommunication.openBookFragment(book);
    }

    @Override
    public void deleteBook(Book book) {
        Query query = databaseReference.orderByChild("author").equalTo(book.getAuthor());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        if (title.equals(book.getTitle())) {
                            databaseReference.child(dataSnapshot.getKey()).removeValue().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    new Handler().post(() -> Toast.makeText(getContext(), "Succesful deletion!", Toast.LENGTH_SHORT).show());

                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Deletion failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Deletion canceled!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
