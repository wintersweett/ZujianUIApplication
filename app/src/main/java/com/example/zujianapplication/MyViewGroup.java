package com.example.zujianapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        this(context,null);
    }
    protected void measureChild(View child ,int parentWidthMeasureSpec ,int parentHeightMeasureSpec){
        LayoutParams lp = child.getLayoutParams() ;
        int childWidthMeasureSpec =
                getChildMeasureSpec(parentWidthMeasureSpec,getPaddingLeft()+getPaddingRight(),lp.width);
        int childHeightMeasureSpec =
                getChildMeasureSpec(parentHeightMeasureSpec,getPaddingLeft()+getPaddingRight(),lp.height) ;
        child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
