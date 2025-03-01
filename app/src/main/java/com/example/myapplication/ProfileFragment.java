package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    ImageButton btn1, btn2, btn4;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn1 = view.findViewById(R.id.pfp_button);
        btn2 = view.findViewById(R.id.rating_button);
        btn4 = view.findViewById(R.id.saved_button);

        tv1 = view.findViewById(R.id.name_text);
        tv2 = view.findViewById(R.id.email_text);
        tv3 = view.findViewById(R.id.fullname_text);
        tv4 = view.findViewById(R.id.password_text);
        tv5 = view.findViewById(R.id.pn_text);
        tv6 = view.findViewById(R.id.bd_text);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        tv1.setText(getFirstWord(sharedPreferences.getString("name","Error")));
        tv3.setText(sharedPreferences.getString("name", "Error"));
        tv2.setText(sharedPreferences.getString("email", "Error"));
        tv4.setText(sharedPreferences.getString("password", "Error"));
        tv5.setText(sharedPreferences.getString("phoneNumber", "0000000000"));
        tv6.setText(sharedPreferences.getString("dob", "01/01/2000"));


        view.findViewById(R.id.logout1).setOnClickListener(view1 -> logout());

        return view;
    }

    public void logout() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
        builder1.setTitle("Log Out")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent intent = new Intent(getActivity(), SignUp_and_Login.class);
                    startActivity(intent);
                    getActivity().finish();
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.clear();
                    myEdit.apply();
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        builder1.show();
    }

    public static String getFirstWord(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        int index = input.indexOf(' ');
        if (index == -1) {
            return input;
        }
        return input.substring(0, index);
    }


}
