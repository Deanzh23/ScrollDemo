package com.cn.dean.demo.scrollgroupdemo.scroll.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Dean on 16/5/30.
 */
public class ScrollLayout extends RelativeLayout {

    private Context mContext;
    private Scroller mScroller;

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();
    }

    private void init() {
        mScroller = new Scroller(mContext);
    }

    public void scroll(float scroll) {
        mScroller.forceFinished(true);

        mScroller.startScroll(0, getScrollY(), 0, (int) scroll, 500);

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
