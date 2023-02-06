package com.example.zujianapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class CirclePhotoView extends View {
    private Bitmap cat ;
    private Bitmap circleMask ;
    private Canvas circle ;
    private Paint paint ;
    public CirclePhotoView(Context context) {
        this(context,null);
    }

    public CirclePhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        cat = BitmapFactory.decodeResource(getResources(), R.mipmap.bautifulgirl) ;
        int minWidth = Math.min(cat.getWidth(),cat.getHeight());
        circleMask = Bitmap.createBitmap(minWidth,minWidth,
                Bitmap.Config.ARGB_8888);
        circle = new Canvas(circleMask) ;
        int r = minWidth/2 ;
        circle.drawCircle(r,r,r,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w= circleMask.getWidth() ;
        int layer = canvas.saveLayer(0,0,w,w,null,Canvas.ALL_SAVE_FLAG);
        Log.d("ws", "onDraw:layer43 "+layer);
        canvas.drawBitmap(cat,0,0,null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(circleMask,0,0,paint);
        canvas.restoreToCount(layer);
        Log.d("ws", "onDraw:layer48 "+layer);
    }
}
