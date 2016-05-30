package com.cn.dean.demo.scrollgroupdemo.scroll.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/27.
 */
public class MainActivity extends AppCompatActivity {

    private ScrollControlLayout mScrollControlLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        mScrollControlLayout = (ScrollControlLayout) findViewById(R.id.scrollControlLayout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mScrollControlLayout.init(this);
    }
}
