package com.example.zujianapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class CircleImageView extends ImageView {
    private Paint paint;
    private Xfermode xfermode ;
    private Path path = new Path() ;
    private int border ;
    private int borderColor ;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setColor(Color.BLACK);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN) ;
        path = new Path() ;
        TypedArray array= context.obtainStyledAttributes(attrs,
                R.styleable.CircleImageView);
        border = array.getDimensionPixelSize(R.styleable.CircleImageView_circle_border,0);
        borderColor = array.getColor(R.styleable.CircleImageView_circle_border_color,Color.GRAY);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable() ;
        if(drawable == null){
            super.onDraw(canvas);
        }
        int width = getMeasuredWidth() ;
        int height = getMeasuredHeight() ;
        RectF ovalRect = new RectF(0,0,width,height) ;
        int layerId = canvas.saveLayer(getPaddingLeft(),getPaddingTop(),width,
                height,paint,Canvas.ALL_SAVE_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bautifulgirl);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),
                bitmap.getHeight()),ovalRect,null);
        paint.setXfermode(xfermode) ;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        path.reset();
        path.addOval(ovalRect, Path.Direction.CCW);
        canvas.drawPath(path,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

        //画空心圆
        if(border != 0){
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(border);
            ovalRect.inset(border/2,border/2);
            canvas.drawOval(ovalRect,paint);
        }

    }
}
