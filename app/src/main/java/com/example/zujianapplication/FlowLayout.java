package com.example.zujianapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                height = size;
                break;
            case MeasureSpec.AT_MOST:
                //内容的最大高度
                int width = getMeasuredWidth();
                int n = getChildCount();
                //当前行子组件的最大高度
                int maxViewHeight = 0;
                //当前行子组件的总宽度
                int maxLinWidth = 0;
                for (int i = 0; i < n; i++) {
                    View child = getChildAt(i);
                    maxLinWidth += child.getMeasuredWidth();
                    maxViewHeight = Math.max(child.getMeasuredHeight(), maxViewHeight);
                    //预测是否需要换行
                    if (i < n - 1 && maxLinWidth
                            + getChildAt(i + 1).getMeasuredWidth()
                            > width - getPaddingLeft() - getPaddingRight()) {
                        height += maxViewHeight;
                        maxLinWidth = 0;
                        maxViewHeight = 0;
                    } else if (i == n - 1) {
                        height += maxViewHeight;
                    }
                }
                height += getPaddingTop() + getPaddingBottom();

                break;
        }

        return height;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                width = size;
                break;
            case MeasureSpec.AT_MOST:
                //计算出所有子组件的宽度
                int n = getChildCount();
                int childrenWidth = 0;
                for (int i = 0; i < n; i++) {
                    View child = getChildAt(i);
                    int childWidth = child.getMeasuredWidth();
                    if (childWidth > size) {
                        throw new IllegalStateException("the sub view is too large");
                    }
                    childrenWidth += childWidth;

                }
                if (childrenWidth > size) {
                    width = size;
                } else {
                    width = childrenWidth;
                }
                width += getPaddingLeft() + getPaddingRight();

                break;
        }
        return width;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int n = getChildCount();
        int maxViewHeight = 0;
        int maxLineWidth = 0;
        int totalHeight = 0;
        int width = getMeasuredWidth();
        for (int i = 0; i < n; i++) {
            View child = getChildAt(i);
            if (maxLineWidth + getChildAt(i).getMeasuredWidth()
                    > width - getPaddingLeft() - getPaddingRight()) {
                totalHeight +=maxViewHeight;
                maxLineWidth = 0 ;
                maxViewHeight = 0 ;
            }
            layoutChild(child,maxLineWidth,totalHeight,
                    maxLineWidth+child.getMeasuredWidth(),
                    totalHeight+child.getMeasuredHeight()) ;
            maxViewHeight = Math.max(maxViewHeight,child.getMeasuredHeight()) ;
            maxLineWidth +=child.getMeasuredWidth() ;
        }

    }

    private void layoutChild(View child, int l, int t, int r, int b) {
        //所有子组件都要统一向右 向下平移指定的padding
        child.layout(l+getPaddingLeft(),
                t+getPaddingTop(),
                r+getPaddingLeft(),
                b+getPaddingTop());
    }
}
