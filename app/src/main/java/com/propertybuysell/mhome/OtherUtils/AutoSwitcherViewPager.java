package com.propertybuysell.mhome.OtherUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class AutoSwitcherViewPager extends ViewPager {


    private final Runnable mSwither = new Runnable() {

        @Override
        public void run() {
            if (getAdapter() != null) {
                int count = getCurrentItem();

                if (count == (getAdapter().getCount() - 1)) {
                    count = 0;
                } else {
                    count++;
                }


                setCurrentItem(count, true);
            }
            postDelayed(this, 5000);
        }
    };


    public AutoSwitcherViewPager(Context context) {
        this(context, null);
    }


    public AutoSwitcherViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postDelayed(mSwither, 5000);
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                postDelayed(mSwither, 5000);
                break;

            default:
                removeCallbacks(mSwither);
                break;
        }
        return super.onTouchEvent(arg0);
    }


}