package com.smart.android.file.manager.tool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.ads.AppLovin;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    TextView title;
    Animation animTop, animBottom;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppLovin.initialize_sdk(MainActivity.this);

        //getSupportActionBar().hide();
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Initilize widgets
        timer = new Timer();
        img = findViewById(R.id.imglogo);
        title = findViewById(R.id.title);

        //Initilize Animations
        animTop = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Set Animations
        img.setAnimation(animTop);
        title.setAnimation(animBottom);
        //Set Timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


    }
}