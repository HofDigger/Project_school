package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_AUTHOR = "author";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_THUMBNAIL = "thumbnail";

    private String title;
    private String author;
    private String description;
    private String thumbnail;

    public static BookFragment newInstance(String title, String author, String description, String thumbnail) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_AUTHOR, author);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_THUMBNAIL, thumbnail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            author = getArguments().getString(ARG_AUTHOR);
            description = getArguments().getString(ARG_DESCRIPTION);
            thumbnail = getArguments().getString(ARG_THUMBNAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        TextView titleTextView = view.findViewById(R.id.book_details_title);
        TextView authorTextView = view.findViewById(R.id.book_details_author);
        TextView descriptionTextView = view.findViewById(R.id.book_details_description);
        ImageView thumbnailImageView = view.findViewById(R.id.book_details_image);

        titleTextView.setText(title);
        authorTextView.setText(author);
        descriptionTextView.setText(description);

        Glide.with(requireContext())
                .load(thumbnail)
                .placeholder(R.drawable.images)
                .into(thumbnailImageView);

        return view;
    }
}

