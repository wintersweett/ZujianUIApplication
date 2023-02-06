package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianuiapplication.R;

public class ComposeShaderView extends View {
    public ComposeShaderView(Context context) {
        this(context,null);
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //位图渐变
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        BitmapShader bs = new BitmapShader(bitmap,
                Shader.TileMode.REPEAT,
                Shader.TileMode.MIRROR);

        //线性渐变
        LinearGradient lg = new LinearGradient(
                0,0,
                getMeasuredWidth(),0,
                Color.RED,Color.BLUE,
                Shader.TileMode.CLAMP
        );
        //混合渐变
        ComposeShader cs =
                new ComposeShader(bs,lg, PorterDuff.Mode.SRC_ATOP);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(cs);
        canvas.drawRect(new Rect(0,0,
                getMeasuredWidth(),600),paint);



    }
}
