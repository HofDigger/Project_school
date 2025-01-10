package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class loading_screen extends AppCompatActivity {

    ImageView imageView;
    ProgressBar loadingBar;
    TextView loadingText;
    private int progress = 0; //
    private final String[] loadingTexts = {
            "Loading your books...",
            "Organize virtual shelves...",
            "Looking for the best books...",
            "Almost ready to read!"
    };
    private int textIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.load), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        imageView = findViewById(R.id.book_loading);
        loadingBar = findViewById(R.id.loading_bar);
        loadingText = findViewById(R.id.loading_text);

        Glide.with(this)
                .load(R.drawable.b43d438638e2ed51d1f19dad2a4eb24d)
                .into(imageView);


        Handler handler = new Handler();
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                if (progress < 100) {
                    progress += 1; //
                    loadingBar.setProgress(progress);

                    if (progress % 25 == 0 && textIndex < loadingTexts.length) {
                        loadingText.setText(loadingTexts[textIndex]);
                        textIndex++;
                    }

                    handler.postDelayed(this, 50);
                } else {
                    startActivity(new Intent(loading_screen.this, SignUp_and_Login.class));
                    finish();
                }
            }
        };

        handler.post(updateTask);
    }
}
