package com.cn.dean.demo.scrollgroupdemo.scroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/26.
 */
public class ScrollViewGroup extends RelativeLayout {

    private Context mContext;
    private Scroller mScroller;
    private Rect mOutRect;

    private RelativeLayout mFrontBottomLayout;

    private int mY = 0;
    private float mDownY;
    private float mMoveY;

    public ScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        initScroller();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFrontBottomLayout = (RelativeLayout) findViewById(R.id.frontBottomLayout);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        int count = getChildCount();
//
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//
//            if (view.getId() == R.id.frontBottomLayout) {
//                mFrontBottomLayout = (RelativeLayout) view;
//            }
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getY();

                int moveDistance = mY + (int) (mDownY - mMoveY);
                Log.d(ScrollViewGroup.class.getSimpleName(), "[MotionEvent.ACTION_MOVE]--->moveDistance is " + moveDistance);

                if (mMoveY < mDownY) {
                    if (moveDistance + mFrontBottomLayout.getHeight() <= mOutRect.bottom * 0.8)
                        scrollView(moveDistance);
                } else if (mMoveY > mDownY) {
                    if (moveDistance >= 0)
                        scrollView(moveDistance);
                }


                mDownY = mMoveY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                autoScroll();
                break;
        }

        return true;
    }

    private void scrollView(int moveDistance) {
        scrollTo(0, moveDistance);
        /** 移动位置，并记录当前移动，供下次移动使用 **/
        mY += mDownY - mMoveY;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        mOutRect = new Rect();

        ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(mOutRect);
    }

    private void initScroller() {
        mScroller = new Scroller(mContext);
    }

    public void autoScroll() {
        mScroller.forceFinished(true);

        int[] localRect = new int[2];
        mFrontBottomLayout.getLocationOnScreen(localRect);

        int startY;
        if (localRect[1] > mOutRect.bottom / 2.0 - 120) {
            startY = mY * mMoveY - mDownY > 0 ? 1 : -1;
            mY = 0;
        } else {
            startY = (mOutRect.bottom - localRect[1] - mY);
            mY = (int) (mOutRect.bottom * 0.8 - mFrontBottomLayout.getHeight());
        }
        mScroller.startScroll(0, startY, 0, 0);

        invalidate();
    }

    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();

            scrollTo(mScroller.getCurrX(), currY);

            postInvalidate();
        }
    }

}
