package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class ClipView extends View {
    private int i= 0 ;
    private Bitmap bmpBoom ;
    public ClipView(Context context) {
        this(context,null);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bmpBoom = BitmapFactory.decodeResource(getResources(), R.mipmap.bautifulgirl) ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        test2(canvas);

    }
    private void test2(Canvas canvas){
        int width = bmpBoom.getWidth() ;
        int height = bmpBoom.getHeight() ;
        //剪切区
        int frameWidth = width /7 ;
        Rect rect = new Rect(0,0,frameWidth,height) ;
        canvas.save() ;
        canvas.translate(100,100);
        canvas.clipRect(rect);
        //播放一帧
        canvas.drawBitmap(bmpBoom,-i*frameWidth,0,null);
        canvas.restore();
        i++;
        if(i == 7) i=0;
    }
    private void test1(Canvas canvas){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bautifulgirl);
        //绘制狗狗完整照片
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.translate(0,500);
        canvas.clipRect(new Rect(100,100,500,500));
        //定义一个新的剪切区，与上一个剪切区做Op运算
        Path path = new Path();
        path.addCircle(500,350,200, Path.Direction.CCW);
        if(Build.VERSION.SDK_INT>=26){
            canvas.clipPath(path) ;
        }else {

            canvas.clipPath(path, Region.Op.UNION) ;
        }
        canvas.drawBitmap(bitmap,0,0,null);

    }
}




















