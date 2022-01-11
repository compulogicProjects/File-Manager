package com.vcrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.vcrecorder.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    CardView cardView;
    TextView title;
    Animation animTop, animBottom;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide Action Bar
        getSupportActionBar().hide();
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Initilize widgets
        timer = new Timer();
        cardView = findViewById(R.id.imglogo);
        title = findViewById(R.id.title);

        //Initilize Animations
        animTop = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Set Animations
        cardView.setAnimation(animTop);
        title.setAnimation(animBottom);
        //Set Timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


    }
}