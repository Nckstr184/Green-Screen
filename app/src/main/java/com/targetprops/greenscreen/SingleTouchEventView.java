package com.targetprops.greenscreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouchEventView extends View {
    public Paint paint = new Paint();
    private Path path = new Path();
    private Canvas canvas = new Canvas();
    ColorScreen context;
    final int threshold = 10;

    public SingleTouchEventView(Context context) {
        super(context);
        this.context = (ColorScreen)context;
        paint.setAntiAlias(true);
        paint.setStrokeWidth(25f);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAlpha(75);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    int counter = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX(0);
        float eventY = event.getY(0);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                counter++;
                if(counter%threshold==0) {
                    path.rewind();
                    path.moveTo(eventX, eventY);
                }
                path.lineTo(eventX, eventY);
                canvas.drawPaint(paint);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(event.getPointerCount()==2)
                    context.toggleActionBar(2);
                break;
            case MotionEvent.ACTION_UP:
                path.rewind();
                counter=0;
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }
}
