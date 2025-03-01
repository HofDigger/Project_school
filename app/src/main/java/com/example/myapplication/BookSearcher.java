package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookSearcher extends Service {

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BookSearcher getService() {
            return BookSearcher.this;
        }
    }

    private ArrayList<Book> books = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String query = intent.getStringExtra("query");

        Log.d("BookSearcher", "התחלנו לחפש ספרים עבור השאילתה: " + query);

        String requestUrl = "https://openlibrary.org/search.json?q=" + query;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                response -> {
                    try {
                        JSONArray docsArray = response.getJSONArray("docs");

                        if (docsArray.length() == 0) {
                            Log.d("BookSearcher", "לא נמצאו ספרים עבור השאילתה: " + query);
                        }
                        books.clear();
                        for (int i = 0; i < Math.min(docsArray.length(), 10); i++) {
                            JSONObject bookJson = docsArray.getJSONObject(i);
                            String title = bookJson.getString("title");
                            String author = bookJson.getJSONArray("author_name").getString(0);
                            String publicationYear = String.valueOf(bookJson.getInt("first_publish_year"));
                            String description = bookJson.optString("description", "No description available");
                            String imageUrl = "https://covers.openlibrary.org/b/id/" + bookJson.getInt("cover_i") + "-L.jpg";

                            books.add(new Book(title, author, publicationYear, description, imageUrl));
                        }

                    } catch (JSONException e) {
                        Log.e("BookSearcher", "שגיאה בעיבוד התשובה מה-API", e);
                    }
                },
                error -> {
                    Log.e("BookSearcher", "שגיאה בבקשה ל-API", error);
                }
        );

        Volley.newRequestQueue(this).add(request);

        return START_STICKY;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BookSearcher", "השירות הסתיים");
    }
}


