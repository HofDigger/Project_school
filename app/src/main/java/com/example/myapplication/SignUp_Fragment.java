package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Fragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private EditText etEmail, etPass, etFullName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up_, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPassword);
        etFullName = view.findViewById(R.id.etFullName);

        view.findViewById(R.id.already_registered).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Login_fragment())
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            register();
        });

        return view;
    }

    public void register() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String fullName = etFullName.getText().toString();

        if (email.isEmpty() || pass.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@")) {
            Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6)
        {
            Toast.makeText(getContext(), "The password is too short", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            User user = new User(fullName, email);
                            databaseReference.child("users").child(userId).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            getParentFragmentManager().beginTransaction()
                                                    .replace(R.id.fragment_container, new Login_fragment())
                                                    .commit();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static class User {
        public String fullName;
        public String email;

        public User() {
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }
    }
}
