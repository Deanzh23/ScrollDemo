package com.cn.dean.demo.scrollgroupdemo.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cn.dean.demo.scrollgroupdemo.R;

/**
 * Created by Dean on 16/5/27.
 */
public class ScrollAnimationLayout extends FrameLayout {

    private RelativeLayout mTopLayout, mCenterLayout, mBottomLayout;

    public ScrollAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//
//            switch (view.getId()) {
//                case R.id.topLayout:
//                    mTopLayout = (RelativeLayout) view;
//                    mTopLayout.layout(left, top, right, (int) mCenterLayout.getY());
//                    break;
//                case R.id.centerLayout:
//                    mCenterLayout = (RelativeLayout) view;
//                    break;
//                case R.id.bottomLayout:
//                    mBottomLayout = (RelativeLayout) view;
//                    mBottomLayout.layout(left, (int) (mCenterLayout.getY() + mCenterLayout.getHeight()), right, bottom);
//                    break;
//            }
//        }
    }
}
