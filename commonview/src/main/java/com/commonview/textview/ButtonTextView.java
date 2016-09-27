package com.commonview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.view.shj.commonview.R;

/**
 * Created by SHJ on 2016/9/13.
 */
public class ButtonTextView extends TextView {
    public static int NORMAL_COLOR = 0xFFAAAAAA;
    public static int PRESS_COLOR = 0xFF9E9E9E;
    public static int CORNER_SIZE = 8;
    private int mNormal_Color;
    private int mPress_Color;
    private int mCorner_Size;
    private boolean isPress = false;
    public ButtonTextView(Context context) {
        super(context,null);
    }

    public ButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
        getAttrs(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isPress){
            setBackgroundRound(this.getMeasuredWidth(),this.getMeasuredHeight(),this,mNormal_Color);
        }
        super.onDraw(canvas);
    }

    private void setBackgroundRound(int width,int height,View v,int color) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(color);
        RectF rec = new RectF(0, 0, width, height);
        c.drawRoundRect(rec, mCorner_Size, mCorner_Size, paint);
        if(Build.VERSION.SDK_INT<16) {
            v.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
        }else{
            v.setBackground(new BitmapDrawable(getResources(), bmp));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isPress = true;
                setBackgroundRound(this.getMeasuredWidth(), this.getMeasuredHeight(), this, mPress_Color);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPress = false;
                setBackgroundRound(this.getMeasuredWidth(), this.getMeasuredHeight(), this, mNormal_Color);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ButtonTextView);
        mNormal_Color = ta.getColor(R.styleable.ButtonTextView_normal_color,NORMAL_COLOR);
        mPress_Color = ta.getColor(R.styleable.ButtonTextView_press_color,PRESS_COLOR);
        mCorner_Size = ta.getInteger(R.styleable.ButtonTextView_corner_size,CORNER_SIZE);
        ta.recycle();
    }
}
