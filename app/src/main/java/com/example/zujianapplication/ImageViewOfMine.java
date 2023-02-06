package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianapplication.tools.BitmapBuffer;
import com.example.zujianapplication.tools.ShapeDrawer;
import com.example.zujianapplication.tools.SystemParams;

public class ImageViewOfMine extends View {
    private ShapeDrawer shapeDrawer ;
    public ImageViewOfMine(Context context) {
        this(context,null);
    }

    public ImageViewOfMine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       // shapeDrawer = new Line1View(getContext());
    }
    public void setShapeDrawer(ShapeDrawer drawer){
        this.shapeDrawer = drawer ;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SystemParams.areaWidth = this.getMeasuredWidth() ;
        SystemParams.areaHeight = this.getMeasuredHeight() ;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(SystemParams.isRedo){
            canvas.drawBitmap(BitmapBuffer.getInstance().getBitmap(), 0,0,null);
            SystemParams.isRedo = false ;

        }else {
            shapeDrawer.draw(canvas);
        }
        shapeDrawer.logic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return shapeDrawer.onTouchEvent(event);
    }
}
