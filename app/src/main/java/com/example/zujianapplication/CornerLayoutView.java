package com.example.zujianapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/***
*@Author: WinterSweett
*@Description: 为了支持margin ，需要重写generateDefaultLayoutParams generateLayoutParams
*/
public class CornerLayoutView extends ViewGroup {
    public CornerLayoutView(Context context) {
        this(context,null);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    public CornerLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
    *@Description 定位子组件
    *@author WinterSweett
    */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftPadding = getPaddingLeft() ;
        int rightPadding = getPaddingRight() ;
        int topPadding = getPaddingTop() ;
        int bottomPadding = getPaddingBottom() ;
        for(int i= 0 ;i<getChildCount() ;i++){
            View child = getChildAt(i) ;
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int leftMargin = layoutParams.leftMargin ;
            int rightMargin = layoutParams.rightMargin ;
            int topMargin = layoutParams.topMargin ;
            int bottomMargin = layoutParams.bottomMargin ;
            switch (i){
                case 0:
                    child.layout(leftPadding+leftMargin,
                            topPadding+topMargin,
                            child.getMeasuredWidth()+leftPadding+leftMargin,
                            child.getMeasuredHeight()+topPadding+topMargin);
                    break;
                case 1:
                    child.layout(getMeasuredWidth() - child.getMeasuredWidth()-rightMargin-rightPadding,
                            topPadding+topMargin,
                            child.getMeasuredWidth()-rightMargin-rightPadding,
                            child.getMeasuredHeight()+topPadding+topMargin);
                    break;
                case 2:
                    child.layout(leftPadding+leftMargin,
                            getMeasuredHeight() - child.getMeasuredHeight()-bottomMargin-bottomPadding,
                            child.getMeasuredWidth()+leftPadding+leftMargin,
                            child.getMeasuredHeight()-bottomMargin-bottomPadding);
                    break;
                case 3:
                    child.layout(getMeasuredWidth() - child.getMeasuredWidth()-rightMargin-rightPadding,
                            getMeasuredHeight()-child.getMeasuredHeight()-bottomMargin-bottomPadding,
                            getMeasuredWidth()-rightMargin-rightPadding,
                            getMeasuredHeight()-bottomMargin-bottomPadding);
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //先测量所有子组件的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //再测量自己的大小
        int width = measureWidth(widthMeasureSpec) ;
        int height = measureHeight(heightMeasureSpec) ;
        setMeasuredDimension(width,height);

    }
    private int measureWidth(int widthMeasureSpec){
        int mode = MeasureSpec.getMode(widthMeasureSpec) ;
        int size = MeasureSpec.getSize(widthMeasureSpec) ;
        int width = 0 ;
        if(mode == MeasureSpec.EXACTLY){
            //match_parent 或 具体值
            width = size ;

        }else if(mode == MeasureSpec.AT_MOST){
            int aWidth ,bWidth,cWidth,dWidth ;
            aWidth = bWidth = cWidth = dWidth = 0;
            int marginHa ,marginHb,marginHc,marginHd ;
            marginHa = marginHb= marginHc = marginHd = 0;
            for(int i= 0 ;i<getChildCount() ;i++){
                MarginLayoutParams layoutParams = (MarginLayoutParams) getChildAt(i).getLayoutParams();
                switch (i){
                    case 0:
                        aWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHa +=layoutParams.leftMargin+layoutParams.rightMargin ;
                        break;
                    case 1:
                        bWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHb +=layoutParams.leftMargin+layoutParams.rightMargin ;
                        break;
                    case 2:
                        cWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHc +=layoutParams.leftMargin+layoutParams.rightMargin ;
                        break;
                    case 3:
                        dWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHd +=layoutParams.leftMargin+layoutParams.rightMargin ;
                        break;
                }
            }
          width = Math.max(aWidth,cWidth) + Math.max(bWidth,dWidth)
          +getPaddingLeft() + getPaddingRight()
          +Math.max(marginHa,marginHc)
          +Math.max(marginHb,marginHd)  ;
        }
        return width ;
    }

    /**
    *@Description 忽略内部参数命名
    *@author WinterSweett
    */
    private int measureHeight(int heightMeasureSpec){
        int mode = MeasureSpec.getMode(heightMeasureSpec) ;
        int size = MeasureSpec.getSize(heightMeasureSpec) ;
        int width = 0 ;
        if(mode == MeasureSpec.EXACTLY){
            //match_parent 或 具体值
            width = size ;

        }else if(mode == MeasureSpec.AT_MOST){
            int aWidth ,bWidth,cWidth,dWidth ;
            aWidth = bWidth = cWidth = dWidth = 0;
            int marginHa ,marginHb,marginHc,marginHd ;
            marginHa = marginHb= marginHc = marginHd = 0;
            for(int i= 0 ;i<getChildCount() ;i++){
                MarginLayoutParams layoutParams = (MarginLayoutParams) getChildAt(i).getLayoutParams();
                switch (i){
                    case 0:
                        aWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHa +=layoutParams.topMargin+layoutParams.bottomMargin ;
                        break;
                    case 1:
                        bWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHb +=layoutParams.topMargin+layoutParams.bottomMargin ;
                        break;
                    case 2:
                        cWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHc +=layoutParams.topMargin+layoutParams.bottomMargin ;
                        break;
                    case 3:
                        dWidth = getChildAt(i).getMeasuredWidth() ;
                        marginHd +=layoutParams.topMargin+layoutParams.bottomMargin ;
                        break;
                }
            }
            width = Math.max(aWidth,cWidth) + Math.max(bWidth,dWidth)
                    +getPaddingTop() + getPaddingBottom()
                    +Math.max(marginHa,marginHc)
                    +Math.max(marginHb,marginHd)  ;
        }
        return width ;
    }
}
