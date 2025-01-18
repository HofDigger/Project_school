package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final Context context;
    private final List<Book> books;
    private final OnBookClickListener onBookClickListener;

    public BookAdapter(Context context, List<Book> books, OnBookClickListener onBookClickListener) {
        this.context = context;
        this.books = books != null ? books : List.of();
        this.onBookClickListener = onBookClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        // Set book details
        holder.title.setText(book.getTitle() != null ? book.getTitle() : "Unknown Title");
        holder.author.setText(book.getAuthors() != null ? book.getAuthors() : "Unknown Author");

        // Load image using Glide
        Glide.with(context)
                .load(book.getThumbnail())
                .placeholder(R.drawable.images)
                .error(R.drawable.images)
                .into(holder.image);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Book_Info.class);
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthors());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("thumbnail", book.getThumbnail());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.book_image);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
        }
    }

    public interface OnBookClickListener {
        void onBookClick(Book book);

    }
}

