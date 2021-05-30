package com.example.tema3android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema3android.R;
import com.example.tema3android.adapters.MyAdapter;
import com.example.tema3android.interfaces.IOnItemClickListener;
import com.example.tema3android.models.Book;
import com.google.firebase.database.FirebaseDatabase;

public class BookFragment extends Fragment {
    private View view;
    private TextView selectedBookTitle;
    private TextView selectedBookAuthor;
    private TextView selectedBookDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selected_book_fragment, container, false);
        selectedBookTitle = view.findViewById(R.id.selected_book_title);
        selectedBookAuthor = view.findViewById(R.id.selected_book_author);
        selectedBookDescription = view.findViewById(R.id.selected_book_description);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedBookTitle.setText(bundle.get("title").toString());
            selectedBookAuthor.setText(bundle.get("author").toString());
            selectedBookDescription.setText(bundle.get("description").toString());

        }
        return view;
    }
}
