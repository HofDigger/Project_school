package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.databinding.ActivityPrimaryPageBinding;

public class PrimaryPage extends AppCompatActivity {

    private ActivityPrimaryPageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrimaryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragemnt(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragemnt(new HomeFragment());
            } else if (itemId == R.id.search) {
                replaceFragemnt(new SearchFragment());
            } else if (itemId == R.id.favorite) {
                replaceFragemnt(new FavorityFragment());
            } else if (itemId == R.id.profile) {
                replaceFragemnt(new ProfileFragment());
            }
            return true;
        });
    }
    private void replaceFragemnt(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}