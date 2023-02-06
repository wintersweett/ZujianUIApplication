package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class BitmapShaderView extends View {
    public BitmapShaderView(Context context) {
        this(context,null);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.bautifulgirl
        );
        BitmapShader bs = new BitmapShader(
                bitmap,
                Shader.TileMode.REPEAT,
                Shader.TileMode.MIRROR
        );
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(bs);
        canvas.drawRect(new Rect(0,0,
                getMeasuredWidth(),
                getMeasuredHeight()),paint);
    }
}
