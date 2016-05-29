package com.cn.dean.demo.scrollgroupdemo.scroll.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/29.
 */
public class ScrollControlLayout extends RelativeLayout {

    private Context mContext;
    private RelativeLayout mFrontLayout;
    private RelativeLayout mFrontBottomLayout;
    private RelativeLayout mScoreLayout;

    private float layoutHeight;
    private float mMoveFrontOffset;
    private float mMoveBehindOffset;

    private float mDownY;
    private float mMoveY;

    public ScrollControlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView();
    }

    private void initView() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutHeight = getHeight();
                mFrontLayout = (RelativeLayout) findViewById(R.id.frontLayout);
                mFrontBottomLayout = (RelativeLayout) findViewById(R.id.frontBottomLayout);
                mScoreLayout = (RelativeLayout) findViewById(R.id.scoreLayout);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getRawY();

                /** 移动 **/
                moveControl();

                mDownY = mMoveY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }

    /**
     * 移动控制
     */
    private void moveControl() {
        moveFront();
        moveBehind();
    }

    /**
     * 移动上层
     */
    private void moveFront() {
        mMoveFrontOffset += mDownY - mMoveY;
//        Log.d(ScrollControlLayout.class.getSimpleName(), "[moveFront]---> mMoveFrontOffset is " + mMoveFrontOffset);

        /** 下边界 **/
        if (mMoveFrontOffset < 0)
            mMoveFrontOffset = 0;
        /** 上边界 **/
        else if (mMoveFrontOffset > layoutHeight * 0.8 - mFrontBottomLayout.getHeight())
            mMoveFrontOffset = (float) (layoutHeight * 0.8 - mFrontBottomLayout.getHeight());

        mFrontLayout.scrollTo(0, (int) mMoveFrontOffset);
    }

    /**
     * 移动下层
     */
    private void moveBehind() {
        mMoveBehindOffset += (mDownY - mMoveY) * 0.4;
        Log.d(ScrollControlLayout.class.getSimpleName(), "[moveBehind]---> mMoveBehindOffset is " + mMoveFrontOffset);

        /** 下边界 **/
        if (mMoveBehindOffset < 0)
            mMoveBehindOffset = 0;
        /** 上边界 **/
        else if (mMoveBehindOffset > 165)
            mMoveBehindOffset = 165;

        mScoreLayout.scrollTo(0, (int) mMoveBehindOffset);
    }

}
