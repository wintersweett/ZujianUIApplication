package com.example.zujianapplication.tools;

import android.graphics.Color;
import android.graphics.Paint;

public class AttributesTool {
    private int color ;
    private int borderWidth ;
    private boolean fill ;
    private static AttributesTool self ;
    private static Paint paint ;
    private AttributesTool(){
        reset();
    }
    public Paint getPaint(){
        if(paint == null){
            paint = new Paint() ;
        }
        paint.setAntiAlias(true);
        paint.setColor(this.color);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(this.fill? Paint.Style.FILL: Paint.Style.STROKE);
        paint.setTextSize(30);
        return paint ;
    }
    public void setFill(boolean fill){
        this.fill = fill ;
    }
    public boolean isFill(){
        return fill ;
    }
    public void setBorderWidth(int borderWidth){
        this.borderWidth = borderWidth ;
    }
    public int getBorderWidth(){
        return borderWidth ;
    }
    public void setColor(int color){
        this.color = color ;
    }
    public int getColor(){
        return color ;
    }
    public static AttributesTool getInstance(){
        if(self == null){
            self = new AttributesTool();
        }
        return self ;
    }

    private void reset() {
        this.color = Color.BLACK ;
        this.borderWidth = 1 ;
        this.fill = false ;
    }
}
