package com.example.alquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class splashScreen extends AppCompatActivity {
    ImageView img;
    TextView title;
    Timer timer;
    Animation animTop,animBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        timer = new Timer();
        img = findViewById(R.id.imglogo);
        title = findViewById(R.id.title);



        animTop = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img.setAnimation(animTop);
        title.setAnimation(animBottom);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,HomeScreen.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
}


