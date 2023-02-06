package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FiveChessView extends View {
    //棋子的尺寸
    private static final int SIZE = 120 ;
    //发光点的偏移大小
    private static final int OFFSET = 10 ;
    private Paint paint ;
    public FiveChessView(Context context) {
        this(context,null);
    }

    public FiveChessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth() ;
        int height = getMeasuredHeight() ;
        int rows = height / SIZE ;
        int cols = width / SIZE ;
        //画棋盘
        drawChessBoard(canvas,rows,cols) ;
        //画棋子
        drawChess(canvas,4,4,ChessType.BLACK) ;
        drawChess(canvas,4,5,ChessType.BLACK) ;
        drawChess(canvas,5,4,ChessType.BLACK) ;
        drawChess(canvas,3,5,ChessType.BLACK) ;
    }

    /**
    *@Description 画棋子
    *@author WinterSweett
    */
    private void drawChess(Canvas canvas, int x, int y, ChessType chessType) {
        int colorOuter = chessType == ChessType.BLACK ? Color.BLACK : Color.GRAY ;
        int colorInner =Color.WHITE ;
        //定义渐变，发光点向右下角偏移OFFSET
        RadialGradient rg = new RadialGradient(
                x*SIZE+OFFSET,
                y*SIZE+OFFSET,
                SIZE/1.5f,
                colorInner,
                colorOuter,
                Shader.TileMode.CLAMP

        );
        paint.setShader(rg);
        //画棋子
        setLayerType(LAYER_TYPE_SOFTWARE,paint);
        //给棋子加阴影
        paint.setShadowLayer(6,4,4,Color.parseColor("#AACCCCCC"));
        canvas.drawCircle(x*SIZE,y*SIZE,SIZE/2,paint);
    }
/**
*@Description 画棋盘
*@author WinterSweett
*/
    private void drawChessBoard(Canvas canvas, int rows, int cols) {
        paint.setColor(Color.GRAY);
        //取消阴影
        paint.setShadowLayer(0,0,0,Color.GRAY);
        for(int i = 0 ;i< rows ;i++){
            canvas.drawLine(0,i*SIZE,cols*SIZE,i*SIZE,paint);

        }
        for(int i= 0 ;i< cols ;i++){
            canvas.drawLine(i*SIZE,0,i*SIZE,rows*SIZE,paint);
        }
    }

    enum ChessType{
        BLACK ,WHITE
    }
}
