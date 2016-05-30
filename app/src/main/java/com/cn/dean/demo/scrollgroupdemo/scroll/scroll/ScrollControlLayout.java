package com.cn.dean.demo.scrollgroupdemo.scroll.scroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/29.
 */
public class ScrollControlLayout extends RelativeLayout {

    private static final int MOVE_TO_TOP = 0;
    private static final int MOVE_TO_BOTTOM = 1;

    private Context mContext;
    private Rect outRect;

    private ScrollLayout mFrontLayout;
    private RelativeLayout mFrontBottomLayout;
    private ScrollLayout mScoreLayout;
    private RelativeLayout mScoreCenterLayout;
    private TextView mQualityOfSleepTextView;
    private TextView mScoreBottomTextView;

    private float layoutHeight;
    private float mMoveFrontOffset;
    private float mMoveBehindOffset;
    private float mScoreInitHeight;
    private float mScoreMaxY;

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
                mFrontLayout = (ScrollLayout) findViewById(R.id.frontLayout);
                mFrontBottomLayout = (RelativeLayout) findViewById(R.id.frontBottomLayout);
                mScoreLayout = (ScrollLayout) findViewById(R.id.scoreLayout);
                mScoreCenterLayout = (RelativeLayout) findViewById(R.id.scoreCenterLayout);
                mQualityOfSleepTextView = (TextView) findViewById(R.id.qualityOfSleepTextView);
                mScoreBottomTextView = (TextView) findViewById(R.id.scoreBottomTextView);
                mScoreInitHeight = mScoreBottomTextView.getY() - mQualityOfSleepTextView.getY();
                mScoreMaxY = mScoreCenterLayout.getY() + mScoreCenterLayout.getHeight();
            }
        });
    }

    public void init(Activity activity) {
        outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
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

                /** 手动移动 **/
                moveControl();

                mDownY = mMoveY;
                break;
            case MotionEvent.ACTION_UP:
                /** 弹性移动 **/
                autoMove();
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
        mMoveBehindOffset += (mDownY - mMoveY) * 0.35;

        /** 下边界 **/
        if (mMoveBehindOffset < 0)
            mMoveBehindOffset = 0;
        /** 上边界 **/
        else if (mMoveBehindOffset > layoutHeight * 0.2 - mScoreInitHeight)
            mMoveBehindOffset = (float) (layoutHeight * 0.2) - mScoreInitHeight;

        mScoreLayout.scrollTo(0, (int) mMoveBehindOffset);
    }

    private void autoMove() {
        int[] localRect = new int[2];
        mFrontBottomLayout.getLocationOnScreen(localRect);

        if (localRect[1] > layoutHeight * 0.5 - 50) {
            autoMoveFront(MOVE_TO_BOTTOM);
            autoMoveBehind(MOVE_TO_BOTTOM);
        } else {
            autoMoveFront(MOVE_TO_TOP);
            autoMoveBehind(MOVE_TO_TOP);
        }
    }

    private void autoMoveFront(int direction) {
        int[] localRect = new int[2];
        mFrontBottomLayout.getLocationOnScreen(localRect);

        float move = 0;

        switch (direction) {
            case MOVE_TO_BOTTOM:
                mMoveFrontOffset = 0;
                move = localRect[1] - layoutHeight + mFrontBottomLayout.getHeight() - outRect.top;
                break;
            case MOVE_TO_TOP:
                mMoveFrontOffset = (float) (layoutHeight * 0.8 - mFrontBottomLayout.getHeight());
                move = (float) (localRect[1] - layoutHeight * 0.2 - outRect.top);
                break;
        }

        mFrontLayout.scroll(move);
    }

    private void autoMoveBehind(int direction) {
        int[] localRect = new int[2];
        mScoreCenterLayout.getLocationOnScreen(localRect);

        float move = 0;

        switch (direction) {
            case MOVE_TO_BOTTOM:
                mMoveBehindOffset = 0;
                move = localRect[1] - mScoreMaxY + mScoreCenterLayout.getHeight() - outRect.top;
                break;
            case MOVE_TO_TOP:
                mMoveBehindOffset = (float) (layoutHeight * 0.2) - mScoreInitHeight;
                move = (float) (localRect[1] - outRect.top);
                break;
        }

        mScoreLayout.scroll(move);
    }

}
