package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize views
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recycler_view);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Implement item click listener for books
        adapter = new BookAdapter(requireContext(), bookList, book -> {
            // Handle book click (for example, load a BookDetailsFragment)
            if (getActivity() != null) {
                BookFragment detailsFragment = BookFragment.newInstance(
                        book.getTitle(), book.getAuthors(), book.getDescription(), book.getThumbnail()
                );
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);

        // Initialize Volley RequestQueue
        queue = Volley.newRequestQueue(requireContext());

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    fetchBooks(query.trim());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void fetchBooks(String query) {
        // Construct API URL
        String url = "https://openlibrary.org/search.json?q=" + query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray docs = response.getJSONArray("docs");
                        bookList.clear(); // Clear previous search results

                        for (int i = 0; i < Math.min(20, docs.length()); i++) {
                            JSONObject bookObj = docs.getJSONObject(i);

                            // Extract book details
                            String title = bookObj.optString("title", "Unknown Title");
                            String author = bookObj.optJSONArray("author_name") != null
                                    ? bookObj.optJSONArray("author_name").join(", ").replace("\"", "")
                                    : "Unknown Author";
                            String thumbnail = bookObj.has("cover_i") ?
                                    "https://covers.openlibrary.org/b/id/" + bookObj.getString("cover_i") + "-L.jpg" : null;

                            // Add to book list
                            if (thumbnail != null) { // Ensure books with images are added
                                bookList.add(new Book(title, author, "No Description", thumbnail));
                            }
                        }

                        // Notify adapter of data changes
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error fetching books", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show();
                }
        );

        // Add request to queue
        queue.add(jsonObjectRequest);
    }
}
