package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_fragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private EditText etEmail, etPass;
    private Button forgot_pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPassword);
        forgot_pass = view.findViewById(R.id.forgot_pass);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_to_restore_password();
            }
        });

        view.findViewById(R.id.dont_have_an_account).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SignUp_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.login).setOnClickListener(v -> {
            login();
        });

        return view;
    }

    public void login() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Intent intent;
                        if (task.isSuccessful()) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            Log.d("LoginFragment", "User ID: " + userId);
                            databaseReference.child("users").child(userId).child("fullName").get()
                                    .addOnCompleteListener(task1 -> {
                                        Log.d("LoginFragment", "Fetching user data...");
                                        if (task1.isSuccessful()) {
                                            Log.d("LoginFragment", "next");
                                            String name = task1.getResult().getValue(String.class);
                                            Log.d("LoginFragment", "Fetched name: " + name);
                                            saveShared(name, email, pass, userId);
                                        }
                                    });

                            intent = new Intent(getActivity(), PrimaryPage.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void dialog_to_restore_password() {
        EditText input = new EditText(requireContext());
        input.setHint("Enter your email");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Restore Password")
                .setMessage("Please enter your email address:")
                .setView(input)
                .setPositiveButton("Send", (dialog, id) -> {
                    String userInput = input.getText().toString().trim();
                    if (!userInput.isEmpty() & userInput.contains("@")) {
                        restorePassword(userInput);
                    } else {
                        Toast.makeText(getContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        builder.show();
    }

    public void restorePassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getContext(), "Email sent.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void saveShared(String name, String email, String password, String uid) {
        if (name == null || email == null || password == null || uid == null) {
            Log.e("SharedPreferences", "Error: One or more values are null. Data not saved.");
            return;
        }

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("name", name);
        myEdit.putString("email", email);
        myEdit.putString("password", password);
        myEdit.putString("uid", uid);
        myEdit.putBoolean("isLoggedIn", true);

        myEdit.apply();

        Log.d("SharedPreferences", "Data saved successfully.");
        Log.d("SharedPreferences", "Saved name: " + name);
        Log.d("SharedPreferences", "Saved email: " + email);
        Log.d("SharedPreferences", "Saved password: " + password);
        Log.d("SharedPreferences", "Saved uid: " + uid);
    }
}
