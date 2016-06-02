package com.example.cro_germany.easywrite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class MyView extends SurfaceView implements Callback,OnTouchListener{

    private Paint p = new Paint();//创建画笔对象
    private Path path = new Path();//路径对象

    //构造方法
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);//添加回调函数
        p.setColor(Color.RED);//画笔颜色
        p.setTextSize(10);//大小
        p.setStrokeWidth(10);//粗细
        p.setAntiAlias(true);//抗锯齿
        p.setStyle(Style.STROKE);//填充样式为空心
        setOnTouchListener(this);//添加监听

    }

    //绘制方法
    public void draw(){
        Canvas canvas = getHolder().lockCanvas();//锁定画布
        canvas.drawColor(Color.WHITE);//初始化画布颜色
        canvas.drawPath(path, p);
        getHolder().unlockCanvasAndPost(canvas);//解锁画布
    }

    //清除画布的方法
    public void clear(){
        path.reset();//路径清除重置
        draw();//调用draw方法进行再次绘制
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();//调用draw方法
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    //OnTouchListener的onTouch方法
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                path.moveTo(event.getX(), event.getY());
                draw();
                break;

            case MotionEvent.ACTION_MOVE://移动
                path.lineTo(event.getX(), event.getY());
                draw();
                break;
        }

        return true;
    }

}
