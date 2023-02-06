package com.example.zujianuiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zujianapplication.BallMoveView;
import com.example.zujianapplication.ClipView;
import com.example.zujianapplication.Line1View;
import com.example.zujianapplication.Line2View;
import com.example.zujianapplication.Rect1View;
import com.example.zujianapplication.Rect3View;
import com.example.zujianapplication.WatchView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ws" ;
    private ImageView imageView ;
    private BallMoveView bmv ;
    protected ClipView clipView ;
    private WatchView wv ;
    private Line1View lv1 ;
    private Line2View lv2 ;
    private TextView tv ;
    private Rect1View rect1View ;
    private Rect3View rect3V;
    private void testInterrupt(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (!Thread.currentThread().isInterrupted()){
                    Log.d(TAG, "run: "+"我被中断了"+Thread.currentThread().isInterrupted());
                    try {
                        Thread.currentThread().sleep(500);
                        Log.d(TAG, "sleep: "+"sleep");
                    } catch (InterruptedException e) {
                        Log.d(TAG, "catch: "+"catch");
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run: "+"我活了"+Thread.currentThread().isInterrupted());
            }
        };
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "run:wai "+"interrupt前");
        thread.interrupt();
        Log.d(TAG, "run:wai "+"interrupt后");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testInterrupt();
        bmv = findViewById(R.id.bmv) ;
        clipView = findViewById(R.id.cv);
        wv = findViewById(R.id.wv) ;
        lv1 = findViewById(R.id.lv1) ;
        lv2 = findViewById(R.id.lv2) ;
        tv = findViewById(R.id.tv) ;
        rect1View = findViewById(R.id.rect1View) ;
        rect3V = findViewById(R.id.rect3V) ;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setActivated(true);
            }
        });
        wv.run();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                bmv.postInvalidate();
            }
        },200,50);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clipView.postInvalidate();
            }
        },200,50);


        // useImg();
    }

    private void useImg(){
        imageView = findViewById(R.id.iv) ;
        Bitmap bitmap = Bitmap.createBitmap(1000,1499, Bitmap.Config.ARGB_8888) ;
        Canvas canvas = new Canvas(bitmap);
//         use1(canvas);
//        use2(canvas);
//        use3(canvas);
//        use4(canvas);
//        use13(canvas);
        use14(canvas);
        imageView.setImageBitmap(bitmap);
    }
    private void use14(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paint.setTextSize(14);
        String text= "我是中国人，中国人不骗中国人，你说好不好呀，好不好嘛" ;
        // canvas.rotate(90);
        canvas.translate(100,100);
        canvas.rotate(90);
        canvas.drawText(text,10,50,paint);
//        canvas.drawText(text,0,4,10,100,paint);
//        canvas.drawText(text.toCharArray(),0,4,10,150,paint);

//        Path path = new Path() ;
//        path.moveTo(10,200);
//        path.quadTo(100,100,300,300);
//        canvas.drawTextOnPath(text,path,15,15,paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        // canvas.drawPath(path,paint);
    }

    /**
     *@Description 绘制文字
     *@author WinterSweett
     */
    private void use13(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(14);
        String text= "我是中国人，中国人不骗中国人，你说好不好呀，好不好嘛" ;
        canvas.drawText(text,10,50,paint);
        canvas.drawText(text,0,4,10,100,paint);
        canvas.drawText(text.toCharArray(),0,4,10,150,paint);

        Path path = new Path() ;
        path.moveTo(10,200);
        path.quadTo(100,100,300,300);
        canvas.drawTextOnPath(text,path,15,15,paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,paint);
    }
    /**
     *@Description 画出矩形 与 圆形的运算结果
     *@author WinterSweett
     */
    private void use12(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path() ;
        path1.addRect(new RectF(10,10,110,110), Path.Direction.CCW);
        Path path2 = new Path() ;
        path2.addCircle(100,100,50, Path.Direction.CCW);
        //运算语句
        path1.op(path2, Path.Op.UNION) ;
        canvas.drawPath(path1,paint);
    }

    /**
     *@Description 用path 画矩形与圆
     *@author WinterSweett
     */
    private void use11(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10,10,110,110), Path.Direction.CCW);
        Path path2 = new Path() ;
        path2.addCircle(100,100,50, Path.Direction.CCW);
        canvas.drawPath(path1,paint);
        paint.setColor(Color.RED);
        canvas.drawPath(path2,paint);
    }

    private void use10(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path() ;
        path.moveTo(100,100);
        path.arcTo(new RectF(100,150,300,300),-30,60,false);
        canvas.drawPath(path,paint);
    }


    private void use9(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100,100);
        path.quadTo(200,10,300,300);
        canvas.drawPath(path,paint);

        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        canvas.drawPoints(new float[]{100,100,200,10,300,300},paint);

    }
    /**
     *@Description 绘制path
     *@author WinterSweett
     */
    private void use8(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path() ;
        //矩形
        path.addRect(new RectF(10,10,300,100),Path.Direction.CCW);
        //圆角矩形，4个角的弧度都不一样，2个数确定一个弧度
        path.addRoundRect(new RectF(10,120,300,220),new float[]{10,20,20,10,30,40,40,30}, Path.Direction.CCW);
        //椭圆
        path.addOval(new RectF(10,240,300,340), Path.Direction.CCW);
        //圆
        path.addCircle(60,390,50, Path.Direction.CCW);
        //弧线
        path.addArc(new RectF(10,500,300,600),-30,-60);

        canvas.drawPath(path,paint);
    }
    private void use7(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path() ;
        //将画笔移动到点 x,y的位置，使用的绝对定位
        path.moveTo(0,150);
        //使用的相对定位
        path.rLineTo(300,0);
        path.rLineTo(-300,150);
        path.rLineTo(150,-300);
        path.rLineTo(150,300);
        //在第一个点 和最后一个点之前画一条直线 形成闭合区域
        path.close();
        canvas.drawPath(path,paint);
    }
    /**
     *@Description 绘制圆 椭圆
     *@author WinterSweett
     */
    private void use6(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        RectF rect = new RectF(10,10,400,300) ;
        canvas.drawOval(rect,paint);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect,-30,-30,false,paint);
    }
    /**
     *@Description 绘制矩形
     *@author WinterSweett
     */
    private void use5(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setColor(Color.RED);
        canvas.drawRoundRect(10,10,400,300,50,50,paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(10,320,400,620),30,50,paint);
    }

    /**
     *@Description 画线
     *@author WinterSweett
     */
    private void use4(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        final int offsetDy = 50 ;
        for(int i= 0 ;i<5 ;i++){
            canvas.drawLine(10,offsetDy*1,300,offsetDy*i,paint);
        }
    }

    /**
     *@Description 绘制点
     *@author WinterSweett
     */
    private void use3(Canvas canvas){
        Paint paint = new Paint() ;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(40);
        //canvas.drawPoint(120,20,paint);

        //TODO 该方法的参数 pts 是一个数组，从下标 0 开始每 2 个数确定一个点，连续绘制多个点。 多余的元素会忽略。
        float[] points = new float[]{10,10,50,50,50,100,50,150} ;
        canvas.drawPoints(points,paint);

        paint.setColor(Color.GREEN);
        canvas.drawPoints(points,1,4,paint);


    }
    private void use2(Canvas canvas){
        //原图大小绘制
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bautifulgirl) ;
        canvas.drawBitmap(bitmap,0,0,null);
        //TODO 缩放没看出差别啊？？？？？？dst是绘制到哪里了
        //对图片缩放
        int bmpWidth = bitmap.getWidth() ;
        int bmpHeight = bitmap.getHeight() ;
        Rect src = new Rect(0,0,bmpWidth,bmpHeight);
        Rect dst = new Rect(0,bmpHeight,bmpWidth/155,bmpHeight/155+bmpHeight);
        canvas.drawBitmap(bitmap,src,dst,null);
    }
    private void use1(Canvas canvas){
        //绘制文字
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        int sp = 14 ;
        paint.setTextSize(sp);
        paint.setTextSkewX(0.5f);
        paint.setUnderlineText(true);
        paint.setFakeBoldText(true);
        canvas.drawText("我是谁我要去哪里",10,100,paint);
        //绘制图形
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(new Rect(10,200,350,350),paint);
    }
}