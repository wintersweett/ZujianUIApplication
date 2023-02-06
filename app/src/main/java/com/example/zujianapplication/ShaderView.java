package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShaderView extends View {
    public ShaderView(Context context) {
        this(context,null);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(100);
        this.setLayerType(LAYER_TYPE_SOFTWARE,paint);
        paint.setShadowLayer(10,1,1, Color.RED);
        canvas.drawText("android开发",100,100,paint);
        paint.setShadowLayer(10,5,5,Color.BLUE);
        canvas.drawText("android绘图技术",100,220,paint);
    }
}
