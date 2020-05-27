package com.example.roshan.rapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Animation animation;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img=(ImageView)findViewById(R.id.imageView);
        animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.intro);
        img.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, Main2Activity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
