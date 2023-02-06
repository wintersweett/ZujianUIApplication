package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SizeViewGroup extends ViewGroup {
    public SizeViewGroup(Context context) {
        this(context,null);
    }

    public SizeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView textView = new TextView(context) ;
        textView.setText("我是中国人");
        textView.setTextSize(100);
        textView.setBackgroundColor(Color.YELLOW);
        LayoutParams layoutParams = new LayoutParams(200,200);
        addView(textView,layoutParams);
        setBackgroundColor(Color.alpha(255));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View textView = getChildAt(0) ;
        textView.layout(50,50,textView.getMeasuredWidth()+50,
                textView.getMeasuredHeight()+50);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //先测量所有子组件的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //测量自身的大小，
        setMeasuredDimension(500,500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()) ;
        rectF.inset(2,2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        Path path = new Path() ;
        path.addRoundRect(rectF,20,20, Path.Direction.CCW);
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }
}
