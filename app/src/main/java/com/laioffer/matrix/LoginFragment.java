package com.laioffer.matrix;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends OnBoardingBaseFragment {

    private SharedPreferences sharedPreferences;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(SharedPreferences sharedPreferences) {
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = super.onCreateView(inflater, container, savedInstanceState);
        submitButton.setText(getString(R.string.login));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final String password = Utils.md5Encryption(passwordEditText.getText().toString());

                database.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //varify data in database
                        if (dataSnapshot.hasChild(username) && (password.equals(dataSnapshot.child(username).child("user_password").getValue()))) {
                            Config.username = username;
                            login(username, password);
                            startActivity(new Intent(getActivity(), ControlPanel.class));
                        } else {
                            Toast.makeText(getActivity(),"Please try to login again", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    private void login(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.CHECK_LOGIN, true);
        editor.putString(Config.USER_NAME, username);
        editor.putString(Config.USER_PASSWORD, password);
        editor.commit();
        //Log.d("pause","test");
    }
}
