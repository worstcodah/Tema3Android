package com.example.tema3android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema3android.R;
import com.example.tema3android.interfaces.IOnItemClickListener;
import com.example.tema3android.models.Book;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Book> bookList;
    Context context;
    IOnItemClickListener onItemClickListener;

    public MyAdapter(ArrayList<Book> bookList, IOnItemClickListener onItemClickListener, Context context) {
        this.bookList = bookList;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookViewHolder) {
            Book book = (Book) bookList.get(position);
            ((BookViewHolder) holder).bind(book);
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView author;
        private Button deleteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            description = itemView.findViewById(R.id.book_description);
            deleteButton = itemView.findViewById(R.id.book_delete_button);
        }

        void bind(Book book) {
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            description.setText(book.getDescription());
            deleteButton.setOnClickListener(v -> onItemClickListener.deleteBook(book));
            itemView.setOnClickListener(v -> onItemClickListener.openBookFragment(book));
        }
    }
}
