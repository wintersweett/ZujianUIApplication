package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class AnomalousPhotoView extends View {
    private Bitmap girl ;
    private Bitmap mask ;
    private Paint paint ;
    private static final int OFFSET = 100 ;
    public AnomalousPhotoView(Context context) {
        this(context,null);
    }

    public AnomalousPhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        girl = BitmapFactory.decodeResource(getResources(), R.mipmap.bautifulgirl) ;
        mask = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int id = canvas.saveLayer(OFFSET,OFFSET,mask.getWidth()+OFFSET,
                mask.getHeight()+OFFSET,
                null,Canvas.ALL_SAVE_FLAG
                );
        canvas.drawBitmap(girl,0,0,null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask,OFFSET,OFFSET,paint);
        canvas.restoreToCount(id);
    }
}
