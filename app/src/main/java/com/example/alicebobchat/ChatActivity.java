package com.example.alicebobchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    private FragmentA fragmentA;
    private FragmentB fragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fragmentA = (FragmentA) getSupportFragmentManager().findFragmentById(R.id.fragmentA);
        fragmentB = (FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
    }


}
