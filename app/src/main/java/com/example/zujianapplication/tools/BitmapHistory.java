package com.example.zujianapplication.tools;

import android.graphics.Bitmap;

import java.util.Stack;

public class BitmapHistory {
    private static Stack<Bitmap> stack ;
    private static BitmapHistory self ;
    private BitmapHistory(){
        if(stack == null){
            stack = new Stack<>();
        }
    }
    public static BitmapHistory getInstance(){
        if(self == null){
            self = new BitmapHistory() ;
        }
        return self ;
    }

    //将当前的历史绘图结果压栈
    public void pushBitmap(Bitmap bitmap){
        int count = stack.size() ;
        if(count >= 5){
            Bitmap bmp = stack.remove(0) ;
            if(!bmp.isRecycled()){
                bmp.recycle();
                System.gc();
                bmp = null ;
            }
        }
        stack.push(bitmap) ;
    }

    public Bitmap redo(){
        Bitmap bmp = stack.pop() ;
        if(bmp != null && !bmp.isRecycled()){
            bmp.recycle();
            System.gc();
            bmp = null ;
        }
        if(stack.empty()){
            return null ;
        }
        return stack.peek() ;
    }

    public boolean isRedo(){
        return !stack.empty() ;
    }
}
