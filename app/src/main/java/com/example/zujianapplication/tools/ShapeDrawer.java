package com.example.zujianapplication.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public abstract class ShapeDrawer {
    private View view ;
    public ShapeDrawer(View view){
        super();
        this.view = view ;
    }

    public View  getView(){
        return view ;
    }

    public void draw(Canvas canvas){
        Bitmap bitmap = BitmapBuffer.getInstance().getBitmap();
        canvas.drawBitmap(bitmap,0,0,null);
    }

    public abstract boolean onTouchEvent(MotionEvent event) ;
    public abstract void logic() ;
}
