package com.example.cadastrodecontatossqlite.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cadastrodecontatossqlite.R;
import com.example.cadastrodecontatossqlite.config.DbHelper;
import com.example.cadastrodecontatossqlite.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    public static DbHelper db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        db = DbHelper.getInstance(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}