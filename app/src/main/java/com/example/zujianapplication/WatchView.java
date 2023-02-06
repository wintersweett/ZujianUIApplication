package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class WatchView extends View {
    private Paint paint ;
    private Calendar calendar ;
    public WatchView(Context context) {
        this(context,null);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        calendar = Calendar.getInstance() ;
    }

    public void run(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取组件宽度
        int width = this.getMeasuredWidth() ;
        //获取组件高度
        int height = this.getMeasuredHeight() ;
        //计算圆盘直径，取短的
        int len = Math.min(width,height);
        //绘制表盘
        drawPlate(canvas,len) ;
        //绘制指针
        drawPoints(canvas,len);

    }
    /**
    *@Description 绘制表盘
    *@author WinterSweett
    */
    private void drawPlate(Canvas canvas ,int len){
        canvas.save() ;
        //画圆
        int r = len >>2 ;
        canvas.drawCircle(r,r,r,paint);
        //画刻度
        for(int i =0 ;i<60 ;i++){
            if(i%5 == 0){
                //长刻度，长刻度占圆半径的1/10
                paint.setColor(Color.RED);
                paint.setStrokeWidth(4);
                canvas.drawLine(r+9*r/10,r,len,r,paint);
            }else {
                //短刻度，长刻度占圆半径的1/15
                paint.setColor(Color.GRAY);
                paint.setStrokeWidth(1);
                canvas.drawLine(r+14*r/15,r,len,r,paint);

            }
            //以（r,r)为中心，将画布旋转6度
            canvas.rotate(6,r,r);
        }
        canvas.restore();
    }

    private void drawPoints(Canvas canvas ,int len){
        //获取系统时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        //获取时分秒
        int hours = calendar.get(Calendar.HOUR)%12 ;//转换为12h制
        int minute = calendar.get(Calendar.MINUTE) ;
        int seconds = calendar.get(Calendar.SECOND) ;
        int degree = 360/12*hours ;
        //转换成弧度
        double radians = Math.toRadians(degree) ;
        //根据当前时计算时针两个点的坐标
        //起点（圆中心），终点：计算得到
        int r = len/2 ;
        int startX = r ;
        int startY = r ;
        int endX = (int) (startX+r*0.5*Math.cos(radians));
        int endY = (int) (startY+r*0.5*Math.sin(radians));
        canvas.save() ;
        paint.setStrokeWidth(3);
        //0度从3点处开始，时间从12点开始，所以需要将画布旋转90度
        canvas.rotate(-90,r,r);
        //画时针
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();
        //画分针
        degree = 360/60*minute;
        radians = Math.toRadians(degree);
        endX = (int) (startX+r*0.6*Math.cos(radians));
        endY = (int) (startY + r*0.6*Math.sin(radians));
        canvas.save();
        paint.setStrokeWidth(2);
        canvas.rotate(-90,r,r);
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();

        //画秒针
        degree = 360/60*seconds ;
        radians = Math.toRadians(degree) ;
        endX = (int) (startX+r*0.8*Math.cos(radians));
        endY = (int) (startY +r*Math.sin(radians));
        canvas.save() ;
        paint.setStrokeWidth(1);
        canvas.rotate(-90,r,r);

        canvas.drawLine(startX,startY,endX,endY,paint);

        radians = Math.toRadians(degree-180) ;
        endX = (int) (startX+r*0.15*Math.cos(radians));
        endY = (int) (startY+r*0.15*Math.sin(radians));
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();
    }
}












