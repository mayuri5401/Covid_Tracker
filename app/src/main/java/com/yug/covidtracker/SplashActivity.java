package com.yug.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView author;
    private ImageView logo , covid;
    Animation topanimation, bottomanimation;
    private static int SPLASH_SCREEN =3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        topanimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        author = findViewById(R.id.author);
        logo = findViewById(R.id.logo);
        covid = findViewById(R.id.covid);

        author.setAnimation(bottomanimation);
        logo.setAnimation(topanimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}