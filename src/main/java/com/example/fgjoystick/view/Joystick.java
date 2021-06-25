package com.example.fgjoystick.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import androidx.annotation.Nullable;

interface changeable {
    public void onChange(double a, double b);
}
public class Joystick extends View {
    public changeable onChange;
    private float _innerCircleRadius;
    private float _outterCircleRadius;
    private float _outterCenterPositionY;
    private float _outterCenterPositionX;
    private float _innerCenterPositionX;
    private float _innerCenterPositionY;
    private Paint outterCirclePaint;
    private Paint innerCirclePaint;
    private boolean _isPressed = false;


    public Joystick(Context context) {
        super(context);
        init(null);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (set == null) {
            return;
        }

        innerCirclePaint.setColor(Color.RED);
        outterCirclePaint.setColor(Color.GRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println("Hello");
        _outterCircleRadius = (float) Math.min(w , h) / 4;
        _innerCircleRadius = ( _outterCircleRadius / 3f);
        _outterCenterPositionX = _innerCenterPositionX = w/2;
        _outterCenterPositionY = _innerCenterPositionY = h/2;
    }

    public double twoPointsDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outterCirclePaint.setColor(Color.GRAY);
        innerCirclePaint.setColor(Color.RED);
        canvas.drawCircle(
                _outterCenterPositionX,
                _outterCenterPositionY,
                _outterCircleRadius,
                outterCirclePaint
        );

        canvas.drawCircle(
                _innerCenterPositionX,
                _innerCenterPositionY,
                _innerCircleRadius,
                innerCirclePaint
        );
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
         return twoPointsDistance(
                touchPositionX, touchPositionY,
                _outterCenterPositionX, _outterCenterPositionY) < _outterCircleRadius;
    }

    public void setIsPress(boolean b) {
        this._isPressed = b;
    }
    public boolean getIsPressed() {
        return this._isPressed;
    }

    public void setPosition(float x, float y) {
        if (twoPointsDistance(x, y, _outterCenterPositionX, _outterCenterPositionY) > _outterCircleRadius) {
            float deltaX = x - _outterCenterPositionX;
            float deltaY = y - _outterCenterPositionY;
            double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            _innerCenterPositionX = _outterCenterPositionX + (float) (deltaX / deltaDistance) * _outterCircleRadius;
            _innerCenterPositionY = _outterCenterPositionY + (float) (deltaY / deltaDistance) * _outterCircleRadius;
        } else {
            _innerCenterPositionX = x;
            _innerCenterPositionY = y;
        }
        this.update();
    }

    private void update() {
        postInvalidate();
    }

    public void resetPosition() {
        _innerCenterPositionX = _outterCenterPositionX;
        _innerCenterPositionY = _outterCenterPositionY;
        this.update();
    }

    public double getOutterRadius() {
        return _outterCircleRadius;
    }

    public double getOutterCircleCenterX() {
        return _outterCenterPositionX;
    }
    public double getOutterCircleCenterY() {
        return _outterCenterPositionY;
    }
}


