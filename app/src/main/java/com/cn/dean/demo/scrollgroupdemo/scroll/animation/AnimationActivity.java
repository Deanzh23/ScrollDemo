package com.cn.dean.demo.scrollgroupdemo.scroll.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/29.
 */
public class AnimationActivity extends AppCompatActivity {

    private RelativeLayout mFrontBottomLayout;
    private Button mButton;

    private float mDownY;
    private float mMoveY;

    private boolean isMoving = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {
        mFrontBottomLayout = (RelativeLayout) findViewById(R.id.frontBottomLayout);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationActivity.this, "debug", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getRawY();

                if (!isMoving) {
                    /** 执行动画 **/
                    move();

                    mDownY = mMoveY;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                /** 执行动画 **/
//                move();
                break;
        }

        return super.onTouchEvent(event);
    }

    private int mY;

    private void move() {
        isMoving = true;

        mFrontBottomLayout.scrollTo(0, mY += (int) (mMoveY - mDownY));

        isMoving = false;

//        float y = mFrontBottomLayout.getTop() + (mMoveY - mDownY);
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFrontBottomLayout, "translationY", y);
//        objectAnimator.setDuration(100);
//        objectAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                Log.d(AnimationActivity.class.getSimpleName(), "[move start]--->y is " + mFrontBottomLayout.getTop());
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.d(AnimationActivity.class.getSimpleName(), "[move end]--->y is " + mFrontBottomLayout.getTop());
////                mFrontBottomLayout.scrollTo(0, );
//                isMoving = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                Log.d(AnimationActivity.class.getSimpleName(), "[move repeat]--->y is " + mFrontBottomLayout.getTop());
//            }
//        });
//        objectAnimator.start();
    }

}
