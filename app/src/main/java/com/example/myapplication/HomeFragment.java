package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private BookSearcher bookSearcherService;
    private boolean isBound = false;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private CardAdapter cardAdapter1, cardAdapter2, cardAdapter3;
    private ArrayList<Book> books1 = new ArrayList<>();
    private ArrayList<Book> books2 = new ArrayList<>();
    private ArrayList<Book> books3 = new ArrayList<>();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BookSearcher.LocalBinder binder = (BookSearcher.LocalBinder) service;
            bookSearcherService = binder.getService();
            isBound = true;

            books1 = bookSearcherService.getBooks();
            books2 = bookSearcherService.getBooks();
            books3 = bookSearcherService.getBooks();

            updateRecyclerView(recyclerView1, cardAdapter1, books1);
            updateRecyclerView(recyclerView2, cardAdapter2, books2);
            updateRecyclerView(recyclerView3, cardAdapter3, books3);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        recyclerView3 = view.findViewById(R.id.recycler_view3);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Intent intent = new Intent(getActivity(), BookSearcher.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        startBookSearch("Harry Potter");
        startBookSearch("Lord of the Rings");
        startBookSearch("Game of Thrones");

        return view;
    }

    private void startBookSearch(String query) {
        Intent searchIntent = new Intent(getActivity(), BookSearcher.class);
        searchIntent.putExtra("query", query);
        getActivity().startService(searchIntent);
    }

    private void updateRecyclerView(RecyclerView recyclerView, CardAdapter adapter, ArrayList<Book> books) {
        if (adapter == null) {
            adapter = new CardAdapter(books);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBound) {
            getActivity().unbindService(serviceConnection);
            isBound = false;
        }
    }
}
