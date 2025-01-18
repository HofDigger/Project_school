package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Book_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        // Get data from intent or previous fragment
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        String thumbnail = getIntent().getStringExtra("thumbnail");

        // Create a new instance of BookFragment with the data
        BookFragment bookFragment = BookFragment.newInstance(title, author, description, thumbnail);

        // Replace fragment in the container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
