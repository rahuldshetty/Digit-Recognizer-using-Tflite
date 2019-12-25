package com.rahuldshetty.digitrecognizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class SimpleDrawingView extends View {

    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private List<Point> circlePath;
    private Path path = new Path();
    private Bitmap mField = null;
    private View view;
    private int width = 50;

    public SimpleDrawingView(Context context, AttributeSet attr) {
        super(context,attr);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        circlePath = new ArrayList<>();
        view = this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,drawPaint);
    }

    public Bitmap getBitmap(){
        Bitmap map = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(map);
        Drawable d = view.getBackground();

        if(d!=null)
            d.draw(c);
        view.draw(c);

        return map;
    }

    private void setupPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(width);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    public void clear(){
        path.reset();
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float touchX = event.getX(),touchY = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX,touchY);
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX,touchY);
                break;
            default:return false;


        }

        postInvalidate();
        return true;
    }
}
