package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    private RequestQueue queue;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private static final String[] RANDOM_WORDS = {
            "adventure", "mystery", "science", "fantasy", "history",
            "romance", "art", "philosophy", "biography", "travel",
            "the", "a", "book", "story", "life", "world", "dream", "future", "past", "journey"
    };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            // Initialize the RequestQueue
            queue = Volley.newRequestQueue(requireContext());

            // Initialize RecyclerViews
            recyclerView1 = view.findViewById(R.id.recycler_view1);
            recyclerView2 = view.findViewById(R.id.recycler_view2);
            recyclerView3 = view.findViewById(R.id.recycler_view3);

            // Set LinearLayoutManager for each RecyclerView
            recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));


            // Fetch data for each RecyclerView
            fetchBooksData(getRandomWord(), new Response.Listener<List<Book>>() {
                @Override
                public void onResponse(List<Book> books) {
                    List<CardItem> cardList1 = new ArrayList<>();
                    for (Book book : books) {
                        cardList1.add(new CardItem(book.getThumbnail()));
                    }

                    // Initialize and set the adapter for the first RecyclerView
                    CardAdapter cardAdapter1 = new CardAdapter(requireContext(), cardList1);
                    recyclerView1.setAdapter(cardAdapter1);
                }
            });

            fetchBooksData("Nineteen Eighty-four", new Response.Listener<List<Book>>() {
                @Override
                public void onResponse(List<Book> books) {
                    List<CardItem> cardList2 = new ArrayList<>();
                    for (Book book : books) {
                        cardList2.add(new CardItem(book.getThumbnail()));
                    }

                    // Initialize and set the adapter for the second RecyclerView
                    CardAdapter cardAdapter2 = new CardAdapter(requireContext(), cardList2);
                    recyclerView2.setAdapter(cardAdapter2);
                }
            });

            fetchBooksData("Sherlock Holmes", new Response.Listener<List<Book>>() {
                @Override
                public void onResponse(List<Book> books) {
                    List<CardItem> cardList3 = new ArrayList<>();
                    for (Book book : books) {
                        cardList3.add(new CardItem(book.getThumbnail()));
                    }

                    // Initialize and set the adapter for the third RecyclerView
                    CardAdapter cardAdapter3 = new CardAdapter(requireContext(), cardList3);
                    recyclerView3.setAdapter(cardAdapter3);
                }
            });

            return view;
        }


    private void fetchBooksData(String query, final Response.Listener<List<Book>> callback) {
        String url = "https://openlibrary.org/search.json?q=" + query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray docs = response.getJSONArray("docs");
                            List<Book> books = new ArrayList<>();
                            int addedBooks = 0;

                            for (int i = 0; i < docs.length(); i++) {
                                if (addedBooks >= 10) break; // Limit to 10 books

                                JSONObject book = docs.getJSONObject(i);

                                // Skip books without a cover image
                                if (!book.has("cover_i")) continue;

                                String title = book.optString("title", "Unknown Title");
                                String author = book.optJSONArray("author_name") != null
                                        ? book.optJSONArray("author_name").join(", ").replace("\"", "")
                                        : "Unknown Author";
                                String coverId = book.getString("cover_i");
                                String thumbnailUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";

                                books.add(new Book(title, author, "No Description", thumbnailUrl));
                                addedBooks++; // Increment the count of added books
                            }

                            // Pass the list of books to the callback
                            callback.onResponse(books);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(RANDOM_WORDS.length);
        return RANDOM_WORDS[index];
    }
}
