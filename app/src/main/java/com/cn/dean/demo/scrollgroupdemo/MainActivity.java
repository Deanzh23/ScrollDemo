package com.cn.dean.demo.scrollgroupdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cn.dean.demo.scrollgroupdemo.animation.AnimationActivity;
import com.cn.dean.demo.scrollgroupdemo.scroll.ScrollerActivity;

public class MainActivity extends AppCompatActivity {

    private Button scrollerButton, animationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollerButton = (Button) findViewById(R.id.scrollerButton);
        scrollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollerActivity.class));
            }
        });

        animationButton = (Button) findViewById(R.id.animationButton);
        animationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimationActivity.class));
            }
        });
    }

}
