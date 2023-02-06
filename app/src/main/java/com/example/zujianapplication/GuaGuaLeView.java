package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class GuaGuaLeView extends View {
    private Paint paint;
    private Paint clearPaint ;
    private Bitmap btmBuffer;
    private Canvas cvsBuffer;

    public GuaGuaLeView(Context context) {
        this(context, null);
    }

    public GuaGuaLeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)) ;
        clearPaint.setStrokeJoin(Paint.Join.ROUND);
        clearPaint.setStrokeCap(Paint.Cap.ROUND);
        clearPaint.setStrokeWidth(500);
        drawBack();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        btmBuffer = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cvsBuffer = new Canvas(btmBuffer);
        cvsBuffer.drawColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(btmBuffer, 0, 0, paint);
    }

    int curX;
    int curY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curX = x ;
                curY = y ;
                break;
            case MotionEvent.ACTION_MOVE:
                cvsBuffer.drawLine(curX,curY,x,y,clearPaint);
                invalidate();
                curX = x ;
                curY = y ;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }

    private void drawBack() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bautifulgirl);
        Bitmap mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutable);
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setTextSize(30);
        paint1.setColor(Color.RED);
        canvas.drawText("woshishui", 100, 100, paint1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new BitmapDrawable(getResources(), mutable));
        } else {
            setBackgroundDrawable(new BitmapDrawable(mutable));
        }


    }
}
