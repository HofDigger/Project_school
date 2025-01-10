package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class HomeFragment extends Fragment {
    private static final String API_KEY = "AIzaSyAAyLI7uxuNysDF5UJXi0wlIshxMw1NVAc";
    private RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        queue = Volley.newRequestQueue(requireContext());
//        fetchBooksData("meow");

        return view;
    }

//    private void fetchBooksData(String string) {
//        String query = string;
//        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray items = response.getJSONArray("items");
//                            for (int i = 0; i < items.length(); i++) {
//                                JSONObject book = items.getJSONObject(i);
//                                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
//
//                                String title = volumeInfo.getString("title");
//                                String authors = volumeInfo.getJSONArray("authors").join(", ");
//                                String description = volumeInfo.optString("description", "אין תיאור");
//                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//        queue.add(jsonObjectRequest);
//    }
}
